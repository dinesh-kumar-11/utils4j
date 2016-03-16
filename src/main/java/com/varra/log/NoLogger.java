/*
 * utils4j - NoLogger.java, Dec 20, 2012 10:35:29 AM
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
package com.varra.log;

import com.varra.props.VarraProperties;
import com.varra.util.ShutdownMode;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;

/**
 * Silent Logger which swallows all the logs, will be the default logger for all
 * the logger messages.
 * 
 * Note: Set logger like {@link VarraProperties#setProperty}(
 * {@link LoggerConstants#LOG_CLASS_FQ_NAME},
 * Log4jLogger[ConsoleLogger].class.getName()
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class NoLogger implements Log
{
	
	/** The Constant NO_LOGGER. */
	private static final NoLogger NO_LOGGER = new NoLogger();
	
	/** The name. */
	private String name;
	
	/**
	 * Instantiates a new no logger.
	 */
	public NoLogger()
	{
		
	}
	
	/**
	 * Gets the no logger.
	 * 
	 * @return the noLogger
	 */
	public static NoLogger getNoLogger()
	{
		return NO_LOGGER;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#getName()
	 */
	@Override
	public String getName()
	{
		return name;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#setName(java.lang.String)
	 */
	@Override
	public void setName(String name)
	{
		this.name = name;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#trace(java.lang.Object)
	 */
	@Override
	public void trace(Object message)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#trace(java.lang.Object, java.lang.Throwable)
	 */
	@Override
	public void trace(Object message, Throwable t)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#info(java.lang.Object)
	 */
	@Override
	public void info(Object message)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#info(java.lang.Object, java.lang.Throwable)
	 */
	@Override
	public void info(Object message, Throwable t)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#debug(java.lang.Object)
	 */
	@Override
	public void debug(Object message)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#debug(java.lang.Object, java.lang.Throwable)
	 */
	@Override
	public void debug(Object message, Throwable t)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#warn(java.lang.Object)
	 */
	@Override
	public void warn(Object message)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#warn(java.lang.Object, java.lang.Throwable)
	 */
	@Override
	public void warn(Object message, Throwable t)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#error(java.lang.Object)
	 */
	@Override
	public void error(Object message)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#error(java.lang.Object, java.lang.Throwable)
	 */
	@Override
	public void error(Object message, Throwable t)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#fatal(java.lang.Object)
	 */
	@Override
	public void fatal(Object message)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#fatal(java.lang.Object, java.lang.Throwable)
	 */
	@Override
	public void fatal(Object message, Throwable t)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#test(java.lang.Object)
	 */
	@Override
	public void test(Object message)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#test(java.lang.Object, java.lang.Throwable)
	 */
	@Override
	public void test(Object message, Throwable t)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#log(java.lang.Object, int, java.lang.String)
	 */
	@Override
	public void log(Object message, int level, String levelString)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#log(java.lang.Object, java.lang.Throwable)
	 */
	@Override
	public void log(Object message, Throwable t)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#isDebugEnabled()
	 */
	@Override
	public boolean isDebugEnabled()
	{
		
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#isInfoEnabled()
	 */
	@Override
	public boolean isInfoEnabled()
	{
		
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#setLevel(com.varra.log.MyLogLevel)
	 */
	@Override
	public void setLevel(MyLogLevel level)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#getLevel()
	 */
	@Override
	public MyLogLevel getLevel()
	{
		
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.util.Shutdownable#shutdown(com.varra.util.ShutdownMode)
	 */
	@Override
	public void shutdown(ShutdownMode mode)
	{
		
	}
}
