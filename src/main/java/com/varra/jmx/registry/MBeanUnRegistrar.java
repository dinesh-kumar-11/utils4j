/*
 * utils4j - MBeanUnRegistrar.java, Mar 7, 2011 5:12:30 PM
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

import com.varra.log.Logger;

import com.varra.jmx.exception.MBeanRegistrationException;
import com.varra.jmx.mbean.MBean;


/**
 * An {@link MBeanUnRegistrar} which Un registers the specified MBean from the
 * {@link MBeanManager}.<br>
 * Implemented for type safety and easy integration with the Spring or any other
 * tools.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
public class MBeanUnRegistrar
{
	
	/** The logger. */
	private final Logger logger; 
	
	/**
	 * Instantiates a new m bean registrar.
	 * 
	 * @param mBean
	 *            the m bean
	 */
	public MBeanUnRegistrar(MBean mBean)
	{
		logger = Logger.getLogger(this.getClass().getName());
		
		//MBean registration ...
        final MBeanRegistration manager = new MBeanManager();
        try
        {
            if (manager.isRegistered(mBean))
            {
                logger.info("Unregistering the provided MBean: "+mBean);
                manager.unRegister(mBean);
            }
            logger.info("Successfully UnRegistered the MBean:"+mBean);
        }
        catch (MBeanRegistrationException e)
        {
            logger.error("Exception in UnRegistering the MBean: "+mBean, e.getCause());
        }
	}
	
}
