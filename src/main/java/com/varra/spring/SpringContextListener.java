/*
 * utils4j - SpringContextListener.java, Dec 20, 2012 9:43:01 AM
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
package com.varra.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;
import com.varra.log.Logger;
import com.varra.spring.SpringContext;

/**
 * A Convenient class to set the {@link SpringContext} by listening to the web listener.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class SpringContextListener implements ApplicationContextAware
{
	
	/** The logger to log the debugging messages as application runs. */
	private final Logger logger;
	
	/**
	 * Instantiates a new spring context listener.
	 */
	public SpringContextListener()
	{
		logger = Logger.getLogger(SpringContextListener.class);
		logger.info("Initialized successfully.");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		SpringContext.setContext(applicationContext);
		final String[] names = SpringContext.getContext().getBeanDefinitionNames();
		logger.trace("Here goes all the beans defined in this factory.");
		for (String name : names)
		{
			logger.trace("bean: "+name);
		}
		logger.info("Spring Context has been set in successfully");
	}
}
