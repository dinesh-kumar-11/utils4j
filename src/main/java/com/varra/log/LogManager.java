/*
 * utils4j - LogManager.java, Aug 16, 2015 4:24:53 PM
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

import java.util.LinkedHashMap;
import java.util.Map;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;
import com.varra.props.VarraProperties;
import com.varra.util.ObjectUtils;
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
public class LogManager implements LoggerConstants
{
	
	/** The loggers. */
	private static final Map<String, Logger> loggers = new LinkedHashMap<String, Logger>();
	
	/** The log class name. */
	private static String logClassName;
	
	/** The log class. */
	private static Class<Log> logClass;
	
	/** The first time. */
	private static boolean firstTime = true;
	
	/**
	 * Gets the logger.
	 * 
	 * @param name
	 *            the name
	 * @return the logger
	 */
	public static Logger getLogger(String name)
	{
		if (loggers.get(name) == null)
		{
			try
			{
				final Log log = getLogClass(name).newInstance();
				log.setName(name);
				final Logger logger = new Logger(log, name);
				loggers.put(name, logger);
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
		return loggers.get(name);
	}
	
	/**
	 * Sets the logger to perform the actual logging mechanism by delegating the
	 * tasks. <br>
	 * <br>
	 * Provides a way to integrate the {@link Logger} with the preferred Logging
	 * framework..
	 * 
	 * @param logClassName
	 *            the logClassName to set
	 */
	public static void setLogClassName(String logClassName)
	{
		LogManager.logClassName = logClassName;
	}
	
	/**
	 * Gets the log class name.
	 * 
	 * @return the logClassName
	 */
	public static String getLogClassName()
	{
		return logClassName;
	}
	
	/**
	 * Sets the log class.
	 * 
	 * @param logClass
	 *            the logClass to set
	 */
	public static void setLogClass(Class<Log> logClass)
	{
		LogManager.logClass = logClass;
	}
	
	/**
	 * Gets the log class.
	 *
	 * @param name
	 *            the name
	 * @return the logClass
	 */
	@SuppressWarnings("unchecked")
	public static Class<Log> getLogClass(String name)
	{
		if (logClass == null)
		{
			try
			{
				final Object className = VarraProperties.getAppProperty(LOG_CLASS_FQ_NAME);
				if (ObjectUtils.isNotNull(className))
				{
					logClass = (Class<Log>) Class.forName(className.toString());
				}
				else if (isFirstTime())
				{
					System.err.println("com.varra.log.Logger is not instantiated properly, caller: "+name);
					System.err.println("Set Logger Class as: VarraProperties.setProperty(LoggerConstants.LOG_CLASS_FQ_NAME, Log4jLogger[ConsoleLogger].class.getName())");
					
					logClass = (Class<Log>) Class.forName(ConsoleLogger.class.getName());
					setFirstTime(false);
				}
			}
			catch (Exception e)
			{
				// e.printStackTrace();
			}
		}
		return logClass;
	}
	
	/**
	 * Checks if is first time.
	 * 
	 * @return true, if is first time
	 */
	private static boolean isFirstTime()
	{
		return firstTime;
	}
	
	/**
	 * Sets the first time.
	 * 
	 * @param firstTime
	 *            the firstTime to set
	 */
	private static void setFirstTime(boolean firstTime)
	{
		LogManager.firstTime = firstTime;
	}
	
	/**
	 * Shutdown all.
	 * 
	 * @param mode
	 *            the mode
	 */
	public static void shutdownAll(ShutdownMode mode)
	{
		loggers.clear();
	}
}
