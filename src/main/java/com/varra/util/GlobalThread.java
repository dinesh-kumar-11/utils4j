/*
 * utils4j - GlobalThread.java, Aug 16, 2015 4:57:43 PM
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

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;
import static com.varra.props.VarraProperties.*;

/**
 * An global singleton {@link SafeThread} instance.<br>
 * Created for distribution ease. <br>
 * Use {@link #getGlobalThread(int)} to Initialize the the thread with the
 * specified number of threads. Otherwise it uses
 * {@link Executors#newCachedThreadPool()} and creates number of the threads on
 * demand.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class GlobalThread extends SafeThread
{
	
	/** The global thread. */
	private static GlobalThread globalThread;
	
	/**
	 * Instantiates a new global thread.
	 */
	private GlobalThread()
	{
		super(GlobalThread.class.getSimpleName(), Executors.newCachedThreadPool());
	}
	
	/**
	 * Instantiates a new global thread.
	 * 
	 * @param noOfThreads
	 *            the no of threads
	 */
	private GlobalThread(final int noOfThreads)
	{
		super(GlobalThread.class.getSimpleName(), Executors.newFixedThreadPool(noOfThreads));
	}
	
	/**
	 * Instantiates a new global thread.
	 *
	 * @param minNoOfThreads
	 *            the min no of threads
	 * @param maxNoOfThreads
	 *            the max no of threads
	 */
	private GlobalThread(final int minNoOfThreads, final int maxNoOfThreads)
	{
		super(GlobalThread.class.getSimpleName(), new ThreadPoolExecutor(minNoOfThreads, maxNoOfThreads, getWrapperProperty(GT_KEEP_ALIVE_TIME_PROPERTY, GT_KEEP_ALIVE_TIME_DEFAULT_VALUE), TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()));
	}
	
	/**
	 * Gets the singleton {@link SafeThread}. Instantiates the
	 * {@link GlobalThread} if not done so far with the number of threads, else
	 * returns the existing one.<br>
	 * It uses <b>{@link java.util.concurrent.ThreadPoolExecutor#ThreadPoolExecutor(int, int, long, TimeUnit, java.util.concurrent.BlockingQueue)}</b> to create the
	 * threads on demand.
	 *
	 * @param minNoOfThreads
	 *            the min no of threads
	 * @param maxNoOfThreads
	 *            the max no of threads
	 * @return the global thread
	 */
	public synchronized static GlobalThread getGlobalThread(final int minNoOfThreads, final int maxNoOfThreads)
	{
		if (globalThread == null)
		{
			globalThread = new GlobalThread(minNoOfThreads, maxNoOfThreads);
		}
		return globalThread;
	}
	
	/**
	 * Gets the singleton {@link SafeThread}. Instantiates the
	 * {@link GlobalThread} if not done so far with the number of threads, else
	 * returns the existing one.<br>
	 * It uses <b>{@link Executors#newFixedThreadPool(int)}</b> to
	 * create the threads on demand.
	 * 
	 * @param noOfThreads
	 *            the no of threads
	 * @return the global thread
	 */
	public synchronized static GlobalThread getGlobalThread(int noOfThreads)
	{
		if (globalThread == null)
		{
			globalThread = new GlobalThread(noOfThreads);
		}
		return globalThread;
	}
	
	/**
	 * Gets the singleton {@link SafeThread}. Instantiates the
	 * {@link GlobalThread} if not done so far with the number of threads, else
	 * returns the existing one.<br>
	 * It uses <b>{@link Executors#newCachedThreadPool()}</b> to create the
	 * threads on demand.
	 * 
	 * @return the global thread
	 */
	public synchronized static GlobalThread getGlobalThread()
	{
		if (globalThread == null)
		{
			globalThread = new GlobalThread();
		}
		return globalThread;
	}
}