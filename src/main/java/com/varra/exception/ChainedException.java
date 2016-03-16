/*
 * utils4j - ChainedException.java, Aug 16, 2015 1:58:35 PM
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

/**
 * This {@link Exception} is the base class for the this Exception module.<br>
 * <b> It extends the {@link Exception} class and adds Chaining feature.</b>
 * Through the use of chaining, stack traces of all the exceptions in the chain
 * can be maintained. <br>
 * <br>
 * This class overrides the {@link #printStackTrace()} method of
 * {@link Exception} class and prints the stack trace of both the Exceptions
 * (the original exception and the exception that is replacing the original
 * exception).<br>
 * Taken from the article <a href=
 * "http://developer.java.sun.com/developer/technicalArticles/Programming/exceptions2/">
 * Exceptional practices, Part 2</a> by Brian Goetz, JavaWorld Oct 2001
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V. Reddy</a>
 * @version 1.0
 * 			
 */
public class ChainedException extends Exception
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1708365137994129851L;
	
	/** The instance of Throwable that is replacing the actual exception. */
	private Throwable cause = null;
	
	/**
	 * Constructor.
	 */
	public ChainedException()
	{
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message
	 *            message representing the cause of exception
	 */
	public ChainedException(String message)
	{
		super(message);
	}
	
	/**
	 * Useful for revealing the original error with a bonus message.
	 * 
	 * Constructor takes a string and something throwable. Adds the throwable
	 * item to a collection that can later be dumped out by toString() and/or
	 * printStackTrace()
	 * 
	 * @param message
	 *            message representing the cause of exception
	 * @param cause
	 *            Exception that needs to be chained
	 */
	public ChainedException(String message, Throwable cause)
	{
		super(message);
		this.cause = cause;
	}
	
	/**
	 * Useful for revealing the original error.
	 * 
	 * Constructor takes something throwable and adds it to a collection that
	 * can later be dumped out by toString() and/or printStackTrace()
	 *
	 * @param cause
	 *            Exception that needs to be chained
	 */
	public ChainedException(Throwable cause)
	{
		super();
		this.cause = cause;
	}
	
	/**
	 * Gets the cause of the actual exception.
	 * 
	 * @return the cause
	 * @see java.lang.Throwable#getCause()
	 */
	public Throwable getCause()
	{
		return cause;
	}
	
	/**
	 * Overridden method to include the stack trace of the original exception.
	 * 
	 * @see java.lang.Throwable#printStackTrace()
	 */
	public void printStackTrace()
	{
		super.printStackTrace();
		if (cause != null)
		{
			System.err.println("Caused by:");
			cause.printStackTrace();
		}
	}
	
	/**
	 * Overridden method to include the stack trace of the original exception.
	 * 
	 * @param ps
	 *            the ps
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintStream)
	 */
	public void printStackTrace(java.io.PrintStream ps)
	{
		super.printStackTrace(ps);
		if (cause != null)
		{
			ps.println("Caused by:");
			cause.printStackTrace(ps);
		}
	}
	
	/**
	 * Overridden method to include the stack trace of the original exception.
	 * 
	 * @param pw
	 *            the pw
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintWriter)
	 */
	public void printStackTrace(java.io.PrintWriter pw)
	{
		super.printStackTrace(pw);
		if (cause != null)
		{
			pw.println("Caused by:");
			cause.printStackTrace(pw);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName() + ": [Message: ");
		builder.append(getMessage());
		builder.append(", Cause: ");
		builder.append(getCause());
		builder.append("]");
		return builder.toString();
	}
}