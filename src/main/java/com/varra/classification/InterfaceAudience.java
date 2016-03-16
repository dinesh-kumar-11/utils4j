/*
 * utils4j - InterfaceAudience.java, May 10, 2011 8:21:15 PM
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
package com.varra.classification;

import java.io.Serializable;
import java.lang.annotation.Documented;

/**
 * Annotation to inform users of a package, class or method's intended audience.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class InterfaceAudience implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1562112574403426145L;
	
	/**
	 * Intended for use by any project or application.
	 * 
	 * @author Rajakrishna V. Reddy
	 * @version 1.0
	 */
	@Documented
	public @interface Public
	{
	};
	
	/**
	 * Intended only for the project(s) or Package(s) specified in the
	 * annotation.<br>
	 * For example, "mbean", "notification", "util".
	 * 
	 * @author Rajakrishna V. Reddy
	 * @version 1.0
	 */
	@Documented
	public @interface LimitedPrivate
	{
		
		/**
		 * Value.
		 * 
		 * @return the string[]
		 */
		String[] value();
	};
	
	/**
	 * Intended for use only within this itself.
	 * 
	 * @author Rajakrishna V. Reddy
	 * @version 1.0
	 */
	@Documented
	public @interface Private
	{
	};
	
	/**
	 * Instantiates a new interface audience.
	 */
	private InterfaceAudience()
	{
		
	}
	// Audience can't exist on its own.
}
