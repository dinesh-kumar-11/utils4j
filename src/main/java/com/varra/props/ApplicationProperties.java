/*
 * MWatchEventProcessingEngine - ApplicationProperties.java, Apr 6, 2013 1:15:09 PM
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

import static com.varra.props.Constants.DEF_MONITORING_INTERVAL;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;
import com.varra.exception.PropertyFileLoaderException;
import com.varra.util.ObjectUtils;
import com.varra.util.RegexUtils;
import com.varra.util.StringUtils;

/**
 * This is the utility class to provide the application properties file details
 * to be loaded for application.<br>
 * <br>
 * Contains the properties file name and the flag to enable/disable the
 * monitoring functionality. <br>
 * <br>
 * Can add/remove the {@link PropertyChangeListener}s using
 * {@link VarraProperties#registerPropertyChangeListener(PropertyChangeListener, String...)} and 
 * {@link com.varra.props.VarraProperties#unregisterPropertyChangeListener(PropertyChangeListener)}
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class ApplicationProperties implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8117224739278087026L;
	
	/** The property file name. */
	final private String propertyFileName;
	
	/** If or not this is monitoring enabled. */
	final private boolean isMonitoringEnabled;
	
	/** The interval. */
	final private Integer interval;
	
	/** The property file. */
	final private File propertyFile;
	
	/** The properties. */
	private Properties properties = new Properties();
	
	/** The listeners. */
	private final Set<PropertyChangeListener> listeners;

	/** The initial loading. */
	private boolean initialLoading = true;
	
	/**
	 * Instantiates a new application properties with the parameters given.
	 * 
	 * @param propertyFileName
	 *            the property file name
	 * @throws PropertyFileLoaderException
	 *             the property file loader exception
	 */
	public ApplicationProperties(String propertyFileName) throws PropertyFileLoaderException
	{
		this(propertyFileName, false);
	}
	
	/**
	 * Instantiates a new application properties with the parameters given.
	 * 
	 * @param propertyFileName
	 *            the property file name
	 * @param isMonitoringEnabled
	 *            the is monitoring enabled
	 * @throws PropertyFileLoaderException
	 *             the property file loader exception
	 */
	public ApplicationProperties(String propertyFileName, boolean isMonitoringEnabled) throws PropertyFileLoaderException
	{
		this(propertyFileName, isMonitoringEnabled, DEF_MONITORING_INTERVAL);
	}
	
	/**
	 * Instantiates a new application properties with the parameters given.
	 * 
	 * @param propertyFileName
	 *            the property file name
	 * @param isMonitoringEnabled
	 *            the is monitoring enabled
	 * @param interval
	 *            the interval
	 * @throws PropertyFileLoaderException
	 *             the property file loader exception
	 */
	public ApplicationProperties(String propertyFileName, boolean isMonitoringEnabled, int interval) throws PropertyFileLoaderException
	{
		super();
		
		listeners = new HashSet<PropertyChangeListener>();
		propertyFileName = RegexUtils.resolve2Env(propertyFileName);
		if (isValid(propertyFileName))
		{
			this.propertyFileName = propertyFileName;
			this.isMonitoringEnabled = isMonitoringEnabled;
			this.interval = interval > 0 ? interval : DEF_MONITORING_INTERVAL;
			this.propertyFile = new File(propertyFileName);
		}
		else
		{
			throw new PropertyFileLoaderException("Received an invalid properties file to load.. !"+propertyFileName);
		}
	}
	
	/**
	 * Gets the property file name.
	 * 
	 * @return the propertyFileName
	 */
	public String getPropertyFileName()
	{
		return propertyFileName;
	}
	
	/**
	 * Checks if is monitoring enabled.
	 * 
	 * @return the isMonitoringEnabled
	 */
	public boolean isMonitoringEnabled()
	{
		return isMonitoringEnabled;
	}
	
	/**
	 * Gets the properties file monitor interval. This matters if the
	 * 
	 * @return the interval {@link #isMonitoringEnabled()} enabled (i.e. true).
	 */
	public Integer getInterval()
	{
		return interval;
	}
	
	/**
	 * Checks if is valid application properties.
	 * 
	 * @param propertyFileName
	 *            the property file name
	 * @return true, if is valid application properties
	 */
	@InterfaceAudience.LimitedPrivate("props")
	@InterfaceStability.Evolving
	public boolean isValid(final String propertyFileName)
	{
		boolean isValid = false; // Pessimistic ;)
		if (propertyFileName != null && propertyFileName.trim().length() > 0 && new File(propertyFileName).exists())
		{
			isValid = true;
		}
		return isValid;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((propertyFileName == null) ? 0 : propertyFileName.hashCode());
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof ApplicationProperties))
		{
			return false;
		}
		final ApplicationProperties other = (ApplicationProperties) obj;
		if (propertyFileName == null)
		{
			if (other.propertyFileName != null)
			{
				return false;
			}
		}
		else if (!propertyFileName.equals(other.propertyFileName))
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Gets the property file.
	 * 
	 * @return the propertyFile
	 */
	public File getPropertyFile()
	{
		return propertyFile;
	}
	
	/**
	 * Gets the properties.
	 * 
	 * @return the properties
	 */
	public Properties getProperties()
	{
		return properties;
	}
	
	/**
	 * Sets the properties and update them to the {@link VarraProperties} for
	 * further notification if required.
	 * 
	 * @param properties
	 *            the properties to set
	 */
	void setProperties(Properties properties)
	{
		if (ObjectUtils.isNotNull(properties))
		{
			if (isInitialLoading())
			{
				initialLoading = false;
			}
			else
			{
				for (Entry<Object, Object> entry : properties.entrySet())
				{
					final String oldValue = this.properties.getProperty(entry.getKey().toString());
					if (ObjectUtils.isNotNull(oldValue))
					{
						if (!entry.getValue().equals(oldValue))
						{
							// Updated property.
							VarraProperties.setProperty(StringUtils.toString(entry.getKey()), entry.getValue(), this);
						}
					}
					else
					{
						// New property.
						VarraProperties.setProperty(StringUtils.toString(entry.getKey()), entry.getValue(), this);
					}
				}
				for (Entry<Object, Object> entry : this.properties.entrySet())
				{
					if (!properties.keySet().contains(entry.getKey().toString()))
					{
						//Deleted Property.
						VarraProperties.setProperty(StringUtils.toString(entry.getKey()), null, this);
					}
				}
			}
			this.properties.clear();
			this.properties.putAll(properties);
		}
	}
	
	/**
	 * Checks if is initial loading.
	 * 
	 * @return true, if is initial loading
	 */
	private boolean isInitialLoading()
	{
		return initialLoading;
	}

	/**
	 * Adds the property change listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public synchronized void addPropertyChangeListener(PropertyChangeListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Removes the property change listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public synchronized void removePropertyChangeListener(PropertyChangeListener listener)
	{
		listeners.remove(listener);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("ApplicationProperties [propertyFileName=");
		builder.append(propertyFile.getAbsolutePath());
		builder.append(", isMonitoringEnabled=");
		builder.append(isMonitoringEnabled);
		if (isMonitoringEnabled)
		{
			builder.append(", interval=");
			builder.append(interval);
		}
		builder.append("]");
		
		return builder.toString();
	}
}
