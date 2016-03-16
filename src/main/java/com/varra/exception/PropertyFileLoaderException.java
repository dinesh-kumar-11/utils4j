/*
 * utils4j - PropertyFileLoaderException.java, Apr 30, 2011 4:35:14 PM
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
public class PropertyFileLoaderException extends ChainedException
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -518206592269823012L;
	
	/**
	 * Instantiates a new property file loader exception.
	 */
	public PropertyFileLoaderException()
	{
		super();
	}
	
	/**
	 * Instantiates a new property file loader exception.
	 * 
	 * @param message
	 *            the message
	 */
	public PropertyFileLoaderException(String message)
	{
		super(message);
	}
	
	/**
	 * Instantiates a new property file loader exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public PropertyFileLoaderException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	/**
	 * Instantiates a new property file loader exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public PropertyFileLoaderException(Throwable cause)
	{
		super(cause);
	}
}
