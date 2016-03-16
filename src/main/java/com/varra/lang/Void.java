/*
 * utils4j - Void.java, Aug 2, 2013 4:09:06 PM
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
package com.varra.lang;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;

/**
 * 
 * The Void class is an placeholder class to hold a reference to the Class
 * object representing the Java keyword void. It is created to hold a successful
 * reference instead of null for the reflection based method executions which
 * returns void.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 1.0
 * @since 3.0
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public final class Void
{
	
	/** The Constant INSTANCE. */
	public static final Void INSTANCE = new Void();
	
	/**
	 * Instantiates a new void.
	 */
	private Void()
	{
		
	}
	
	/**
	 * Get the instance of type Void.
	 * 
	 * @return the void
	 */
	public static Void getInstance()
	{
		return INSTANCE;
	}
	
}
