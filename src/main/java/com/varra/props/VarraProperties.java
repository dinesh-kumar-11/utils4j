/*
 * utils4j - VarraProperties.java, Aug 16, 2015 4:38:09 PM
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

import static com.varra.util.StringUtils.*;
import static com.varra.util.WrapperUtils.*;
import static com.varra.util.ObjectUtils.*;

import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;
import com.varra.exception.VarraPropertiesException;
import com.varra.exception.PropertyFileLoaderException;
import com.varra.log.Logger;
import com.varra.spring.SpringContext;
import com.varra.util.FIFOQueue;

import com.varra.util.ShutdownMode;

/**
 * Loads the Properties from the environment, system and file, make them
 * Available to rest of the application.
 * 
 * <b>Note: It can be used to load the properties from Spring xml file or a plain
 *        .properties file.</b>
 * @author Rajakrishna V. Reddy
 * @version 1.0<br>
 *
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public final class VarraProperties implements Constants
{
	
	/** The Constant propertyFileList. */
	private final static Map<ApplicationProperties, PropertyFileMonitor> appPropsMap = new LinkedHashMap<ApplicationProperties, PropertyFileMonitor>();
	
	/** The Constant springContextEnabled. */
	//private static final boolean springContextEnabled = Boolean.parseBoolean(System.getProperty(SPRING_CONTEXT_ENABLED, "true"));
	
	/** The monitored properties. */
	private static final MonitoredProperties monitoredProperties = new MonitoredProperties();
	
	static
	{
		if (!Boolean.parseBoolean(System.getProperty(SPRING_CONTEXT_ENABLED, "true")))
		{
			System.out.println("Spring context is not enabled..!, please spcify: " + SPRING_CONTEXT_ENABLED);
		}
	}
	
	/**
	 * Instantiates a new mind tree properties.
	 */
	private VarraProperties()
	{
		super();
		
		// What can I do with this, As I have everything as static ??!!
	}
	
	/**
	 * Gets the property as generic, But be careful in taking care of TYPE while
	 * using it. <br>
	 * <br>
	 * For Best Implementation and correctness use
	 * {@link #getPropertyAsGeneric(String, Class)}.
	 *
	 * @param <T>
	 *            the generic type
	 * @param propName
	 *            the prop name
	 * @return the property as generic
	 * @see VarraProperties#getPropertyAsGeneric(String, Class)
	 * @since 3.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getPropertyAsGeneric(String propName)
	{
		return (T) getProperty(propName);
	}
	
	/**
	 * Gets the property as generic, But be careful in taking care of TYPE while
	 * using it.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param propName
	 *            the prop name
	 * @param klass
	 *            the klass
	 * @return the property as generic
	 * @since 3.0
	 */
	public static <T> T getPropertyAsGeneric(String propName, Class<T> klass)
	{
		try
		{
			final T object = VarraProperties.<T> getPropertyAsGeneric(propName);
			if (klass.isInstance(object))
			{
				return (T) object;
			}
		}
		catch (Exception e)
		{
			Logger.getLogger(VarraProperties.class).info("Error while trying to get the property: "+propName, e);
		}
		return null;
	}
	
	/**
	 * Gets the global property, it could be a {@link SpringContext} bean,
	 * application monitoring property or an environment property.
	 * 
	 * @param propName
	 *            the prop name
	 * @return the property
	 */
	public static Object getProperty(final String propName)
	{
		if (isNotBlank(propName))
		{
			final Object appValue = getAppProperty(propName);
			if (SpringContext.isSpringContextEnabled())
			{
				final Object bean = getBean(propName);
				return isNull(SpringContext.getContext()) ? appValue : isNull(bean) ? appValue : bean;
			}
			return appValue;
		}
		return null;
	}
	
	/**
	 * Gets the bean.
	 * 
	 * @param propName
	 *            the prop name
	 * @return the bean
	 */
	private static Object getBean(String propName)
	{
		try
		{
			return SpringContext.getContext().getBean(propName);
		}
		catch (Exception e)
		{
			// What to do.!
		}
		return null;
	}
	
	/**
	 * Gets the application property.
	 * 
	 * @param propName
	 *            the prop name
	 * @return the application property
	 */
	public static Object getAppProperty(String propName)
	{
		final Object value = monitoredProperties.getProperty(propName);
		if (isNull(value))
		{
			final String monitorableValue = getMonitorableProperty(propName);
			return isNull(monitorableValue) ? getSystemProperty(propName) : monitorableValue;
		}
		return value;
	}
	
	/**
	 * Gets the application property.
	 * 
	 * @param propName
	 *            the prop name
	 * @return the application property
	 */
	public static Object getAppProperty(String fileName, String propName)
	{
		if (isNotBlank(fileName) && isNotBlank(propName))
		{
			final Set<Entry<ApplicationProperties, PropertyFileMonitor>> entrySet = appPropsMap.entrySet();
			for (Entry<ApplicationProperties, PropertyFileMonitor> entry : entrySet)
			{
				final ApplicationProperties applicationProperties = entry.getKey();
				if (applicationProperties.getPropertyFileName().equals(fileName))
				{
					return applicationProperties.getProperties().getProperty(propName);
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets the application property.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the application properties
	 */
	public static Map<? extends Object, ? extends Object> getAppProperties(String fileName)
	{
		if (isNotBlank(fileName))
		{
			final Set<Entry<ApplicationProperties, PropertyFileMonitor>> entrySet = appPropsMap.entrySet();
			for (Entry<ApplicationProperties, PropertyFileMonitor> entry : entrySet)
			{
				final ApplicationProperties applicationProperties = entry.getKey();
				if (applicationProperties.getPropertyFileName().equals(fileName))
				{
					return Collections.unmodifiableMap(applicationProperties.getProperties());
				}
			}
		}
		return Collections.emptyMap();
	}
	
	/**
	 * Gets the monitorable property.
	 * 
	 * @param propName
	 *            the prop name
	 * @return the monitorable property
	 */
	private static String getMonitorableProperty(String propName)
	{
		final Set<Entry<ApplicationProperties, PropertyFileMonitor>> entrySet = appPropsMap.entrySet();
		for (Entry<ApplicationProperties, PropertyFileMonitor> entry : entrySet)
		{
			final ApplicationProperties applicationProperties = entry.getKey();
			final String value = applicationProperties.getProperties().getProperty(propName);
			if (isNotNull(value))
			{
				return value;
			}
		}
		return null;
	}
	
	/**
	 * Gets the system property.
	 * 
	 * @param propName
	 *            the prop name
	 * @return the system property
	 */
	public static String getSystemProperty(final String propName)
	{
		final String value = System.getProperty(propName);
		return isBlank(value) ? System.getenv(propName) : value;
	}
	
	/**
	 * Gets the property value if it is available, else returns the defValue
	 * provided.
	 * 
	 * @param propName
	 *            the prop name
	 * @param defValue
	 *            the default value
	 * @return the property
	 */
	public static Object getProperty(final String propName, final Object defValue)
	{
		final Object value = getProperty(propName);
		return value == null ? defValue : value;
	}
	
	/**
	 * Gets the property value if it is available, else returns the defValue
	 * provided.
	 * 
	 * Note: <b> start using {@link com.varra.props.VarraProperties#getWrapperProperty(String, Object)}, as it is @Deprecated.</b>
	 * @param propName
	 *            the prop name
	 * @param defValue
	 *            the default value
	 * @return the property
	 */
	@Deprecated
	public static int getIntegerProperty(final String propName, final int defValue)
	{
		try
		{
			final Object value = getProperty(propName);
			return value == null ? defValue : Integer.parseInt(value.toString());
		}
		catch (Exception e)
		{
			// WHat should I do .. !!!
		}
		return defValue;
	}
	
	/**
	 * Gets the property value if it is available, else returns the defValue
	 * provided.
	 * 
	 * Note: <b> start using {@link com.varra.props.VarraProperties#getWrapperProperty(String, Object)}, as it is @deprecated.</b>
	 * @param propName
	 *            the prop name
	 * @param defValue
	 *            the default value
	 * @return the property
	 */
	@Deprecated
	public static boolean getBooleanProperty(final String propName, final boolean defValue)
	{
		try
		{
			final Object value = getProperty(propName);
			return isNull(value) ? defValue : Boolean.parseBoolean(value.toString());
		}
		catch (Exception e)
		{
			// WHat should I do .. !!!
		}
		return defValue;
	}
	
	/**
	 * Gets the property value if it is available, else returns the defValue
	 * provided.
	 *
	 * @param <T>
	 *            the generic type
	 * @param propName
	 *            the prop name
	 * @param defValue
	 *            the default value
	 * @return the property
	 * @since 3.0
	 */
	public static <T> T getWrapperProperty(final String propName, final T defValue)
	{
		final Object value = getProperty(propName);
		return to(isNull(value) ? null : value.toString(), defValue);
	}
	
	/**
	 * Gets the global property, it could be a {@link SpringContext} bean,
	 * application monitoring property or an environment property which starts
	 * with the given prefix.
	 * 
	 * @param prefix
	 *            the prefix
	 * @return the list
	 */
	public static List<String> startsWith(final String prefix)
	{
		final List<String> objects = new FIFOQueue<String>();
		final Set<Entry<ApplicationProperties, PropertyFileMonitor>> entrySet = appPropsMap.entrySet();
		for (Entry<ApplicationProperties, PropertyFileMonitor> entry : entrySet)
		{
			final ApplicationProperties applicationProperties = entry.getKey();
			final Properties properties = applicationProperties.getProperties();
			final Enumeration<?> names = properties.propertyNames();
			while (names.hasMoreElements())
			{
				final String key = names.nextElement().toString();
				if (key != null && key.startsWith(prefix))
				{
					objects.add(key);
				}
			}
			
		}
		return objects;
	}
	
	/**
	 * Sets the property.
	 * 
	 * @param propName
	 *            the prop name
	 * @param value
	 *            the value
	 */
	public static void setProperty(final String propName, final Object value)
	{
		monitoredProperties.setProperty(propName, value);
	}
	
	/**
	 * Sets the system property.
	 * 
	 * @param propName
	 *            the prop name
	 * @param value
	 *            the value
	 * @since 3.0
	 */
	public static void setSystemProperty(final String propName, final String value)
	{
		System.setProperty(propName, value);
	}
	
	/**
	 * Sets the property.
	 * 
	 * @param propName
	 *            the prop name
	 * @param value
	 *            the value
	 * @param source
	 *            the source
	 */
	public static void setProperty(final String propName, final Object value, Object source)
	{
		monitoredProperties.setProperty(propName, value, source);
	}
	
	/**
	 * Gets the property file list, contains only the valid.
	 * 
	 * @return the propertyFileList {@link ApplicationProperties} entries.
	 */
	public static Set<ApplicationProperties> getPropertyFileList()
	{
		return appPropsMap.keySet();
	}
	
	// //////////////////////////////////////////////////////////////////
	// / Property file Monitor and other utility methods!! ////
	// //////////////////////////////////////////////////////////////////
	/**
	 * Adds the application properties.
	 * 
	 * @param properties
	 *            the properties
	 * @return true, if successful
	 * @throws PropertyFileLoaderException
	 *             the property file loader exception
	 */
	public static synchronized boolean addApplicationProperties(final ApplicationProperties properties) throws PropertyFileLoaderException
	{
		if (isNotNull(properties))
		{
			final PropertyFileMonitor monitor = new PropertyFileMonitor(properties);
			monitor.start();
			appPropsMap.put(properties, monitor);
		}
		return true;
	}
	
	/**
	 * Removes the application properties.
	 * 
	 * @param properties
	 *            the properties
	 * @return true, if successful
	 */
	public static synchronized boolean removeApplicationProperties(final ApplicationProperties properties)
	{
		if (isNotNull(properties))
		{
			final PropertyFileMonitor monitor = appPropsMap.remove(properties);
			if (isNotNull(monitor) && properties.isMonitoringEnabled())
			{
				monitor.shutdown(ShutdownMode.GRACEFUL_SHUTDOWN);
			}
		}
		return true;
	}
	
	/**
	 * Register property change listener.
	 *
	 * @param listener
	 *            the listener
	 * @param properties
	 *            the properties
	 * @throws VarraPropertiesException
	 *             the mind tree properties exception
	 */
	public static void registerPropertyChangeListener(PropertyChangeListener listener, String... properties) throws VarraPropertiesException
	{
		monitoredProperties.registerPropertyChangeListener(listener, properties);
	}
	
	/**
	 * Unregister property change listener.
	 * 
	 * @param property
	 *            the property
	 * @param listener
	 *            the listener
	 */
	public static void unregisterPropertyChangeListener(String property, PropertyChangeListener listener)
	{
		monitoredProperties.unregisterPropertyChangeListener(property, listener);
	}
	
	/**
	 * Unregister property change listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public static void unregisterPropertyChangeListener(PropertyChangeListener listener)
	{
		monitoredProperties.unregisterPropertyChangeListener(listener);
	}
	
	/**
	 * Starts monitoring thread, if not started earlier.
	 * @since 3.0 this is automated, whenever listeners are registered, this will be started, hence no need to call it manually.
	 */
	@Deprecated
	public static void startMonitoring()
	{
		monitoredProperties.startMonitoring();
	}
}
