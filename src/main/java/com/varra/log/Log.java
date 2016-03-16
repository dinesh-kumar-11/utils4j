/*
 * utils4j - Log.java, Aug 16, 2015 3:52:46 PM
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
import com.varra.util.Shutdownable;

/**
 * A convenient way of implementing the Logger functionality. Very useful to
 * integrate with the third party Logger implementations. <br>
 * Just implement this with the preferred logging framework whatever you choose.
 * <br>
 * <b>Note: </b><font color="#855C33"><b>Actual implementations need to take
 * care of Log Level enabled/disabled check before processing to log the
 * messages, if you are not using the third party framework.</b></font>
 *
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V. Reddy</a>
 * @version 3.0
 * @see Logger
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public interface Log extends Shutdownable
{
	
	/**
	 * Gets the name of the logger.
	 * 
	 * @return the name
	 */
	String getName();
	
	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the name
	 */
	void setName(String name);
	
	/**
	 * Log a message object with the {@link Level#TRACE TRACE} level.
	 * 
	 * @param message
	 *            the message
	 */
	void trace(Object message);
	
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
	void trace(Object message, Throwable t);
	
	/**
	 * Log a message object with the {@link Level#INFO INFO} Level.
	 * 
	 * @param message
	 *            the message
	 */
	void info(Object message);
	
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
	void info(Object message, Throwable t);
	
	/**
	 * Log a message object with the {@link Level#DEBUG DEBUG} level.
	 * 
	 * @param message
	 *            the message
	 */
	void debug(Object message);
	
	
	/**
	 * Log a message object with the {@link Level#DEBUG DEBUG} level including
	 * the stack trace of the {@link Throwable} <code>t</code> passed as
	 * parameter.
	 *
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 *            
	 * @see     com.varra.log.Log#debug
	 */
	void debug(Object message, Throwable t);
	
	/**
	 * Log a message object with the {@link Level#WARN WARN} Level.
	 * 
	 * @param message
	 *            the message
	 */
	void warn(Object message);
	
	/**
	 * Log a message with the <code>WARN</code> level including the stack trace
	 * of the {@link Throwable} <code>t</code> passed as parameter.
	 * 
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @see     com.varra.log.Log#warn for more detailed information.
	 */
	void warn(Object message, Throwable t);
	
	/**
	 * Log a message object with the {@link Level#ERROR ERROR} Level..
	 * 
	 * @param message
	 *            the message
	 */
	void error(Object message);
	
	/**
	 * Log a message object with the <code>ERROR</code> level including the
	 * stack trace of the {@link Throwable} <code>t</code> passed as parameter.
	 * 
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @see     com.varra.log.Log#error form for more detailed information.
	 */
	void error(Object message, Throwable t);
	
	/**
	 * Log a message object with the {@link Level#FATAL FATAL} Level.
	 * 
	 * @param message
	 *            the message
	 */
	void fatal(Object message);
	
	/**
	 * Log a message object with the {@link Level#FATAL FATAL} level including
	 * the stack trace of the {@link Throwable} <code>t</code> passed as
	 * parameter.
	 * 
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @see     com.varra.log.Log#fatal for more detailed information.
	 */
	void fatal(Object message, Throwable t);
	
	/**
	 * Log a message object with the test Level. <br>
	 * <br>
	 * Note: <b>Is useful for testing purpose, if the code is moved to
	 * production just you can disable this level instead of removing the log
	 * entries in classes spread across in your Application.</b><br>
	 * There will be a situation where {@link Level#DEBUG DEBUG} and
	 * {@link Level#INFO INFO} are not adequate.
	 * 
	 * @param message
	 *            the message
	 */
	void test(Object message);
	
	/**
	 * Log a message object with the test level including the
	 * stack trace of the {@link Throwable} <code>t</code> passed as parameter.
	 * 
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @see     com.varra.log.Log#test for more detailed information.
	 */
	void test(Object message, Throwable t);
	
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
	void log(Object message, int level, String levelString);
	
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
	 * @see com.varra.log.Log#log for more detailed information.
	 */
	void log(Object message, Throwable t);
	
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
	 */
	public boolean isDebugEnabled();
	
	/**
	 * Check whether this category is enabled for the info Level. See also
	 * {@link #isDebugEnabled}.
	 * 
	 * @return boolean - <code>true</code> if this category is enabled for level
	 *         info, <code>false</code> otherwise.
	 */
	public boolean isInfoEnabled();
	
	/**
	 * Set the level of this Category. If you are passing any of
	 * <code>MyLogLevel.DEBUG</code>, <code>MyLogLevel.INFO</code>,
	 * <code>MyLogLevel.WARN</code>, <code>MyLogLevel.ERROR</code>,
	 * <code>MyLogLevel.FATAL</code> as a parameter, you need to case them as
	 * MyLogLevel.
	 * <p>
	 * As in
	 * 
	 * <pre>
	 * logger.setLevel((MyLogLevel) MyLogLevel.DEBUG);
	 * </pre>
	 * <p>
	 * Null values are not entertained.
	 *
	 * @param level
	 *            the new level
	 */
	public void setLevel(MyLogLevel level);
	
	/**
	 * Returns the assigned {@link MyLogLevel}, if any, for this Category.
	 *
	 * @return MyLogLevel - the assigned Level, can be <code>null</code>.
	 * 		
	 *         the level
	 */
	public MyLogLevel getLevel();
}
