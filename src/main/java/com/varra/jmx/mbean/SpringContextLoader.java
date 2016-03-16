/*
 * utils4j - SpringContextLoader.java, May 6, 2011 9:59:23 AM
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

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;
import com.varra.spring.SpringContext;

/**
 * Loads the {@link SpringContext} file, re-loads the file whenever it gets a
 * request thru the JMX.<br>
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
@InterfaceAudience.LimitedPrivate("com.varra.spring")
@InterfaceStability.Evolving
public class SpringContextLoader implements SpringContextLoaderMBean
{
	
	/** The spring context loader. */
	private static SpringContextLoader springContextLoader;
	
	/**
	 * Instantiates a new spring context loader.
	 */
	private SpringContextLoader()
	{
	}
	
	/**
	 * Gets the spring context loader.
	 * 
	 * @return the spring context loader
	 */
	public static synchronized SpringContextLoader getSpringContextLoader()
	{
		if (springContextLoader == null)
		{
			springContextLoader = new SpringContextLoader();
		}
		return springContextLoader;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.jmx.mbean.SpringContextLoaderMBean#lastLoaded()
	 */
	public long lastLoaded()
	{
		return SpringContext.lastLoaded();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.jmx.mbean.SpringContextLoaderMBean#reload()
	 */
	public void reload()
	{
		SpringContext.reload();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.jmx.mbean.SpringContextLoaderMBean#getContextFileName()
	 */
	public String getContextFileName()
	{
		return SpringContext.getConfigFileName();
	}
}
