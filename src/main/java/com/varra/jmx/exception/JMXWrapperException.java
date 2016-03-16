/*
 * utils4j - JMXWrapperException.java, Feb 5, 2011 3:51:19 PM
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
package com.varra.jmx.exception;

import com.varra.exception.ChainedException;

/**
 * Wraps exceptions thrown by the JMXWrapper interface.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
public class JMXWrapperException extends ChainedException
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5868197856445465424L;
	
	
	/**
	 * Instantiates a new jMX wrapper exception.
	 */
	public JMXWrapperException()
	{
		super();
	}
	
	/**
	 * Instantiates a new jMX wrapper exception.
	 * 
	 * @param message
	 *            the message
	 */
	public JMXWrapperException(String message)
	{
		super(message);
	}
	
	/**
	 * Instantiates a new jMX wrapper exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public JMXWrapperException(Throwable cause)
	{
		super(cause);
	}
	
	/**
	 * Instantiates a new jMX wrapper exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public JMXWrapperException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
