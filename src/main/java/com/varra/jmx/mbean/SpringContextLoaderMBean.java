/*
 * utils4j - SpringContextLoaderMBean.java, May 5, 2011 8:50:33 PM
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
 * Exposing this functionality as an MBean, to make this available to outside
 * world.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public interface SpringContextLoaderMBean
{
	
	/**
	 * Gets the timeStamp of the {@link SpringContext} file when it is Last
	 * loaded.
	 * 
	 * @return the long
	 * 
	 * @see SpringContext#lastLoaded()
	 */
	public long lastLoaded();
	
	/**
	 * This will trigger a request to Reload the {@link SpringContext} file
	 * forcefully right now.
	 * 
	 * @see SpringContext#reload()
	 */
	public void reload();
	
	/**
	 * Gets the full qualified {@link SpringContext} file name.
	 * 
	 * @return the {@link SpringContext} file name
	 * 
	 * @see SpringContext#getConfigFileName()
	 */
	public String getContextFileName();
}
