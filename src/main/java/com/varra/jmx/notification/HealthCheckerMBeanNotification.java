/*
 * utils4j - HealthCheckerMBeanNotification.java, May 5, 2011 1:07:27 PM
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
package com.varra.jmx.notification;

import javax.management.Notification;

/**
 * Provides definitions of the HealthCheck change notifications sent by MBeans.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
public class HealthCheckerMBeanNotification extends Notification
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4154365018102599123L;
	
	/** The Constant TYPE. */
	private static final String TYPE = "Health";
	
	/** The status. */
	private final boolean status;
	
	/**
	 * Instantiates a new health checker m bean notification.
	 * 
	 * @param source
	 *            the source
	 * @param status
	 *            the status
	 * @param sequenceNumber
	 *            the sequence number
	 */
	public HealthCheckerMBeanNotification(Object source, boolean status, long sequenceNumber)
	{
		super(TYPE, source, sequenceNumber);
		this.status = status;
	}
	
	/**
	 * Instantiates a new health checker m bean notification.
	 * 
	 * @param source
	 *            the source
	 * @param status
	 *            the status
	 * @param sequenceNumber
	 *            the sequence number
	 * @param message
	 *            the message
	 */
	public HealthCheckerMBeanNotification(Object source, boolean status, long sequenceNumber, String message)
	{
		super(TYPE, source, sequenceNumber, message);
		this.status = status;
	}
	
	/**
	 * Instantiates a new health checker m bean notification.
	 * 
	 * @param source
	 *            the source
	 * @param status
	 *            the status
	 * @param sequenceNumber
	 *            the sequence number
	 * @param timeStamp
	 *            the time stamp
	 */
	public HealthCheckerMBeanNotification(Object source, boolean status, long sequenceNumber, long timeStamp)
	{
		super(TYPE, source, sequenceNumber, timeStamp);
		this.status = status;
	}
	
	/**
	 * Instantiates a new health checker m bean notification.
	 * 
	 * @param source
	 *            the source
	 * @param status
	 *            the status
	 * @param sequenceNumber
	 *            the sequence number
	 * @param timeStamp
	 *            the time stamp
	 * @param message
	 *            the message
	 */
	public HealthCheckerMBeanNotification(Object source, boolean status, long sequenceNumber, long timeStamp,
			String message)
	{
		super(TYPE, source, sequenceNumber, timeStamp, message);
		this.status = status;
	}

	/**
	 * Gets the status of the Application.
	 * 
	 * @return the status
	 */
	public boolean isAlive()
	{
		return status;
	}
}
