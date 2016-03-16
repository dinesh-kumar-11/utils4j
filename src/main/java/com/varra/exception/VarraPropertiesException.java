/*
 * utils4j - VarraPropertiesException.java, Jan 7, 2013 10:29:30 AM
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
public class VarraPropertiesException extends Exception
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4672469225789139660L;
	
	/**
	 * Instantiates a new mind tree properties exception.
	 */
	public VarraPropertiesException()
	{
		super();
	}
	
	/**
	 * Instantiates a new mind tree properties exception.
	 * 
	 * @param message
	 *            the message
	 */
	public VarraPropertiesException(String message)
	{
		super(message);
	}
	
	/**
	 * Instantiates a new mind tree properties exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public VarraPropertiesException(Throwable cause)
	{
		super(cause);
	}
	
	/**
	 * Instantiates a new mind tree properties exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public VarraPropertiesException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
