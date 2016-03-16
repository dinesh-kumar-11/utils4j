/*
 * utils4j - MBeanRegistrar.java, May 10, 2011 9:04:49 PM
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

import java.util.List;

import com.varra.log.Logger;

import com.varra.jmx.exception.MBeanRegistrationException;
import com.varra.jmx.mbean.MBean;

/**
 * An {@link MBeanRegistrar} which registers the specified MBean with the
 * {@link MBeanManager}.<br>
 * Implemented for type safety and easy integration with the Spring or any other
 * tools.<br>
 * Attempts to register the given <code>mBean</code> as an MBean with the
 * default MBeanServer. If registration fails, no exceptions are thrown.
 * Instead, failure is logged and silently accepted.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
public class MBeanRegistrar
{
	
	/** The logger. */
	private final Logger logger;
	
	/**
	 * Instantiates a new m bean registrar.<br>
	 * Attempts to register the given <code>mBean</code> as an MBean with the
	 * default MBeanServer. If registration fails, no exceptions are thrown.
	 * Instead, failure is logged and silently accepted.
	 * 
	 * @param mBean
	 *            The instance to register as MBean. Note that this instance
	 *            needs to be MBean complaint. Otherwise, registration fails
	 *            silently.
	 */
	public MBeanRegistrar(MBean mBean)
	{
		logger = Logger.getLogger(this.getClass().getName());
		
		// MBean registration ...
		final MBeanRegistration manager = new MBeanManager();
		try
		{
			if (manager.isRegistered(mBean))
			{
				logger.info("MBean is already registered ,So unregistering MBean before registering");
				manager.unRegister(mBean);
			}
			manager.register(mBean);
			logger.info("Successfully Registered the MBean:" + mBean);
		}
		catch (MBeanRegistrationException e)
		{
			logger.error("Exception in Registering the MBean: " + mBean, e.getCause());
		}
	}
	
	/**
	 * Instantiates a new mbean registrar.<br>
	 * Attempts to register the given <code>mBean</code>s as an MBean with the
	 * default MBeanServer. If registration fails, no exceptions are thrown.
	 * Instead, failure is logged and silently accepted.
	 * 
	 * @param mBeans
	 *            the m beans
	 */
	public MBeanRegistrar(List<MBean> mBeans)
	{
		logger = Logger.getLogger(this.getClass().getName());
		// MBean registration ...
		final MBeanRegistration manager = new MBeanManager();
		
		for (MBean mBean : mBeans)
		{
			try
			{
				if (manager.isRegistered(mBean))
				{
					logger.info("MBean is already registered ,So unregistering MBean before registering");
					manager.unRegister(mBean);
				}
				manager.register(mBean);
				logger.info("Successfully Registered the MBean:" + mBean);
			}
			catch (MBeanRegistrationException e)
			{
				logger.error("Exception in Registering the MBean: " + mBean, e.getCause());
			}
		}
	}
}
