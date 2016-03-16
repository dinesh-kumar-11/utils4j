/*
 * utils4j - RapidMBean.java, Aug 16, 2015 2:57:40 PM
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

import javax.management.NotificationListener;

import com.varra.jmx.exception.MBeanRegistrationException;
import static com.varra.props.VarraProperties.*;

/**
 * An MBean ported to the Rapid.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
public class RapidMBean extends MBean
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1294509755617520064L;
	
	/**
	 * Instantiates a new rapid m bean.
	 *
	 * @param mBeanRef
	 *            the m bean ref
	 * @param type
	 *            the type
	 * @param name
	 *            the name
	 * @param warName
	 *            the war name
	 * @throws MBeanRegistrationException
	 *             the m bean registration exception
	 */
	public RapidMBean(Object mBeanRef, String type, String name, String warName) throws MBeanRegistrationException
	{
		super(mBeanRef, RAPID_TYPE, type, name, warName, null);
	}
	
	/**
	 * Instantiates a new rapid mBean with the {@link NotificationListener},
	 * please be checked whether the mBeanRef u r providing is compatible before
	 * u do this, if not please use the other constructor.
	 *
	 * @param mBeanRef
	 *            the m bean ref
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
	public RapidMBean(Object mBeanRef, String type, String name, String warName,
			NotificationListener notificationListener) throws MBeanRegistrationException
	{
		super(mBeanRef, RAPID_TYPE, type, name, warName, notificationListener);
	}
}
