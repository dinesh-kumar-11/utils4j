/*
 * utils4j - Log4jConfigLoader.java, Sep 13, 2011 6:59:16 PM
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

import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;
import com.varra.exception.Log4jConfigLoaderException;
import com.varra.log.MyLogLevel;
import com.varra.util.AbstractConfigFileReader;

/**
 * Loads the log4jConfig file and monitors it, re-loads the log file whenever it
 * gets changed.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class Log4jConfigLoader implements Log4jConfigLoaderMBean
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2528586310301692957L;
	
	/** The Constant XML_EXTN. */
	private static final String XML_EXTN = "xml";
	
	/** The logger to log the debugging messages as application runs. */
	private final Logger logger;
	
	/** The Constant DEFAULT_LOGGING_INTERVAL. */
	private static final int DEFAULT_LOGGING_INTERVAL = 5 * 60 * 1000; // 5
																		// minutes
	/** The log4j config file name. */
	private final String log4jConfigFileName;
	
	/** The interval. */
	private int interval;
	
	/** The last loaded. */
	private long lastLoaded;
	
	/** The log content. */
	final private StringBuffer logContent = new StringBuffer();
	
	/** The level. */
	private String level;
	
	/**
	 * Instantiates a new log4j config loader.
	 * 
	 * @param log4jConfigFileName
	 *            the log4j config file name
	 * @param interval
	 *            the interval
	 * @throws Log4jConfigLoaderException
	 *             the log4j config loader exception
	 */
	public Log4jConfigLoader(final String log4jConfigFileName, final int interval) throws Log4jConfigLoaderException
	{
		if (isValidFile(log4jConfigFileName))
		{
			this.log4jConfigFileName = log4jConfigFileName;
			this.interval = interval > 0 ? interval : DEFAULT_LOGGING_INTERVAL;
			
			loadLog4jConfig();
			logger = Logger.getLogger(Log4jConfigLoader.class);
		}
		else
		{
			throw new Log4jConfigLoaderException(
					"Log4j properties file provided is null. So couldn't initialize the logging system.");
		}
	}
	
	/**
	 * Checks if is valid log file name.
	 * 
	 * @param log4jConfigFileName
	 *            the log4j config file name
	 * @return true, if is valid log file name
	 */
	private boolean isValidFile(final String log4jConfigFileName)
	{
		return (log4jConfigFileName != null && log4jConfigFileName.trim().length() > 0) ? new File(log4jConfigFileName)
				.exists() : false;
	}
	
	/**
	 * Loads the log4j config.
	 * 
	 * @throws Log4jConfigLoaderException
	 *             the log4j config loader exception
	 */
	private void loadLog4jConfig() throws Log4jConfigLoaderException
	{
		if (log4jConfigFileName.endsWith(XML_EXTN))
		{
			DOMConfigurator.configureAndWatch(log4jConfigFileName, interval);
		}
		else
		{
			PropertyConfigurator.configureAndWatch(log4jConfigFileName, interval);
		}
		loadLogContent();
		getLevel();
		lastLoaded = System.currentTimeMillis();
	}
	
	/**
	 * Loads the log content.
	 */
	private void loadLogContent()
	{
		try
		{
			logContent.setLength(0);
			logContent.append(AbstractConfigFileReader.readStreamContentAsString(new FileInputStream(
					log4jConfigFileName)));
		}
		catch (Exception e)
		{
			logger.error("Exception while loading the file content", e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.jmx.mbean.Log4jConfigLoaderMBean#lastLoaded()
	 */
	public long lastLoaded()
	{
		return lastLoaded;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.jmx.mbean.Log4jConfigLoaderMBean#reload()
	 */
	public void reload()
	{
		try
		{
			LogManager.resetConfiguration();
			loadLog4jConfig();
			lastLoaded = System.currentTimeMillis();
			loadLogContent();
		}
		catch (Log4jConfigLoaderException e)
		{
			// Here I need this as I may get null by mistake in worst case.
			if (logger != null)
				logger.error("Raised a request to reload the log config file, Got error. ", e);
		}
	}
	
	// TODO for next releases
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.jmx.mbean.Log4jConfigLoaderMBean#reload(java.lang.String)
	 */
	public void reload(final String fileName)
	{
		throw new UnsupportedOperationException("It is not supported in this release.");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.jmx.mbean.Log4jConfigLoaderMBean#getLogFileName()
	 */
	public String getLogFileName()
	{
		return log4jConfigFileName;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.jmx.mbean.Log4jConfigLoaderMBean#getInterval()
	 */
	public int getInterval()
	{
		return interval;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.jmx.mbean.Log4jConfigLoaderMBean#setInterval(int)
	 */
	public void setInterval(final int interval)
	{
		if (interval > 0)
		{
			this.interval = interval;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.jmx.mbean.Log4jConfigLoaderMBean#getLevel()
	 */
	public String getLevel()
	{
		level = getLevelAsString();
		return level;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.jmx.mbean.Log4jConfigLoaderMBean#getLevelAsString()
	 */
	public String getLevelAsString()
	{
		final Level level = Logger.getRootLogger().getLevel();
		if (level != null && level.toString() != null && level.toString().trim().length() > 0)
		{
			return level.toString().toUpperCase();
		}
		return null;
	}
	
	/**
	 * Gets the level as int.
	 * 
	 * @return the int
	 */
	@SuppressWarnings("unused")
	private int getLevelAsInt()
	{
		final Level level = Logger.getRootLogger().getLevel();
		if (level != null && level.toString() != null && level.toString().trim().length() > 0)
		{
			final String levelStr = level.toString().toUpperCase();
			try
			{
				return MyLogLevel.valueOf(levelStr).ordinal();
			}
			catch (Exception e)
			{
				// This is to get rid of the exception from enum if any... !!!
				logger.error("Got the exception when tried to convert the log level: " + level.toString(), e);
			}
		}
		return -1;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.jmx.mbean.Log4jConfigLoaderMBean#setLevel(int)
	 */
	public void setLevel(final int level)
	{
		if (level > 0 && level < MyLogLevel.values().length)
		{
			try
			{
				Logger.getRootLogger().setLevel(Level.toLevel(MyLogLevel.values()[level].toString()));
			}
			catch (Exception e)
			{
				logger.error("Got error while trying to set the level: " + level, e);
			}
		}
		else
		{
			logger.info("Raised a request to set an invalid level: " + level);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.jmx.mbean.Log4jConfigLoaderMBean#setLevel(java.lang.String)
	 */
	public void setLevel(final String level)
	{
		if (isMyLogLevelContains(level))
		{
			try
			{
				logger.info("Got a request to change the log level from: " + getLevel() + ", to: " + level);
				Logger.getRootLogger().setLevel(Level.toLevel(MyLogLevel.valueOf(level).ordinal()));
				logger.info("Succesfully changed the log level, new level: " + getLevel());
			}
			catch (Exception e)
			{
				logger.error("Got error while trying to set the level: " + level, e);
			}
		}
		else
		{
			logger.info("Raised a request to set an invalid level: " + level);
		}
	}
	
	/**
	 * Checks if is my log level contains.
	 * 
	 * @param level
	 *            the level
	 * @return true, if is my log level contains
	 */
	private boolean isMyLogLevelContains(final String level)
	{
		if (level != null && level.trim().length() > 0)
		{
			final MyLogLevel[] values = MyLogLevel.values();
			for (int i = 0; i < values.length; i++)
			{
				final MyLogLevel myLogLevel = values[i];
				if (myLogLevel.name().equalsIgnoreCase(level))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.jmx.mbean.Log4jConfigLoaderMBean#getLevelHelp()
	 */
	public String getLevelHelp()
	{
		return "Please find the number corresponding to the level respectively.\n\n"
				+ "0. The OFF has the highest possible rank and is intended to turn off logging.\n"
				+ "1. The FATAL level designates very severe error events that will presumably lead the application to abort.\n"
				+ "2. The ERROR level designates error events that might still allow the application to continue running.\n"
				+ "3. The WARN level designates potentially harmful situations.\n"
				+ "4. The INFO level designates informational messages that highlight the progress of the application at coarse-grained level.\n"
				+ "5. The DEBUG Level designates fine-grained informational events that are most useful to debug an application.\n"
				+ "6. The TRACE Level designates finer-grained informational events than the DEBUG level.\n"
				+ "7. The ALL has the lowest possible rank and is intended to turn on all logging.\n";
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.jmx.mbean.Log4jConfigLoaderMBean#getContent()
	 */
	public Object getContent()
	{
		try
		{
			return logContent.toString();
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		
		return "Got Error while loading the file, look at the log file.";
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.varra.jmx.mbean.Log4jConfigLoaderMBean#setLogFileName(java.lang.String)
	 */
	public String setLogFileName(String logFileName)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.varra.jmx.mbean.Log4jConfigLoaderMBean#setContent(java.lang.String)
	 */
	public void setContent(Object logFileConfig)
	{
		// TODO Auto-generated method stub
		
	}
	
}
