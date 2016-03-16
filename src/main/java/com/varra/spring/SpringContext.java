/*
 * utils4j - SpringContext.java, Apr 22, 2013 11:05:54 AM
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

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;
import com.varra.jmx.mbean.SpringContextLoader;
import com.varra.util.StringUtils;

/**
 * Provides the Spring Application context based on the behavior of the
 * application.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
public class SpringContext
{
	
	/** The config file name. */
	private static String configFileName;
	
	/** The spring context enabled. */
	private static Boolean springContextEnabled = Boolean.FALSE;
	
	/**
	 * Instantiates a new spring context.
	 */
	private SpringContext()
	{
		super();
		// What can I do with this, As I have everything as static ??!!
	}
	
	/**
	 * Gets the application context. <br>
	 * First check whether the configFile is set or not.
	 * 
	 * @return the application context
	 *         {@link #setConfigFileName(String)};<br>
	 *         context = {@link #getContext()};
	 */
	public static org.springframework.context.ApplicationContext getContext()
	{
		return SpringContextProvider.getContext();
	}
	
	/**
	 * Sets the config file name.
	 * 
	 * @param configFile
	 *            the new config file name
	 */
	public static void setConfigFileName(final String configFile)
	{
		if (StringUtils.isNotBlank(configFile))
		{
			configFileName = configFile;
			configFileName = configFileName.startsWith("file") ? configFileName : "file:" + configFileName;
			
			setSpringContextEnabled(Boolean.TRUE);
		}
	}
	
	/**
	 * Gets the config file name.
	 * 
	 * @return the configFileName
	 */
	public static String getConfigFileName()
	{
		return configFileName;
	}
	
	/**
	 * A convenient flag to Set the spring context enabled/disabled. <b> This
	 * sets the system property</b>
	 * 
	 * @param value
	 *            the spring context enabled or not
	 * @since 3.0
	 */
	public static void setSpringContextEnabled(final Boolean value)
	{
		//System.setProperty(VarraProperties.SPRING_CONTEXT_ENABLED, value.toString());
		springContextEnabled = value;
	}
	
	/**
	 * Checks if the spring context is enabled.
	 * 
	 * @return true, if is spring context enabled
	 */
	public static boolean isSpringContextEnabled()
	{
		return springContextEnabled;
	}
	
	/**
	 * Injected from the class "ApplicationContextProvider" which is
	 * automatically loaded during Spring-Initialization.
	 * 
	 * @param applicationContext
	 *            the new application context
	 */
	public static void setContext(final org.springframework.context.ApplicationContext applicationContext)
	{
		SpringContextProvider.setContext(applicationContext);
	}
	
	/**
	 * This will trigger a request to Reload the {@link SpringContext} file
	 * forcefully right now.It is intended to use only by
	 * {@link SpringContextLoader}.
	 * 
	 */
	@InterfaceAudience.LimitedPrivate("SpringContextLoader.class")
	@InterfaceStability.Evolving
	public static void reload()
	{
		SpringContextProvider.refresh();
	}
	
	/**
	 * Shutdowns the {@link SpringContext} safely..
	 */
	public static void shutdown()
	{
		SpringContextProvider.shutdown();
	}
	
	/**
	 * Gets the last loaded.
	 * 
	 * @return the lastLoaded
	 */
	public static long lastLoaded()
	{
		return SpringContextProvider.lastLoaded();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("SpringContext [");
		if (isSpringContextEnabled())
		{
			builder.append("ApplicationContext: "+getConfigFileName());
		}
		builder.append("]");
		return builder.toString();
	}
}
