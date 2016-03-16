/*
 * util4J - Default.java, Jun 13, 2012 3:18:00 PM
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
package com.varra;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;
import com.varra.util.StringPool;

/**
 * TODO Description go here.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class Default
{
	
	/** The Constant UTIL4J_VERSION. */
	public static final String UTIL4J_VERSION;
	
	/** The Constant UTIL4J_PACKAGE_NAME. */
	public static final String UTIL4J_PACKAGE_NAME;
	
	/**
	 * Default temp file prefix.
	 */
	public static String tempFilePrefix = "util4j-";
	
	/**
	 * Default file encoding (UTF8).
	 */
	public static String encoding = StringPool.UTF_8;
	
	/**
	 * Default IO buffer size (16 KB).
	 */
	public static int ioBufferSize = 16384;
	
	static
	{
		Package pkg = Default.class.getPackage();
		UTIL4J_VERSION = pkg.getImplementationVersion();
		UTIL4J_PACKAGE_NAME = pkg.getName();
	}
}
