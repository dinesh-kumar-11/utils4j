/*
 * utils4j - MonitoredProperties.java, Apr 8, 2013 11:32:15 AM
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;
import com.varra.exception.VarraPropertiesException;
import com.varra.log.Logger;
import com.varra.util.EnhancedTimerTask;
import com.varra.util.FIFOQueue;
import com.varra.util.GlobalThread;
import com.varra.util.ObjectUtils;
import com.varra.util.StringUtils;

/**
 * A Base class for holding the monitored properties and also responsible for
 * firing the property changes.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
final class MonitoredProperties extends EnhancedTimerTask
{
	
	/** The logger to log the debugging messages as application runs. */
	//private final Logger logger = Logger.getLogger(MonitoredProperties.class);
	
	/** The Constant monitoredProperties. */
	private final transient Map<String, Object> monitoredProperties = new LinkedHashMap<String, Object>();
	
	/** The property change listeners. */
	private final transient Map<String, Set<PropertyChangeListener>> propertiesAndListeners = new LinkedHashMap<String, Set<PropertyChangeListener>>();
	
	/** The property change events. */
	private final transient List<PropertyChangeEvent> propertyChangeEvents = new FIFOQueue<PropertyChangeEvent>();
	
	/** The handed over. */
	private volatile boolean handedOver;
	
	/**
	 * Instantiates a new monitored properties.
	 */
	public MonitoredProperties()
	{
		super(MonitoredProperties.class.getSimpleName());
		setDaemon(true);
		setPeriod(20000);
		
		//logger.info("Initialized successfully.");
	}
	
	/**
	 * Gets the property.
	 * 
	 * @param propName
	 *            the prop name
	 * @return the property
	 */
	public Object getProperty(String propName)
	{
		if (StringUtils.isNotBlank(propName))
		{
			return monitoredProperties.get(propName);
		}
		return null;
	}
	
	/**
	 * Sets the property.
	 * 
	 * @param propName
	 *            the prop name
	 * @param value
	 *            the value
	 */
	public void setProperty(final String propName, final Object value)
	{
		if (StringUtils.isNotBlank(propName))
		{
			addOrUpdateProperty(propName, value, this);
		}
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
	public void setProperty(String propName, Object value, Object source)
	{
		if (StringUtils.isNotBlank(propName))
		{
			addOrUpdateProperty(propName, value, source);
		}
	}
	
	/**
	 * Adds the or update property.
	 * 
	 * @param propName
	 *            the prop name
	 * @param value
	 *            the value
	 * @param source
	 *            the source
	 */
	private synchronized void addOrUpdateProperty(final String propName, final Object value, Object source)
	{
		final Object oldValue = monitoredProperties.get(propName);
		monitoredProperties.put(propName, value);
		propertyChangeEvents.add(new PropertyChangeEvent(source, propName, oldValue, value));
	}
	
	/**
	 * Register property change listener.
	 * 
	 * @param property
	 *            the property
	 * @param listener
	 *            the listener
	 * @throws VarraPropertiesException
	 *             the mind tree properties exception
	 */
	public synchronized void registerPropertyChangeListener(PropertyChangeListener listener, String... properties) throws VarraPropertiesException
	{
		if (ObjectUtils.isNotNull(properties) && properties.length > 0 && ObjectUtils.isNotNull(listener))
		{
			for (String property : properties)
			{
				Set<PropertyChangeListener> listeners = propertiesAndListeners.get(property);
				if (ObjectUtils.isNull(listeners))
				{
					listeners = new HashSet<PropertyChangeListener>();
					propertiesAndListeners.put(property, listeners);
				}
				listeners.add(listener);
			}
			startMonitoring();
		}
		else
		{
			throw new VarraPropertiesException("Provided details are invalid, property: " + properties	+ ", listener: " + listener);
		}
	}
	
	/**
	 * Unregister property change listener.
	 * 
	 * @param property
	 *            the property
	 * @param listener
	 *            the listener
	 */
	public synchronized void unregisterPropertyChangeListener(String property, PropertyChangeListener listener)
	{
		if (StringUtils.isNotBlank(property) && ObjectUtils.isNotNull(listener))
		{
			final Set<PropertyChangeListener> listeners = propertiesAndListeners.get(property);
			if (ObjectUtils.isNotNull(listeners))
			{
				listeners.remove(listener);
				if (listeners.isEmpty())
				{
					propertiesAndListeners.remove(property);
				}
			}
		}
		if (propertiesAndListeners.isEmpty())
		{
			stopMonitoring();
		}
	}
	
	/**
	 * Unregister property change listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public synchronized void unregisterPropertyChangeListener(PropertyChangeListener listener)
	{
		if (ObjectUtils.isNotNull(listener))
		{
			for (Entry<String, Set<PropertyChangeListener>> entry : propertiesAndListeners.entrySet())
			{
				entry.getValue().remove(listener);
			}
		}
		if (propertiesAndListeners.isEmpty())
		{
			stopMonitoring();
		}
	}
	
	/**
	 * Fires the property change by looking into the PropertiesChanged List.
	 */
	private synchronized void firePropertyChange()
	{
		for (PropertyChangeEvent changeEvent : propertyChangeEvents)
		{
			final Set<PropertyChangeListener> listeners = propertiesAndListeners.get(changeEvent.getPropertyName());
			if (ObjectUtils.isNotNull(listeners))
			{
				for (PropertyChangeListener listener : listeners)
				{
					listener.propertyChange(changeEvent);
				}
			}
		}
		propertyChangeEvents.clear();
	}
	
	/**
	 * Start monitoring.
	 */
	public synchronized void startMonitoring()
	{
		if (!isHandedOver())
		{
			GlobalThread.getGlobalThread().onTimerTask(this);
			setHandedOver(true);
		}
	}
	
	/**
	 * Stop monitoring.
	 */
	public void stopMonitoring()
	{
		setFinished(true);
		setHandedOver(false);
	}
	
	/**
	 * Checks if is handed over.
	 * 
	 * @return true, if is handed over
	 */
	private boolean isHandedOver()
	{
		return handedOver;
	}
	
	/**
	 * Sets the handed over.
	 * 
	 * @param handedOver
	 *            the handedOver to set
	 */
	private void setHandedOver(boolean handedOver)
	{
		this.handedOver = handedOver;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.util.EnhancedTimerTask#run()
	 */
	@Override
	public void compute()
	{
		try
		{
			firePropertyChange();
			Logger.getLogger(MonitoredProperties.class).debug("Properties have been updated to clients successfully.");
		}
		catch (Exception e)
		{
			Logger.getLogger(MonitoredProperties.class).error("Error while updating the listeners about the property change: " + e.getMessage(), e);
		}
	}
}
