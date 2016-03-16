/*
 * utils4j - MBeanRegistrationException.java, Feb 3, 2011 4:45:08 PM
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

/**
 * Wraps exceptions thrown by the register(), unRegister() methods of the
 * <CODE>MBeanRegistration</CODE> interface.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
public class MBeanRegistrationException extends JMXWrapperException
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -803932191385515338L;
	
	/**
	 * Instantiates a new m bean registration exception.
	 * 
	 * @param message
	 *            the message
	 */
	public MBeanRegistrationException(String message)
	{
		super(message);
	}
	
	/**
	 * Creates an <CODE>MBeanRegistrationException</CODE> that wraps the actual
	 * <CODE>java.lang.Exception</CODE>.
	 * 
	 * @param cause
	 *            the wrapped exception.
	 */
	public MBeanRegistrationException(Throwable cause)
	{
		super(cause);
	}
	
	/**
	 * Instantiates a new m bean registration exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public MBeanRegistrationException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
