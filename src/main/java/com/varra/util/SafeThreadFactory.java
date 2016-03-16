/*
 * utils4j - SafeThreadFactory.java, Apr 1, 2013 1:22:43 PM
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

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.varra.log.Logger;

/**
 * 
 * The Safe thread factory which sets/resets the task's running status flag to
 * avoid the duplicate execution of same kind.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 1.0
 * 
 */
public class SafeThreadFactory implements ThreadFactory
{
	
	/** The logger to log the debugging messages as application runs. */
	private static final Logger logger = Logger.getLogger(SafeThreadFactory.class);
	
	/** The Constant poolNumber. */
	private static final AtomicInteger poolNumber = new AtomicInteger(1);
	
	/** The group. */
	private final ThreadGroup group;
	
	/** The thread number. */
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	
	/** The name prefix. */
	private final String namePrefix;
	
	/**
	 * Instantiates a new default thread factory.
	 */
	public SafeThreadFactory()
	{
		final SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
		
		logger.info("Initialized successfully with group: " + group + ", namePrefix: " + namePrefix);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
	 */
	public Thread newThread(final Runnable task)
	{
		final Thread newThread = new Thread(group, task, namePrefix + threadNumber.getAndIncrement(), 0)
		{
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run()
			{
				logger.info("Set the running state to true.");
				try
				{
					if (ObjectUtils.isNotNull(task))
					{
						task.run();
					}
				}
				catch (Exception ex)
				{
					logger.error("Got an unExpected Error while processing event.", ex);
				}
				try
				{
					if (task instanceof EnhancedTimerTask)
					{
						((EnhancedTimerTask) task).setRunning(false);
						logger.info("Set the running state to false.");
					}
				}
				catch (Exception e)
				{
					// What can I do here, in fact not required.!!
				}
			}
		};
		if (newThread.isDaemon())
			newThread.setDaemon(false);
		if (newThread.getPriority() != Thread.NORM_PRIORITY)
			newThread.setPriority(Thread.NORM_PRIORITY);
		
		logger.info("Created a new thread successfully: " + newThread);
		return newThread;
	}
}