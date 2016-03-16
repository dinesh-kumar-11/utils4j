/*
 * utils4j - SafeThread.java, Sep 15, 2011 3:38:10 PM
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

import static com.varra.util.ObjectUtils.*;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import com.varra.log.Logger;
import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;
import com.varra.listener.TimerTaskListener;

/**
 * An Synchronized RealTime Event storing and processing thread. It will be very
 * helpful in replacing the existing thread implementations, can be used in any
 * context. It receives the events from the different sources and be processed
 * based on the business logic provided.
 * 
 * <br>
 * It Provides the successful event execution guaranteed with the help of a
 * thread pool. It will not miss even a single event from the execution.
 * 
 * <br>
 * 
 * Use {@link #onTimerTask(EnhancedTimerTask)} to add an event to this thread
 * for execution. <br>
 * 
 * It has backing with a synchronized {@link FIFOQueue} to store and to execute. <br>
 * It automatically be in waiting mode if doesn't have packful of events, else
 * execute them and clears the Queue. <br>
 * 
 * Also provides notifications interested in the events based on their
 * execution/completion status<br> @see {@link TimerTaskListener}
 * 
 * <br>
 * Use {@link #addTimerTaskListener(TimerTaskListener, Class)} to
 * register for notifications.<br>
 * 
 * Use {@link #removeTimerTaskListener(TimerTaskListener, Class)} to
 * unregister from notifications.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class SafeThread implements Runnable, TimerTaskReceiver
{
	
	/** The counter. */
	private static int counter;
	
	/** The logger to log the debugging messages as application runs. */
	private final Logger logger;
	
	/** The is running. */
	private volatile boolean isRunning;
	
	/** The event handler. */
	private Thread eventHandler;
	
	/** The queue. */
	private transient FIFOQueue<EnhancedTimerTask> queue;
	
	/** The listeners. */
	private transient Map<Class<? extends EnhancedTimerTask>, TimerTaskListener> listeners;
	
	/** The thread pool. */
	private ExecutorService threadPool;
	
	/** The interval. */
	protected long interval = 1000;
	
	/**
	 * Instantiates a new safe thread.
	 * 
	 * @param threadPool
	 *            the thread pool
	 */
	public SafeThread(final ExecutorService threadPool)
	{
		this("" + counter++, threadPool);
	}
	
	/**
	 * Instantiates a new safe thread.
	 * 
	 * @param name
	 *            the name
	 * @param threadPool
	 *            the thread pool
	 */
	public SafeThread(final String name, final ExecutorService threadPool)
	{
		eventHandler = new Thread(this, isNotNull(name) ? name : this.getClass().getSimpleName());
		this.threadPool = threadPool;
		queue = new FIFOQueue<EnhancedTimerTask>();
		listeners = new LinkedHashMap<Class<? extends EnhancedTimerTask>, TimerTaskListener>();
		logger = Logger.getLogger(this.getClass().getName() + "-" + name);
	}
	
	/**
	 * Causes this thread to begin execution;.
	 */
	public synchronized void start()
	{
		if (!(isRunning() || eventHandler.isAlive()))
		{
			setRunning(true);
			eventHandler.start();
		}
	}
	
	/**
	 * Forces the thread to stop executing.
	 */
	public synchronized void stop()
	{
		setRunning(false);
	}
	
	/**
	 * Sets the running.
	 * 
	 * @param isRunning
	 *            the isRunning to set
	 */
	private void setRunning(boolean isRunning)
	{
		this.isRunning = isRunning;
	}
	
	/**
	 * Checks if it is running.
	 * 
	 * @return the isRunning
	 */
	public boolean isRunning()
	{
		return isRunning;
	}
	
	/**
	 * Shutdowns the backGround {@link SafeThread}.
	 */
	public void shutdown()
	{
		try
		{
			logger.info("Received the request to stop the SafeThread, waiting to complete the background work.");
			stop();
			queue.signalAll();
			threadPool.shutdownNow();
		}
		catch (Exception e)
		{
			
		}
	}
	
	/**
	 * Gets the tasks.
	 * 
	 * @return the tasks
	 */
	protected List<EnhancedTimerTask> getTasks()
	{
		return queue;
	}
	
	/**
	 * Gets the listeners.
	 * 
	 * @return the tasks
	 */
	public Map<Class<? extends EnhancedTimerTask>, TimerTaskListener> getListeners()
	{
		return Collections.unmodifiableMap(listeners);
	}
	
	/**
	 * Adds the timer task listener.
	 * 
	 * @param listener
	 *            the listener
	 * @param type
	 *            the type
	 * @return true, if successful
	 */
	public synchronized boolean addTimerTaskListener(TimerTaskListener listener, Class<? extends EnhancedTimerTask> type)
	{
		if (isNotNull(listener) && isNotNull(type))
		{
			return listeners.put(type, listener) != null;
		}
		return false;
	}
	
	/**
	 * Removes the timer task listener.
	 * 
	 * @param listener
	 *            the listener
	 * @param type
	 *            the type
	 * @return true, if successful
	 */
	public synchronized boolean removeTimerTaskListener(TimerTaskListener listener, Class<? extends EnhancedTimerTask> type)
	{
		if (isNotNull(listener) && isNotNull(type))
		{
			return listeners.remove(type) != null;
		}
		return false;
	}
	
	/**
	 * Call back event.
	 * 
	 * @param task
	 *            the task
	 */
	protected synchronized void callBackEvent(EnhancedTimerTask task)
	{
		final TimerTaskListener listener = listeners.get(task.getClass());
		if (isNotNull(listener))
		{
			if (task.isCanceled())
			{
				listener.omitOnCancel(task);
			}
			else if (task.isFinished())
			{
				listener.omitOnFinish(task);
			}
			else if (task.isExpired())
			{
				listener.omitOnExpiry(task);
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.varra.util.TimerTaskReceiver#onTimerTask(com.varra.util.EnhancedTimerTask)
	 */
	public void onTimerTask(final EnhancedTimerTask timerTask)
	{
		if (isNotNull(timerTask))
		{
			logger.info("Received a new Timer Task: " + timerTask);
			queue.push(timerTask);
			queue.signalAll();
		}
		else
		{
			logger.info("Received a new Timer Task as null, hence discarding it.");
		}
	}
	
	/**
	 * Wait for some time.
	 * 
	 * @param interval
	 *            the interval
	 */
	private void waitForSomeTime(final long interval)
	{
		synchronized (this)
		{
			try
			{
				logger.trace("I'll be in waiting for: " + interval + " ms");
				this.wait(interval);
			}
			catch (InterruptedException e)
			{
				logger.error(e);
			}
		}
	}
	
	/**
	 * Sets the interval.
	 * 
	 * @param interval
	 *            the interval to set
	 */
	public void setInterval(long interval)
	{
		this.interval = interval;
	}
	
	/**
	 * Gets the interval.
	 * 
	 * @return the interval
	 */
	public long getInterval()
	{
		return interval;
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
				if (queue.size() == 0)
				{
					try
					{
						/**
						 * This will be in waiting mode till any one of these
						 * events trigger.
						 * 
						 * 1. Once have packful of Events.
						 * 2. Someone calls Signal/SignallAll.
						 * 3. Someone tries to kill this thread.
						 * 4. JVM goes shutdown.
						 */
						logger.debug("Going to waiting mode as I don't have the events to process.");
						queue.await();
					}
					catch (Exception e)
					{
						// what I have to do here ... !
					}
				}
				
				if (!isRunning())
					break;
				
				final int size = queue.size();
				logger.trace("I've " + size + " Event(s), and going to process.");
				
				for (int index = 0; index < size; index++)
				{
					final EnhancedTimerTask task = queue.pop();
					if (task.isCanceled() || task.isFinished() || task.isExpired())
					{
						logger.info("Going to skip the event execution and remove the event from repository as It is " + (task.isFinished() ? "Finished" : (task.isCanceled() ? "cancelled" : "Expired")) + ", event: " + task);
						
						/**
						 * Notifies the task execution status to the listener
						 * with the task i.e just completed execution.
						 */
						callBackEvent(task);
						continue;
					}
					else if (task.getNoOfPendingTimes() > 0)
					{
						final long period = task.getPeriod();
						Boolean executeThis = Boolean.FALSE;
						if (task.getNoOfExecTimes() == 0) // Initially For the First time.
						{
							executeThis = Boolean.TRUE;
						}
						else if (period > 0 && task.getElapsedTime() > period)
						{
							 /**
							  * Being executed already, but because of delayed processing its dragged to next time interval, hence should not execute this again.
							  */
							if (task.isRunning())
							{
								logger.warn("Being executed already, but because of delayed processing its dragged to next time interval, hence should not execute this again: "+task.getName());
								executeThis = Boolean.FALSE;
							}
							else
							{
								executeThis = Boolean.TRUE;
							}
						}
						else if (task.isExecuteImmediatelySet()) // Request for immediate execution.
						{
							executeThis = Boolean.TRUE;
							logger.debug("Going to execute the task: " + task.getName()+", as it has requested for immediate execution.");
							task.setExecuteImmediately(false);
						}
						if (executeThis)
						{
							logger.debug("Going to execute the task: " + task);
							task.incrementNoOfExecTimes();
							threadPool.submit(task);
							task.updateNextExecutionTime();
							task.setPrevExecutionTime(System.currentTimeMillis());
						}
					}
					else
					{
						/**
						 * TODO Keep this for legacy applications. Next versions
						 * will remove this.
						 */
						//task.setFinished(true);
						
						task.setExpired(true);
					}
					
					/**
					 * Need to insert it as it is not finished yet, and to
					 * maintain the list order used addFirst();
					 */
					queue.addFirst(task);
					// logger.info("Currently Executing Task: " + task);
				}
				waitForSomeTime(getInterval());
			}
			catch (Exception ex)
			{
				logger.error("Got an unExpected Error while processing events. Please have a look at stack trace.", ex);
			}
		}
	}
}