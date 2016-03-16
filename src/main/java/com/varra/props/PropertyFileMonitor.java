/*
 * utils4j - PropertyFileMonitor.java, May 7, 2011 1:01:31 PM
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.varra.props;

import static com.varra.props.VarraProperties.*;
import static com.varra.util.StringUtils.*;
import static com.varra.util.ObjectUtils.*;
import static com.varra.util.WrapperUtils.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Properties;

import com.varra.exception.PropertyFileLoaderException;
import com.varra.log.ConsoleLogger;
import com.varra.log.Logger;
import com.varra.log.LoggerConstants;
import com.varra.util.EnhancedTimerTask;
import com.varra.util.GlobalThread;
import com.varra.util.ShutdownMode;
import com.varra.util.Shutdownable;
import com.varra.util.StringUtils;

/**
 * A watch dog which Monitors the Property file specified, and loads whenever it
 * is modified.<br>
 * So maintains the properties up-to-date. <br>
 * Property file loading Interval can be mentioned in the property file with the
 * property named "this.interval".
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
public class PropertyFileMonitor extends EnhancedTimerTask implements Shutdownable
{
	
	/** Holds the "this." string */
	private static final String THIS = "this.";

	/** The logger to log the debugging messages as application runs. */
	private final Logger logger;
	
	/** The last modified. */
	private long lastModified;
	
	/** The interval. */
	final private ApplicationProperties applicationProperties;
	
	/** The pattern for environment variable regex. */
	final private Pattern pattern;
	
	/** The matcher for environment variable regex. */
	final private Matcher matcher;
	
	/** The flag to decide whether resolution is required. */
	private boolean resolveRequired;
	
	/** The interval of this monitoring. */
	private int interval;
	
	/**
	 * Instantiates a new property file monitor.
	 * 
	 * @param applicationProperties
	 *            the application properties
	 * @throws PropertyFileLoaderException
	 *             the property file loader exception
	 */
	public PropertyFileMonitor(final ApplicationProperties applicationProperties) throws PropertyFileLoaderException
	{
		super(PropertyFileMonitor.class.getSimpleName()+"-"+applicationProperties.getPropertyFile().getName());
		
		logger = Logger.getLogger(PropertyFileMonitor.class);
		if (isNotNull(applicationProperties))
		{
			// The existence of file is checked at construction of
			// @ApplicationProperties itself. here it is enough to check the
			// object of ApplicationProperties whether it is null.
			this.applicationProperties = applicationProperties;
			this.pattern = Pattern.compile(ENV_VARIABLE_REGEX);
			this.matcher = pattern.matcher(SPACE);
			if (applicationProperties.isMonitoringEnabled())
			{
				setDaemon(true);
				setPeriod(applicationProperties.getInterval());
			}
			doLoad();
		}
		else
		{
			throw new PropertyFileLoaderException("Received an invalid properties file to monitor.. !");
		}
	}
	
	/**
	 * Causes this thread to begin execution;.
	 */
	protected synchronized void start()
	{
		if (applicationProperties.isMonitoringEnabled())
		{
			GlobalThread.getGlobalThread().onTimerTask(this);
		}
	}
	
	/**
	 * Forces the thread to stop executing.
	 * @see #shutdown(ShutdownMode mode)
	 */
	protected synchronized void stop()
	{
		shutdown(ShutdownMode.GRACEFUL_SHUTDOWN);
	}
	
	/**
	 * Shutdowns the backGround {@link PropertyFileMonitor}.
	 * @see PropertyFileMonitor#stop()
	 */
	public void shutdown(ShutdownMode mode)
	{
		logger.info("Received the request to stop Me, waiting to complete the background work.");
		setFinished(true);
		logger.info("Shutdown is complete.");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void compute()
	{
		try
		{
			doLoad();
			setPeriod(interval);
		}
		catch (Exception e)
		{
			logger.error("Got ean unexpected error while monitoriing the propertyy file: "+ applicationProperties.getPropertyFileName(), e);
		}
	}
	
	/**
	 * Do loads the property file, checks whether the file is really modified,
	 * loads if so.
	 * 
	 * @throws PropertyFileLoaderException
	 *             the property file loader exception
	 */
	private void doLoad() throws PropertyFileLoaderException
	{
		try
		{
			if (lastModified != applicationProperties.getPropertyFile().lastModified())
			{
				logger.info("Loading properties from the file: " + applicationProperties.getPropertyFileName());
				
				final Properties props = new Properties();
				props.load(new FileInputStream(applicationProperties.getPropertyFile().getAbsolutePath()));
				resolveRequired = to(props.get(RESOLVE_REQUIRED_PROPERTY), Boolean.TRUE);;
				interval = to(props.get(THIS_INTERVAL_PROPERTY), applicationProperties.getInterval());
				if (resolveRequired)
				{
					resolve(props);
				}
				applicationProperties.setProperties(props);
				logger.info(applicationProperties.getProperties());
				lastModified = applicationProperties.getPropertyFile().lastModified();
				
				logger.info("Successfully Loaded the properties from the file: " + applicationProperties.getPropertyFileName());
			}
			else
			{
				logger.info("I couldn't see any changes in the file: " + applicationProperties.getPropertyFile().getAbsolutePath() + ", hence not loading it.");
			}
		}
		catch (Exception e)
		{
			throw new PropertyFileLoaderException("Unable to load the properties file.. !!!", e);
		}
	}
	
	/**
	 * Resolves the properties to the actual values. <br>
	 * os.name = linux<br>
	 * ${os.name}.type = 64 bit<br>
	 * <br>
	 * will be replaced as<br>
	 * os.name = linux<br>
	 * linux.type = 64 bit<br>
	 * 
	 * @param properties
	 * @since 4.0
	 */
	private void resolve(Properties properties)
	{
		try
		{
			logger.trace("Going to resolve the properties: "+properties);
			final List<String> properties2remove = new ArrayList<String>();
			final Map<String, Object> properties2add = new HashMap<String, Object>();
			for (Entry<Object, Object> entry : properties.entrySet())
			{
				final String actualProperty = entry.getKey().toString();
				final String actualValue = entry.getValue().toString();
				final String resolvedProperty = replaceThisWithFileName(resolve(actualProperty.toString(), properties));
				final String resolvedValue = resolve(actualValue.toString(), properties);
				if (!StringUtils.equals(actualProperty, resolvedProperty) || !StringUtils.equals(actualValue, resolvedValue))
				{
					properties2add.put(resolvedProperty, resolvedValue);
					properties2remove.add(actualProperty);
				}
			}
			removeProperties(properties, properties2remove);
			properties.putAll(properties2add);
			logger.trace("Properties after resolve: "+properties);
		}
		catch (Exception e)
		{
			logger.trace(e.getMessage(), e);
		}
	}
	
	/**
	 * Replace the properties which start with this is replaced by filename.
	 * 
	 * @param property
	 * 
	 * @return
	 * 			property
	 */
	private String replaceThisWithFileName(String property)
	{
		final String fileName = new File(applicationProperties.getPropertyFileName()).getName();
		return (isNotNull(property) && property.startsWith(THIS)) ? fileName.substring(0, fileName.lastIndexOf(DOT)) + property.substring(4, property.length()) : property;
	}

	/**
	 * Resolves the properties to the actual values. <br>
	 * os.name = linux<br>
	 * ${os.name}.type = 64 bit<br>
	 * <br>
	 * will be replaced as<br>
	 * os.name = linux<br>
	 * linux.type = 64 bit<br>
	 * 
	 * @param property
	 * @param properties
	 */
	private String resolve(String property, Properties properties)
	{
		matcher.reset(property.toString());
		if (matcher.matches())
		{
			final String name = matcher.groupCount() > 0 ? matcher.group(1) : null; 
			Object value = isNull(properties.get(name)) ? System.getenv(name) : properties.get(name);
			value = isNull(value) ? value : resolve(value.toString(), properties);
			property = property.replaceAll(FIRST_PART + name + SECOND_PART, isNull(value) ? SPACE : value.toString());
			return resolve(property, properties);
		}
		return property;
	}
	
	/**
	 * Removes the regex based properties.
	 * 
	 * @param properties
	 * @param properties2remove
	 */
	private void removeProperties(Properties properties, List<String> properties2remove)
	{
		for (String property : properties2remove)
		{
			properties.remove(property);
		}
	}
	
	/**
	 * Gets the application properties.
	 * 
	 * @return the applicationProperties
	 */
	public ApplicationProperties getApplicationProperties()
	{
		return applicationProperties;
	}
	
	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws PropertyFileLoaderException
	 *             the property file loader exception
	 */
	public static void main(String[] args) throws PropertyFileLoaderException
	{
		VarraProperties.setProperty(LoggerConstants.LOG_CLASS_FQ_NAME, ConsoleLogger.class.getName());
		ApplicationProperties applicationProperties2 = new ApplicationProperties("./conf/my.properties");
		VarraProperties.addApplicationProperties(applicationProperties2);
		VarraProperties.setProperty(ApplicationProperties.class.getSimpleName(), applicationProperties2);
		//PropertyFileMonitor monitor = new PropertyFileMonitor(applicationProperties2);
		//monitor.start();
		//System.out.println("Props: "+monitor.getProperties());
		//monitor.shutdown();
		//System.out.println("Props: "+monitor.getProperties());
		System.out.println("Props: "+applicationProperties2.getProperties());
		/*System.out.println("prop: "+VarraProperties.getProperty("PWD"));
		System.out.println("Props: "+applicationProperties2.getProperties());
		ApplicationProperties property = (ApplicationProperties) VarraProperties.getProperty(ApplicationProperties.class.getSimpleName());
		System.out.println(property.getProperties());*/
		
	}
}
