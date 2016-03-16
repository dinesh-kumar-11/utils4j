/*
 * utils4j - ConsoleLogger.java, Aug 3, 2013 10:15:45 AM
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

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;
import com.varra.util.ShutdownMode;
import com.varra.util.StringPool;

/**
 * Redirects the log out put to the console, log level can be controlled by the
 * static metho {@link #setMyLogLevel(MyLogLevel)} at any point of time.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 3.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class ConsoleLogger implements Log
{
	
	/** The level. */
	private static MyLogLevel level = MyLogLevel.INFO;
	
	/** The name. */
	private String name;
	
	/** The date. */
	private static final Date date = new Date(System.currentTimeMillis());
	
	/** The Constant builder. */
	private static final StringBuilder builder = new StringBuilder(50);
	
	/** The Date format. */
	private static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
	
	/**
	 * Instantiates a new console logger.
	 */
	public ConsoleLogger()
	{
		// TODO error handling
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.util.Shutdownable#shutdown(com.varra.util.ShutdownMode)
	 */
	public void shutdown(ShutdownMode mode)
	{
		info("shutdown process has been initialzed..");
		info("shutdown process has been finished successfully.");
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
		this.name = name;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#trace(java.lang.Object)
	 */
	public void trace(Object message)
	{
		logMe(message, MyLogLevel.TRACE);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#trace(java.lang.Object, java.lang.Throwable)
	 */
	public void trace(Object message, Throwable t)
	{
		logMe(message + ", Error: " + t.getMessage(), MyLogLevel.TRACE, t);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#info(java.lang.Object)
	 */
	public void info(Object message)
	{
		logMe(message, MyLogLevel.INFO);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#info(java.lang.Object, java.lang.Throwable)
	 */
	public void info(Object message, Throwable t)
	{
		logMe(message + ", Error: " + t.getMessage(), MyLogLevel.INFO, t);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#debug(java.lang.Object)
	 */
	public void debug(Object message)
	{
		logMe(message, MyLogLevel.DEBUG);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#debug(java.lang.Object, java.lang.Throwable)
	 */
	public void debug(Object message, Throwable t)
	{
		logMe(message + ", Error: " + t.getMessage(), MyLogLevel.DEBUG, t);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#warn(java.lang.Object)
	 */
	public void warn(Object message)
	{
		logMe(message, MyLogLevel.WARN);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#warn(java.lang.Object, java.lang.Throwable)
	 */
	public void warn(Object message, Throwable t)
	{
		logMe(message + ", Error: " + t.getMessage(), MyLogLevel.WARN, t);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#error(java.lang.Object)
	 */
	public void error(Object message)
	{
		logMe(message, MyLogLevel.ERROR);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#error(java.lang.Object, java.lang.Throwable)
	 */
	public void error(Object message, Throwable t)
	{
		logMe(message + ", Error: " + t.getMessage(), MyLogLevel.ERROR, t);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#fatal(java.lang.Object)
	 */
	public void fatal(Object message)
	{
		logMe(message, MyLogLevel.FATAL);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#fatal(java.lang.Object, java.lang.Throwable)
	 */
	public void fatal(Object message, Throwable t)
	{
		logMe(message + ", Error: " + t.getMessage(), MyLogLevel.FATAL, t);
		t.printStackTrace();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#test(java.lang.Object)
	 */
	public void test(Object message)
	{
		logMe(message, MyLogLevel.TEST);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#test(java.lang.Object, java.lang.Throwable)
	 */
	public void test(Object message, Throwable t)
	{
		logMe(message + ", Error: " + t.getMessage(), MyLogLevel.TEST);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#log(java.lang.Object, int, java.lang.String)
	 */
	public void log(Object message, int level, String levelString)
	{
		logMe(message, MyLogLevel.LOG);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#log(java.lang.Object, java.lang.Throwable)
	 */
	public void log(Object message, Throwable t)
	{
		logMe(message + ", Error: " + t.getMessage(), MyLogLevel.LOG);
	}
	
	/**
	 * Logs the private message.
	 * 
	 * @param message
	 *            the message
	 * @param level
	 *            the level
	 */
	private void logMe(Object message, MyLogLevel level)
	{
		if (level.ordinal() <= getLevel().ordinal())
		{
			date.setTime(System.currentTimeMillis());
			final String dateString = format.format(date);
			System.out.println(MessageFormat.format(getFormattedString(), dateString, level.name().toUpperCase(), name,
					message));
		}
	}
	
	/**
	 * Logs the private message.
	 * 
	 * @param message
	 *            the message
	 * @param level
	 *            the level
	 */
	private void logMe(Object message, MyLogLevel level, Throwable t)
	{
		if (level.ordinal() <= getLevel().ordinal())
		{
			date.setTime(System.currentTimeMillis());
			final String dateString = format.format(date);
			System.out.println(MessageFormat.format(getFormattedString(), dateString, level.name().toUpperCase(), name, message));
			t.printStackTrace();
		}
	}
	
	/**
	 * Gets the formatted string.
	 * 
	 * @return the formatted string
	 */
	private String getFormattedString()
	{
		if (builder.length() == 0)
		{
			builder.append(StringPool.LEFT_SQ_BRACKET);
			builder.append("{0}");
			builder.append(StringPool.RIGHT_SQ_BRACKET);
			builder.append(StringPool.LEFT_SQ_BRACKET);
			builder.append("{1}");
			builder.append(StringPool.RIGHT_SQ_BRACKET);
			builder.append(StringPool.LEFT_SQ_BRACKET);
			builder.append("{2}");
			builder.append(StringPool.RIGHT_SQ_BRACKET);
			builder.append(StringPool.COLON);
			builder.append(StringPool.SPACE);
			builder.append("{3}");
		}
		return builder.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#isDebugEnabled()
	 */
	public boolean isDebugEnabled()
	{
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#isInfoEnabled()
	 */
	public boolean isInfoEnabled()
	{
		return true;
	}
	
	/**
	 * Throws the UnsupportedOperationException, as it is not implemented in
	 * this version.
	 * 
	 * @see #setMyLogLevel(MyLogLevel)
	 * 
	 * @param level
	 *            the new level
	 */
	@Override
	public void setLevel(MyLogLevel level)
	{
		throw new UnsupportedOperationException("Not supported in this version.");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.log.Log#getLevel()
	 */
	@Override
	public MyLogLevel getLevel()
	{
		return level;
	}
	
	/**
	 * Sets the my log level.
	 * 
	 * @param level
	 *            the new my log level
	 */
	public static void setMyLogLevel(MyLogLevel level)
	{
		ConsoleLogger.level = level;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("ConsoleLogger [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
}
