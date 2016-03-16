/*
 * utils4j - GCThread.java, Aug 16, 2015 4:55:54 PM
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
package com.varra.util;

import com.varra.log.Logger;

import com.varra.props.VarraProperties;

/**
 * A Utility thread to request the GC Activity in background mode.<br>
 * Usage:<br>
 * {@link GCThread}.{@link #requestGC()};
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
public class GCThread implements Runnable
{
	
	/** The is running. */
	private static volatile boolean isRunning;
	
	/** The muter. */
	final static private Object mutor = new Object();
	
	/** The gc thread. */
	final private Thread gcThread;
	
	static
	{
		final GCThread gcThread = new GCThread();
		gcThread.start();
	}
	
	/**
	 * Instantiates a new gC thread.
	 */
	private GCThread()
	{
		super();
		gcThread = new Thread(this, this.getClass().getSimpleName());
	}
	
	/**
	 * Requests GC, but not wait till the GC completes. Just sends a GC request
	 * and come out of the context, GC will be running in background.
	 */
	public static void requestGC()
	{
		try
		{
			Logger.getLogger(GCThread.class).info("A fresh request has been made to initialse the GC.");
			synchronized (mutor)
			{
				mutor.notifyAll();
			}
		}
		catch (Exception e)
		{
			Logger.getLogger(GCThread.class).error("Got error while trying to request the GC ", e);
		}
	}
	
	/**
	 * Causes this thread to begin execution;.
	 */
	private synchronized void start()
	{
		if (gcThread != null && !gcThread.isAlive())
		{
			setRunning(true);
			gcThread.start();
		}
	}
	
	/**
	 * Forces the thread to stop executing.
	 */
	private static synchronized void stop()
	{
		setRunning(false);
	}
	
	/**
	 * Shutdowns the backGround {@link GCThread}.
	 */
	public static void shutdown()
	{
		Logger.getLogger(GCThread.class).info("Received the request to stop the GCThread, waiting to complete the background work.");
		stop();
		synchronized (mutor)
		{
			mutor.notifyAll();
		}
	}
	
	/**
	 * Sets the running.
	 * 
	 * @param isRunning
	 *            the isRunning to set
	 */
	private static void setRunning(boolean isRunning)
	{
		GCThread.isRunning = isRunning;
	}
	
	/**
	 * Checks if it is running.
	 * 
	 * @return the isRunning
	 */
	public static boolean isRunning()
	{
		return isRunning;
	}
	
	/**
	 * Do request gc.
	 */
	private void doRequestGC()
	{
		Logger.getLogger(GCThread.class).info("Intilizing the gc.");
		System.gc();
		Logger.getLogger(GCThread.class).info("Intilization of gc is done.");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		while (isRunning())
		{
			try
			{
				doRequestGC();
				
				synchronized (mutor)
				{
					Logger.getLogger(GCThread.class).info("Going to sleep mode.");
					mutor.wait();
					Logger.getLogger(GCThread.class).info("Waking up from a long sleep as someone has requested the GC.");
				}
			}
			catch (Exception e)
			{
				Logger.getLogger(GCThread.class).error("Got error while trying to initialze the GC ", e);
			}
		}
		Logger.getLogger(GCThread.class).info("GCThread has been stopped succefully.");
	}
	
	/**
	 * Sleep me.
	 */
	private static void sleepMe()
	{
		try
		{
			Thread.sleep(VarraProperties.DEF_MONITORING_INTERVAL);
		}
		catch (Exception e)
		{
			Logger.getLogger(GCThread.class).error("Got error while trying to initialze the GC ", e);
		}
	}
	
	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(final String[] args)
	{
		GCThread.requestGC();
		sleepMe();
		GCThread.requestGC();
		sleepMe();
		shutdown();
		GCThread.requestGC();
	}
}
