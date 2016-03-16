/*
 * utils4j - Logger.java, Aug 16, 2015 4:24:23 PM
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

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;
import com.varra.util.ShutdownMode;
import com.varra.util.Shutdownable;

/**
 * Logger - <font color="#006600"><b> Yet An another fine grained {@link Logger}
 * class to log the Application specific messages.</b></font> Is not based on
 * log4j (<font color="#006600"><b>to reduce external dependencies</b></font>).
 * However, if needed, something like log4j could easily be hooked in.
 *
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V. Reddy</a>
 * @version 3.0
 * @see Log
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class Logger implements Shutdownable
{
	
	/** The customized actual log implementation. */
	private final Log logger;
	
	/** The name. */
	private final String name;
	
	/**
	 * Instantiates a new logger.
	 * 
	 * @param log
	 *            the log
	 * @param name
	 *            the name
	 */
	protected Logger(Log log, String name)
	{
		this.logger = log;
		this.name = name;
	}
	
	/**
	 * Retrieve a logger named according to the value of the <code>name</code>
	 * parameter. If the named logger already exists, then the existing instance
	 * will be returned. Otherwise, a new instance is created.
	 * 
	 * @param klass
	 *            the klass
	 * @return the logger
	 */
	public static Logger getLogger(Class<?> klass)
	{
		return getLogger(klass.getName());
	}
	
	/**
	 * Retrieve a logger named according to the value of the <code>name</code>
	 * parameter. If the named logger already exists, then the existing instance
	 * will be returned. Otherwise, a new instance is created.
	 * 
	 * @param name
	 *            the name
	 * @return the logger
	 */
	public static Logger getLogger(String name)
	{
		return LogManager.getLogger(name);
	}
	
	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Log a message object with the {@link Level#TRACE TRACE} level.
	 * 
	 * @param message
	 *            the message
	 */
	public void trace(Object message)
	{
		logger.trace(message);
	}
	
	/**
	 * Log a message object with the {@link Level#TRACE TRACE} level including
	 * the stack trace of the {@link Throwable}<code>t</code> passed as
	 * parameter.
	 * 
	 * <p>
	 * See {@link #debug(Object)} form for more detailed information.
	 * </p>
	 * 
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 */
	public void trace(Object message, Throwable t)
	{
		logger.trace(message, t);
	}
	
	/**
	 * Log a message object with the {@link Level#INFO INFO} Level.
	 * 
	 * @param message
	 *            the message
	 */
	public void info(Object message)
	{
		logger.info(message);
	}
	
	/**
	 * Log a message object with the {@link Level#INFO INFO} level including the
	 * stack trace of the {@link Throwable} <code>t</code> passed as parameter.
	 * 
	 * <p>
	 * See {@link #info(Object)} for more detailed information.
	 * 
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 */
	public void info(Object message, Throwable t)
	{
		logger.info(message, t);
	}
	
	/**
	 * Log a message object with the {@link Level#DEBUG DEBUG} level.
	 * 
	 * @param message
	 *            the message
	 */
	public void debug(Object message)
	{
		logger.debug(message);
	}
	
	/**
	 * Log a message object with the {@link Level#DEBUG DEBUG} level including
	 * the stack trace of the {@link Throwable} <code>t</code> passed as
	 * parameter.
	 * 
	 * <p>
	 * 
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @see com.varra.log.Logger#debug form for more detailed information.
	 */
	public void debug(Object message, Throwable t)
	{
		logger.debug(message, t);
	}
	
	/**
	 * Log a message object with the {@link Level#WARN WARN} Level.
	 * 
	 * @param message
	 *            the message
	 */
	public void warn(Object message)
	{
		logger.warn(message);
	}
	
	/**
	 * Log a message with the <code>WARN</code> level including the stack trace
	 * of the {@link Throwable} <code>t</code> passed as parameter.
	 * 
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @see com.varra.log.Logger#warn for more detailed information.
	 */
	public void warn(Object message, Throwable t)
	{
		logger.warn(message, t);
	}
	
	/**
	 * Log a message object with the {@link Level#ERROR ERROR} Level..
	 * 
	 * @param message
	 *            the message
	 */
	public void error(Object message)
	{
		logger.error(message);
	}
	
	/**
	 * Log a message object with the <code>ERROR</code> level including the
	 * stack trace of the {@link Throwable} <code>t</code> passed as parameter.
	 * 
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @see com.varra.log.Logger#error form for more detailed information.
	 */
	public void error(Object message, Throwable t)
	{
		logger.error(message, t);
	}
	
	/**
	 * Log a message object with the {@link Level#FATAL FATAL} Level.
	 * 
	 * @param message
	 *            the message
	 */
	public void fatal(Object message)
	{
		logger.fatal(message);
	}
	
	/**
	 * Log a message object with the {@link Level#FATAL FATAL} level including
	 * the stack trace of the {@link Throwable} <code>t</code> passed as
	 * parameter.
	 * 
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @see com.varra.log.Logger#fatal for more detailed information.
	 */
	public void fatal(Object message, Throwable t)
	{
		logger.fatal(message, t);
	}
	
	/**
	 * Log a message object with the test Level. <br>
	 * <br>
	 * Note: <b>Is useful for testing purpose, if the code is moved to
	 * production just you can disable this level instead of removing the log
	 * entries in classes spread across in your Application.</b><br>
	 * There will be a situation where {@link Level#DEBUG DEBUG} and
	 * 
	 * @param message
	 *            the message {@link Level#INFO INFO} are not adequate.
	 */
	public void test(Object message)
	{
		logger.test(message);
	}
	
	/**
	 * Log a message object with the test level including the
	 * stack trace of the {@link Throwable} <code>t</code> passed as parameter.
	 * 
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @see com.varra.log.Logger#test for more detailed information.
	 */
	public void test(Object message, Throwable t)
	{
		logger.test(message, t);
	}
	
	/**
	 * Log a message object with the {@link Level#level MyLevel} (an user
	 * defined) Level. <br>
	 * <br>
	 * Note: <b>Is very useful for the developers to use for specific purpose,
	 * like for displaying the time elapsed for an event or for showing the
	 * statistics or for showing the progress of a task..etc</b>
	 * 
	 * @param message
	 *            the message
	 * @param level
	 *            the level
	 * @param levelString
	 *            the level string
	 */
	public void log(Object message, int level, String levelString)
	{
		logger.log(message, level, levelString);
	}
	
	/**
	 * Log a message object with the {@link Level#level MyLevel} (an user
	 * defined) Level <br>
	 * including the stack trace of the {@link Throwable} <code>t</code> passed
	 * as parameter.
	 * 
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @see com.varra.log.Logger#log for more detailed information.
	 */
	public void log(Object message, Throwable t)
	{
		logger.log(message, t);
	}
	
	/**
	 * Check whether this category is enabled for the <code>DEBUG</code> Level.
	 * 
	 * <p>
	 * This function is intended to lessen the computational cost of disabled
	 * log debug statements.
	 * 
	 * <p>
	 * For some <code>cat</code> Category object, when you write,
	 * 
	 * <pre>
	 * logger.debug(&quot;This is entry number: &quot; + i);
	 * </pre>
	 * 
	 * <p>
	 * You incur the cost constructing the message, concatenatiion in this case,
	 * regardless of whether the message is logged or not.
	 * 
	 * <p>
	 * If you are worried about speed, then you should write
	 * 
	 * <pre>
	 * if (logger.isDebugEnabled())
	 * {
	 * 	logger.debug(&quot;This is entry number: &quot; + i);
	 * }
	 * </pre>
	 * 
	 * <p>
	 * This way you will not incur the cost of parameter construction if
	 * debugging is disabled for <code>cat</code>. On the other hand, if the
	 * <code>cat</code> is debug enabled, you will incur the cost of evaluating
	 * whether the category is debug enabled twice. Once in
	 * <code>isDebugEnabled</code> and once in the <code>debug</code>. This is
	 * an insignificant overhead since evaluating a category takes about 1%% of
	 * the time it takes to actually log.
	 * 
	 * @return boolean - <code>true</code> if this category is debug enabled,
	 *         <code>false</code> otherwise.
	 * */
	public boolean isDebugEnabled()
	{
		return logger.isDebugEnabled();
	}
	
	/**
	 * Check whether this category is enabled for the info Level. See also
	 * {@link #isDebugEnabled}.
	 * 
	 * @return boolean - <code>true</code> if this category is enabled for level
	 *         info, <code>false</code> otherwise.
	 */
	public boolean isInfoEnabled()
	{
		return logger.isInfoEnabled();
	}
	
	/**
	 * Set the level of this Category. If you are passing any of
	 * <code>MyLogLevel.DEBUG</code>, <code>MyLogLevel.INFO</code>,
	 * <code>MyLogLevel.WARN</code>, <code>MyLogLevel.ERROR</code>,
	 * <code>MyLogLevel.FATAL</code> as a parameter, you need to case them as
	 * MyLogLevel.
	 * 
	 * <p>
	 * As in
	 * 
	 * <pre>
	 * logger.setLevel((MyLogLevel) MyLogLevel.DEBUG);
	 * </pre>
	 * 
	 * Null values are not entertained.
	 *
	 * @param level
	 *            the new level
	 */
	public void setLevel(MyLogLevel level)
	{
		logger.setLevel(level);
	}
	
	/**
	 * Returns the assigned {@link MyLogLevel}, if any, for this Category.
	 *
	 * @return MyLogLevel - the assigned Level, can be <code>null</code>. the
	 *         level
	 */
	public MyLogLevel getLevel()
	{
		return logger.getLevel();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.util.Shutdownable#shutdown(com.varra.util.ShutdownMode)
	 */
	public void shutdown(ShutdownMode mode)
	{
		logger.shutdown(mode);
		LogManager.shutdownAll(mode);
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
		builder.append("Logger [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
}
