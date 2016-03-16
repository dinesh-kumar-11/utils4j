/*
 * utils4j - ConsoleLogger.java, Nov 16, 2012 5:19:02 PM
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

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;
import com.varra.util.ShutdownMode;

/**
 * TODO Description go here.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 3.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class Log4jLogger implements Log
{
	
	private Logger logger;
	
	/** The name. */
	private String name;
	
	/**
	 * Instantiates a new console logger.
	 */
	public Log4jLogger()
	{
		logger = Logger.getLogger(Log4jLogger.class);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.util.Shutdownable#shutdown(com.varra.util.ShutdownMode)
	 */
	public void shutdown(ShutdownMode mode)
	{
		org.apache.log4j.LogManager.shutdown();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#getName()
	 */
	public String getName()
	{
		return name;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#setName(java.lang.String)
	 */
	public void setName(String name)
	{
		logger = Logger.getLogger(name);
		this.name = name;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#trace(java.lang.Object)
	 */
	public void trace(Object message)
	{
		logger.trace(message);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#trace(java.lang.Object, java.lang.Throwable)
	 */
	public void trace(Object message, Throwable t)
	{
		logger.trace(message);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#info(java.lang.Object)
	 */
	public void info(Object message)
	{
		logger.info(message);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#info(java.lang.Object, java.lang.Throwable)
	 */
	public void info(Object message, Throwable t)
	{
		logger.info(message, t);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#debug(java.lang.Object)
	 */
	public void debug(Object message)
	{
		logger.debug(message);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#debug(java.lang.Object, java.lang.Throwable)
	 */
	public void debug(Object message, Throwable t)
	{
		logger.debug(message, t);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#warn(java.lang.Object)
	 */
	public void warn(Object message)
	{
		logger.warn(message);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#warn(java.lang.Object, java.lang.Throwable)
	 */
	public void warn(Object message, Throwable t)
	{
		logger.warn(message, t);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#error(java.lang.Object)
	 */
	public void error(Object message)
	{
		logger.error(message);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#error(java.lang.Object, java.lang.Throwable)
	 */
	public void error(Object message, Throwable t)
	{
		logger.error(message, t);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#fatal(java.lang.Object)
	 */
	public void fatal(Object message)
	{
		logger.fatal(message);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#fatal(java.lang.Object, java.lang.Throwable)
	 */
	public void fatal(Object message, Throwable t)
	{
		logger.fatal(message, t);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#test(java.lang.Object)
	 */
	public void test(Object message)
	{
		logger.info("test is not implemented: so using INFO. "+message);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#test(java.lang.Object, java.lang.Throwable)
	 */
	public void test(Object message, Throwable t)
	{
		logger.info(message);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#log(java.lang.Object, int, java.lang.String)
	 */
	public void log(Object message, int level, String levelString)
	{
		logger.info(message);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#log(java.lang.Object, java.lang.Throwable)
	 */
	public void log(Object message, Throwable t)
	{
		logger.info(message, t);
	}
	
	/* (non-Javadoc)
	 * @see com.varra.log.Log#isDebugEnabled()
	 */
	public boolean isDebugEnabled()
	{
		return logger.isDebugEnabled();
	}

	/* (non-Javadoc)
	 * @see com.varra.log.Log#isInfoEnabled()
	 */
	public boolean isInfoEnabled()
	{
		return logger.isInfoEnabled();
	}
	
	/* (non-Javadoc)
	 * @see com.varra.log.Log#setLevel(com.varra.log.MyLogLevel)
	 */
	@Override
	public void setLevel(MyLogLevel level)
	{
		Logger.getRootLogger().setLevel(Level.toLevel(MyLogLevel.values()[level.ordinal()].toString()));
	}

	/* (non-Javadoc)
	 * @see com.varra.log.Log#getLevel()
	 */
	@Override
	public MyLogLevel getLevel()
	{
		return MyLogLevel.valueOf(Logger.getRootLogger().getLevel().toString());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Log4jLogger [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
}
