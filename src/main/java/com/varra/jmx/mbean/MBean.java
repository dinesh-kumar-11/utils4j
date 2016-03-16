/*
 * utils4j - MBean.java, Aug 16, 2015 2:56:42 PM
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
package com.varra.jmx.mbean;

import java.io.Serializable;

import javax.management.NotificationListener;

import com.varra.jmx.exception.MBeanRegistrationException;
import com.varra.jmx.registry.MBeanManager;

/**
 * An MBean with the required details to be registered/unRegistered to the.
 * 
 * {@link MBeanManager}.<br>
 * Can be subclassed to provide more specific MBeans.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 */
public class MBean implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2244325137456735890L;
	
	/** The m bean ref. */
	final protected Object mBeanRef;
	
	/** The port. */
	final protected String port;
	
	/** The type. */
	final protected String type;
	
	/** The name. */
	final protected String name;
	
	/** The war name. */
	final protected String warName;
	
	/** The notification listener. */
	final protected NotificationListener notificationListener;
	
	/**
	 * Instantiates a new m Bean.
	 *
	 * @param mBeanRef
	 *            the m bean ref
	 * @param port
	 *            the port
	 * @param type
	 *            the type
	 * @param name
	 *            the name
	 * @param warName
	 *            the war name
	 * @param notificationListener
	 *            the notification listener
	 * @throws MBeanRegistrationException
	 *             the m bean registration exception
	 */
	public MBean(final Object mBeanRef, final String port, final String type, final String name, final String warName,
			final NotificationListener notificationListener) throws MBeanRegistrationException
	{
		super();
		
		this.mBeanRef = mBeanRef;
		this.port = port;
		this.type = type;
		this.name = name;
		this.warName = warName;
		this.notificationListener = notificationListener;
		
		if (!(mBeanRef != null && isValid(port) && isValid(type) && isValid(name) && isValid(warName)))
		{
			throw new MBeanRegistrationException("Provided MBean contetnts are inValid, might be null or empty, Please check, MBean: "+this.toString());
		}
	}
	
	/**
	 * Checks if is valid.
	 * 
	 * @param param
	 *            the param
	 * @return true, if is valid
	 */
	public boolean isValid(String param)
	{
		return param != null && param.trim().length() >0;
	}
	
	/**
	 * Gets the m bean ref.
	 * 
	 * @return the mBeanRef
	 */
	public Object getMBeanRef()
	{
		return mBeanRef;
	}
	
	/**
	 * Gets the port.
	 * 
	 * @return the port
	 */
	public String getPort()
	{
		return port;
	}
	
	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}
	
	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Gets the war name.
	 * 
	 * @return the warName
	 */
	public String getWarName()
	{
		return warName;
	}
	
	/**
	 * Gets the notification listener.
	 * 
	 * @return the notificationListener
	 */
	public NotificationListener getNotificationListener()
	{
		return notificationListener;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("MBean [mBeanRef=");
		builder.append(mBeanRef);
		builder.append(", port=");
		builder.append(port);
		builder.append(", type=");
		builder.append(type);
		builder.append(", Applicaton name=");
		builder.append(name);
		builder.append(", war Name=");
		builder.append(warName);
		builder.append(", notificationListener=");
		builder.append(notificationListener);
		builder.append("]");
		
		return builder.toString();
	}
}
