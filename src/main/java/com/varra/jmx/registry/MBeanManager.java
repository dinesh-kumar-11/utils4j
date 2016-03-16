/*
 * utils4j - MBeanManager.java, Feb 3, 2011 4:21:39 PM
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
package com.varra.jmx.registry;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.varra.log.Logger;

import com.varra.jmx.exception.MBeanRegistrationException;
import com.varra.jmx.mbean.MBean;

/**
 * <p>
 * This is the main class for MBean manipulation on the agent side. It contains
 * the methods necessary for the creation, registration, and deletion of MBeans
 * as well as the access methods for registered MBeans.
 * <p>
 * This is the core component of the JMXWrapper infrastructure.
 * </p>
 * <p>
 * When an MBean is registered or unregistered in the MBean server a
 * {@link javax.management.MBeanServerNotification MBeanServerNotification}
 * Notification is emitted.
 * <p>
 * Implements the {@link MBeanRegistration} to support the registration, and
 * deletion of MBeans.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
public class MBeanManager implements MBeanRegistration
{
	
	/** The logger. */
	final private Logger logger;
	
	/**
	 * Instantiates a new MBean manager.
	 */
	public MBeanManager()
	{
		logger = Logger.getLogger(this.getClass().getName());
		logger.info("Initializing the MBeanManager");
		logger.info("Initialized the MBeanManager Successfully.");
	}
	
	
	/* (non-Javadoc)
	 * @see com.varra.jmx.registry.MBeanRegistration#register(com.varra.jmx.mbean.MBean)
	 */
	public boolean register(MBean mBean) throws MBeanRegistrationException
	{
		if (mBean != null)
		{
			final String mBeanStrName = mBean.getPort()+":type="+mBean.getType()+",name="+mBean.getName();
			
			try
			{
				logger.info("MBean registration is in progress for: "+mBeanStrName);
				
				/** Get the Platform MBean Server */
				final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
				
				/** Construct the ObjectName for the MBean we will register */
				final ObjectName mBeanName = new ObjectName(mBeanStrName);
				
				/** Register the MBean */
				mBeanServer.registerMBean(mBean.getMBeanRef(), mBeanName);
				
				logger.info("MBean has been registered successfully for: "+ mBeanStrName);
				
				if (mBean.getNotificationListener() != null)
				{
					try
					{
						logger.info("notificationListener registration is in progress for: "+mBeanStrName);
						mBeanServer.addNotificationListener(mBeanName, mBean.getNotificationListener(), null, null);
						logger.info("notificationListener registration is done for: "+mBeanStrName);
					}
					catch (InstanceNotFoundException e)
					{
						logger.info("Error in notificationListener registration, cause: "+e.getMessage());
					}
				}
				return true;
			}
			catch (InstanceAlreadyExistsException e)
			{
				throw new MBeanRegistrationException(e);
			}
			catch (javax.management.MBeanRegistrationException e)
			{
				throw new MBeanRegistrationException(e);
			}
			catch (NotCompliantMBeanException e)
			{
				throw new MBeanRegistrationException(e);
			}
			catch (MalformedObjectNameException e)
			{
				throw new MBeanRegistrationException(e);
			}
			catch (NullPointerException e)
			{
				throw new MBeanRegistrationException(e);
			}
		}
		else
		{
			throw new MBeanRegistrationException("Provided MBean instance is a null. MBean: "+mBean);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.varra.jmx.registry.MBeanRegistration#unRegister(com.varra.jmx.mbean.MBean)
	 */
	public boolean unRegister(MBean mBean) throws MBeanRegistrationException
	{
		final String mBeanStrName = mBean.getPort()+":type="+mBean.getType()+",name="+mBean.getName();
		
		try
		{
			logger.info("MBean unRegistration is in progress for: "+mBeanStrName);
			
			/** Get the Platform MBean Server */
			final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
			
			/** Construct the ObjectName for the MBean we will register */
			final ObjectName mBeanName = new ObjectName(mBeanStrName);
			
			/** UnRegister the MBean */
			mBeanServer.unregisterMBean(mBeanName);
			
			logger.info("MBean has been UnRegistered successfully for: "+ mBeanStrName);
			
			return true;
		}
		catch (javax.management.MBeanRegistrationException e)
		{
			throw new MBeanRegistrationException(e);
		}
		catch (MalformedObjectNameException e)
		{
			throw new MBeanRegistrationException(e);
		}
		catch (NullPointerException e)
		{
			throw new MBeanRegistrationException(e);
		}
		catch (InstanceNotFoundException e)
		{
			throw new MBeanRegistrationException(e);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.varra.jmx.registry.MBeanRegistration#isRegistered(com.varra.jmx.mbean.MBean)
	 */
	public boolean isRegistered(MBean mBean) throws MBeanRegistrationException
	{
		final String mBeanStrName = mBean.getPort()+":type="+mBean.getType()+",name="+mBean.getName();
		
		try
		{
			/** Get the Platform MBean Server */
			final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
			
			/** Construct the ObjectName for the Hello MBean we will register */
			final ObjectName mBeanName = new ObjectName(mBeanStrName);
			
			/** UnRegister the MBean */
			return mBeanServer.isRegistered(mBeanName);
		}
		catch (MalformedObjectNameException e)
		{
			throw new MBeanRegistrationException(e);
		}
		catch (NullPointerException e)
		{
			throw new MBeanRegistrationException(e);
		}
	}
}
