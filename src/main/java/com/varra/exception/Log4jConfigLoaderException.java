/*
 * utils4j - Log4jConfigLoaderException.java, Apr 20, 2011 5:51:37 PM
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

package com.varra.exception;

import com.varra.exception.ChainedException;

/**
 * TODO Description go here.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
public class Log4jConfigLoaderException extends ChainedException
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -764184825403997773L;
	
	/**
	 * Instantiates a new log4j config loader exception.
	 */
	public Log4jConfigLoaderException()
	{
		super();
	}
	
	/**
	 * Instantiates a new log4j config loader exception.
	 * 
	 * @param message
	 *            the message
	 */
	public Log4jConfigLoaderException(String message)
	{
		super(message);
	}
	
	/**
	 * Instantiates a new log4j config loader exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public Log4jConfigLoaderException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	/**
	 * Instantiates a new log4j config loader exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public Log4jConfigLoaderException(Throwable cause)
	{
		super(cause);
	}
}
