/*
 * utils4j - HealthChecker.java, May 5, 2011 8:55:32 PM
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

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

import com.varra.log.Logger;

import com.varra.jmx.notification.HealthCheckerMBeanNotification;

/**
 * The MBean Implementation of the {@link HealthCheckerMBean}, provides the
 * actual definition of the health Checking. Needs to be used by the MBean
 * Manager to register/deRegister it to Application JMX Environment.<br>
 * You can use the {@link com.varra.jmx.registry.MBeanRegistration}, so the MBeans
 * will be registered in the standard MBean server deployed with the JRE.<br>
 * Emitts the {@link com.varra.jmx.notification.HealthCheckerMBeanNotification}
 * with the current status of the application.
 * 
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
public class HealthChecker extends NotificationBroadcasterSupport implements HealthCheckerMBean
{
	
	/** The health checker. */
	private static HealthChecker healthChecker;
	
	/** The logger. */
	final private Logger logger;
	
	/** The is alive. */
	private boolean isAlive;
	
	/** The sequence number used for MBean {@link Notification} Emittion. */
	private int sequenceNumber;
	
	/**
	 * Instantiates the new health checker.
	 */
	private HealthChecker()
	{
		logger = Logger.getLogger(this.getClass().getName());
		setAlive(true);
	}
	
	/**
	 * Gets the single instance of HealthChecker.
	 * 
	 * @return single instance of HealthChecker
	 */
	public synchronized static HealthChecker getInstance()
	{
		if (healthChecker == null)
		{
			healthChecker = new HealthChecker();
		}
		return healthChecker;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.jmx.HealthCheckerMBean#isAlive()
	 */
	public boolean isAlive()
	{
		logger.info("Invoking the isAlive stub, status: " + isAlive);
		return isAlive;
	}
	
	/**
	 * Sets the alive status based on the status of the application that it is
	 * running in. and sends the {@link HealthCheckerMBeanNotification} with the
	 * current status respectively.
	 * <p>
	 * Needs to be analyzed the status of the application based on its
	 * functionality and set this value.
	 * <p>
	 * Eg: Suppose it is running in <b>mail application, which sends a mail for
	 * every free configured interval</b> then this {@link #setAlive(boolean)}
	 * should be set to true if the <b>mail application</b> is sending the mails
	 * correctly, else {@link #setAlive(boolean)} should be set to false, as
	 * <b>mail application</b> has some issue in sending the mails.
	 * 
	 * @param isAlive
	 *            the isAlive to set
	 */
	public void setAlive(boolean isAlive)
	{
		this.isAlive = isAlive;
		
		final Notification notification = new HealthCheckerMBeanNotification(this, isAlive, sequenceNumber++,
				System.currentTimeMillis(), "Application status has been changed.");
		super.sendNotification(notification);
	}
}