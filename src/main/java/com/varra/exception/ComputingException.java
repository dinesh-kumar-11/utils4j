/*
 * utils4j - ComputingException.java, Apr 2, 2013 12:26:31 PM
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

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;
import com.varra.exception.ChainedException;

/**
 * TODO Description go here.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class ComputingException extends ChainedException
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7167202835119506872L;
	
	/**
	 * Instantiates a new computing exception.
	 */
	public ComputingException()
	{
		super();
	}
	
	/**
	 * Instantiates a new computing exception.
	 * 
	 * @param message
	 *            the message
	 */
	public ComputingException(String message)
	{
		super(message);
	}
	
	/**
	 * Instantiates a new computing exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public ComputingException(Throwable cause)
	{
		super(cause);
	}
	
	/**
	 * Instantiates a new computing exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public ComputingException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
