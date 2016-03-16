/*
 * utils4j - SpringContextProvider.java, Aug 27, 2013 10:25:58 AM
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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.varra.log.Logger;
import com.varra.util.ObjectUtils;
import com.varra.util.StringUtils;
import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;

/**
 * This is introduced in order to reduce the sprint context dependency jar when
 * {@link SpringContext#isSpringContextEnabled()} is false.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 1.0
 * 
 */
@InterfaceAudience.LimitedPrivate("SpringContext.class")
@InterfaceStability.Evolving
final class SpringContextProvider
{
	
	/** The logger to log the debugging messages as application runs. */
	private static Logger logger;
	
	/** The application context. */
	private static ApplicationContext appContext;
	
	/** The context initialization started. */
	private static boolean initializationStarted = false;
	
	/** The last loaded. */
	private static long lastLoaded;
	
	/** If or not this is first time. */
	private static boolean isFirstTime = true;
	
	/**
	 * Instantiates a new spring context provider.
	 */
	private SpringContextProvider()
	{
		super();
	}
	
	/**
	 * Gets the application context. <br>
	 * First check whether the configFile is set or not.
	 * 
	 * @return the application context
	 *         {@link #setConfigFileName(configFileName)};<br>
	 *         context = {@link #getContext()};
	 */
	public static ApplicationContext getContext()
	{
		if (ObjectUtils.isNull(appContext) && !initializationStarted)
		{
			logger = Logger.getLogger(SpringContext.class);
			initContext();
			SpringContext.setSpringContextEnabled(Boolean.TRUE);
		}
		return appContext;
	}
	
	/**
	 * Inits the context.
	 */
	private static synchronized void initContext()
	{
		initializationStarted = true;
		
		if (ObjectUtils.isNull(appContext))
		{
			try
			{
				if (StringUtils.isNotBlank(SpringContext.getConfigFileName()))
				{
					logger.info("Initialising the App context: " + SpringContext.getConfigFileName());
					appContext = new FileSystemXmlApplicationContext(SpringContext.getConfigFileName());
					logger.info("Successfully Initialized the App context.");
					lastLoaded = System.currentTimeMillis();
				}
				else if (isFirstTime)
				{
					logger.error("Context file is not loaded, as Context file is missing.");
					logger.warn("Context file is not loaded, as Context file is missing.");
					isFirstTime = false;
				}
			}
			catch (Exception e)
			{
				logger.error(e);
			}
		}
		
		initializationStarted = false;
	}
	
	/**
	 * Injected from the class "ApplicationContextProvider" which is
	 * automatically loaded during Spring-Initialization.
	 * 
	 * @param applicationContext
	 *            the new application context
	 */
	public static void setContext(final ApplicationContext applicationContext)
	{
		if (ObjectUtils.isNotNull(applicationContext))
		{
			appContext = applicationContext;
			SpringContext.setSpringContextEnabled(Boolean.TRUE);
		}
	}
	
	/**
	 * Gets the last loaded.
	 * 
	 * @return the lastLoaded
	 */
	public static long lastLoaded()
	{
		return lastLoaded;
	}
	
	/**
	 * Refreshes the {@link SpringContext}.
	 */
	public static void refresh()
	{
		try
		{
			logger.info("Reloading the App context: " + SpringContext.getConfigFileName());
			if (ObjectUtils.isInstanceOf(appContext, AbstractApplicationContext.class))
			{
				((AbstractApplicationContext) appContext).refresh();
			}
			logger.info("Successfully Reloaded the App context: " + SpringContext.getConfigFileName());
			lastLoaded = System.currentTimeMillis();
		}
		catch (Exception e)
		{
			e.printStackTrace(System.err);
			logger.error("Context file is not reloaded, got error while reloading the config file.", e);
		}
	}
	
	/**
	 * Shutdowns the {@link SpringContextProvider} safely..
	 */
	public static void shutdown()
	{
		if (ObjectUtils.isNotNull(appContext))
		{
			logger.info("Destroying the App context: " + SpringContext.getConfigFileName());
			if (appContext instanceof AbstractApplicationContext)
			{
				((AbstractApplicationContext) appContext).destroy();
			}
			logger.info("Successfully Destroyed the App context: " + SpringContext.getConfigFileName());
		}
	}
}
