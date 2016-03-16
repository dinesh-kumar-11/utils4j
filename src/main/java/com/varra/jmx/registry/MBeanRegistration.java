/*
 * utils4j - MBeanRegistration.java, Feb 3, 2011 5:53:27 PM
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

import javax.management.NotificationListener;

import com.varra.jmx.exception.MBeanRegistrationException;
import com.varra.jmx.mbean.MBean;

/**
 * Can be implemented by an MBean Manager in order to provide operations of
 * Registration and unRegistration.
 * <P>
 * It is used to register/unRegister the MBeans in the specified port, type and
 * name.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
public interface MBeanRegistration
{
	
	/**
	 * Registers the specified object as an MBean with the MBean server in the
	 * specified {@code Port} with the {@code Type} and {@code Application}
	 * provided. And Adds a {@link NotificationListener} to a registered
	 * MBean.
	 * <P>
	 * A notification emitted by the MBean will be forwarded by the MBeanServer
	 * to the listener. If the source of the notification is a reference to the
	 * MBean object, the MBean server will replace it by the MBean's ObjectName.
	 * Otherwise the source is unchanged.
	 * <p>
	 * If the any one of the details provided happen to be incorrect/null, then
	 * nothing will be registered and throws a
	 * 
	 * @param mBean
	 *            the mBean
	 * @return True if the MBean is registered successfully in the MBean server,
	 *         false otherwise.
	 * @throws MBeanRegistrationException
	 *             the m bean registration exception
	 *             {@link MBeanRegistrationException} in fact.
	 */
	public boolean register(MBean mBean) throws MBeanRegistrationException;
	
	/**
	 * Unregisters an MBean from the MBean server. The MBean is identified by
	 * its {@code Port}, the {@code Type} and {@code Application} provided. Once
	 * the method has been invoked, the MBean may no longer be accessed by its
	 * object name.
	 * <p>
	 * And Removes a listener from this MBean.
	 * 
	 * @param mBean
	 *            the m bean
	 * @return True if the MBean is unRegistered successfully in the MBean
	 *         server, false otherwise.
	 * @throws MBeanRegistrationException
	 *             the m bean registration exception if the provided mBean is a
	 *             null or not a valid one.
	 */
	public boolean unRegister(MBean mBean) throws MBeanRegistrationException;
	
	/**
	 * Checks whether an MBean, identified by its {@code Port}, the {@code Type}
	 * and {@code Application} provided, is already registered with the MBean
	 * server.
	 * 
	 * @param mBean
	 *            the m bean
	 * @return True if the MBean is already registered in the MBean server,
	 *         false otherwise.
	 * @throws MBeanRegistrationException
	 *             the m bean registration exception
	 */
	public boolean isRegistered(MBean mBean) throws MBeanRegistrationException;
}
