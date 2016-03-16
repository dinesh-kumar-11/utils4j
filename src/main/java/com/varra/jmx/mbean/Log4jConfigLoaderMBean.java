/*
 * utils4j - Log4jConfigLoaderMBean.java, May 10, 2011 8:47:04 PM
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
package com.varra.jmx.mbean;

import java.io.Serializable;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;

/**
 * Loads the log4jConfig file and monitors it, re-loads the log file whenever it
 * gets changed.<br>
 * Exposing this functionality as an MBean, to make this available to outside
 * world.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface Log4jConfigLoaderMBean extends Serializable
{
	
	/**
	 * Gets the timeStamp of the logFile when it is Last loaded.
	 * 
	 * @return the long
	 */
	public long lastLoaded();
	
	/**
	 * The Default LogFile will be loaded for every pre-configured interval, but
	 * this request Reloads the logFile forcefully.
	 */
	public void reload();
	
	/**
	 * The LogFile provided will be loaded for every pre-configured interval,
	 * but this request Reloads the logFile forcefully.
	 */
	//public void reload(final String fileName);
	
	/**
	 * Gets the full qualified log file name.
	 * 
	 * @return the log file name
	 */
	public String getLogFileName();
	
	/**
	 * Sets the log file name.
	 * 
	 * @param logFileName
	 *            the log file name
	 * @return the string
	 */
	public String setLogFileName(final String logFileName);
	
	/**
	 * Gets the interval of the logFile loader that is pre-configured.
	 * 
	 * @return the interval
	 */
	public int getInterval();
	
	/**
	 * Sets the interval of the logFile loader, replaces the value that is
	 * pre-configured for this instance.
	 * 
	 * @param interval
	 *            the new interval
	 */
	public void setInterval(final int interval);
	
	/**
	 * Gets the current root log level configured. <br>
	 * You can change this by using the {@link #setLevel(String)}. method.<br>
	 * It returns -1 there is no log level set for it or if gets any exception.
	 * !!!
	 * 
	 * @return the level - the assigned level, can be -1.
	 */
	public String getLevel();
	
	/**
	 * Gets the current root log level configured. <br>
	 * You can change this by using the {@link #setLevel(int level)} method.<br>
	 * 
	 * @return Level - the assigned Level, can be <code>null</code>.
	 */
	//public String getLevelAsString();
	
	/**
	 * It is an admin's helper method to set the log level on demand thru JMX,
	 * It can be done by changing the root log level entry in log properties
	 * too.<br>
	 * Sets the Root Log level, that is passed as an integer. Please find the
	 * number corresponding to the level respectively.<BR>
	 * <BR>
	 * 
	 * &nbsp;&nbsp;0. &nbsp;The <code><b>OFF</b></code> has the highest possible
	 * rank and is intended to turn off logging.
	 * <ol>
	 * <li>The <code><b>FATAL</b></code> level designates very severe error
	 * events that will presumably lead the application to abort.<br>
	 * <li>The <code><b>ERROR</b></code> level designates error events that
	 * might still allow the application to continue running.<br>
	 * <li>The <code><b>WARN</b></code> level designates potentially harmful
	 * situations.<br>
	 * <li>The <code><b>INFO</b></code> level designates informational messages
	 * that highlight the progress of the application at coarse-grained level.
	 * <br>
	 * <li>The <code><b>DEBUG</b></code> Level designates fine-grained
	 * informational events that are most useful to debug an application.<br>
	 * <li>The <code><b>TRACE</b></code> Level designates finer-grained
	 * informational events than the <code>DEBUG</code level.<br>
	 * <li>The <code><b>ALL</b></code> has the lowest possible rank and is
	 * intended to turn on all logging.
	 * 
	 * @param level
	 *            the new level
	 */
	//public void setLevel(final int level);
	
	/**
	 * It is an admin's helper method to set the log level on demand thru JMX,
	 * It can be done by changing the root log level entry in log properties
	 * too.<br>
	 * Sets the Root Log level, that is passed as an String. Please find the
	 * String corresponding to the level respectively.<BR>
	 * <BR>
	 * 
	 * &nbsp;&nbsp;0. &nbsp;The <code><b>OFF</b></code> has the highest possible
	 * rank and is intended to turn off logging.
	 * <ol>
	 * <li>The <code><b>FATAL</b></code> level designates very severe error
	 * events that will presumably lead the application to abort.<br>
	 * <li>The <code><b>ERROR</b></code> level designates error events that
	 * might still allow the application to continue running.<br>
	 * <li>The <code><b>WARN</b></code> level designates potentially harmful
	 * situations.<br>
	 * <li>The <code><b>INFO</b></code> level designates informational messages
	 * that highlight the progress of the application at coarse-grained level.
	 * <br>
	 * <li>The <code><b>DEBUG</b></code> Level designates fine-grained
	 * informational events that are most useful to debug an application.<br>
	 * <li>The <code><b>TRACE</b></code> Level designates finer-grained
	 * informational events than the <code>DEBUG</code> level.<br>
	 * <li>The <code><b>ALL</b></code> has the lowest possible rank and is
	 * intended to turn on all logging.
	 * </ol>
	 * @param level
	 *            the new level
	 */
	public void setLevel(final String level);
	
	/**
	 * Gets the help menu for level.
	 * 
	 * @return the level help
	 */
	//public String getLevelHelp();
	
	/**
	 * Gets the log4j content.
	 * 
	 * @return the content
	 */
	public Object getContent();
	
	/**
	 * Sets the content.
	 * 
	 * @param logFileConfig
	 *            the new content
	 */
	public void setContent(final Object logFileConfig);
}
