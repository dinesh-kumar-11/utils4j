/*
 * utils4j - EnhancedTimerTask.java, Apr 2, 2013 4:50:17 PM
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

import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.varra.exception.ComputingException;

/**
 * A task that is executed at a specified time.
 * <p>
 * Subclasses implement the Periodicity, if needed. Two TimerTasks are compared
 * with their name and next execution time.
 * 
 * Note: <b> Use <i>{@link #compute()} </i> method to write the business logic.</b>
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 1.0
 */
public abstract class EnhancedTimerTask implements Comparable<EnhancedTimerTask>, Runnable, Computable
{
	
	/** The Constant ALWAYS_TRUE. */
	private static final int ALWAYS_TRUE = 1;
	
	/** The next execution time. */
	private transient long nextExecutionTime;
	
	/** The prev execution time. */
	private transient long prevExecutionTime = 0;
	
	/** If or not this is finished. */
	private transient boolean isFinished;
	
	/** If or not this is canceled. */
	private transient boolean isCanceled;
	
	/** If or not this is expired. */
	private transient boolean isExpired;
	
	/** The period. */
	private transient long period;
	
	/** If or not this is periodic. */
	private transient boolean isPeriodic;
	
	/** The event. */
	private Object event;
	
	/** The no of times, By default It is 1. */
	private int noOfTimes = 1;
	
	/** The no of exec times. */
	private transient int noOfExecTimes = 0;
	
	/** If or not this should be executed immediately. */
	private transient boolean executeImmediately;
	
	/** If or not this is . */
	private transient boolean isDaemon;
	
	/** The name. */
	private final String name;
	
	/** The running. */
	private transient boolean running;
	
	/** The single executable. */
	private transient boolean singleExecutable;
	
	/**
	 * Constructor for subclasses.
	 * 
	 */
	public EnhancedTimerTask()
	{
		super();
		
		this.name = this.getClass().getName();
	}
	
	/**
	 * Constructor for subclasses.
	 * 
	 * @param name
	 *            the name
	 */
	public EnhancedTimerTask(final String name)
	{
		super();
		
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see com.varra.util.Computable#compute()
	 */
	@Override
	public abstract void compute() throws ComputingException;
	
	/**
	 * This method implementing is not advised, consider implementing {@link #compute()} method.
	 */
	public void run()
	{
		try
		{
			this.setRunning(true);
			compute();
			this.setRunning(false);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass().getName()).error("Error in executing: " + getName(), e);
		}
	}
	
	/**
	 * Sets the periodic.
	 * 
	 * @param isPeriodic
	 *            the isPeriodic to set
	 */
	public void setPeriodic(final boolean isPeriodic)
	{
		this.isPeriodic = isPeriodic;
	}
	
	/**
	 * Returns whether this task is periodic. By default return false.
	 * 
	 * @return true, if is periodic
	 * @see #getPeriod
	 */
	public boolean isPeriodic()
	{
		return isPeriodic;
	}
	
	/**
	 * Sets the period.
	 * 
	 * @param period
	 *            the period to set
	 */
	public void setPeriod(final long period)
	{
		this.period = period;
		this.isPeriodic = true;
	}
	
	/**
	 * Returns the period of this task. By default returns 0.
	 * 
	 * @return the period
	 * @see #isPeriodic
	 */
	public long getPeriod()
	{
		return period;
	}
	
	/**
	 * Returns the next time at which the task will be executed, ie the.
	 * 
	 * @return the next execution time {@link #run} method is called.
	 * @see #updateNextExecutionTime
	 */
	public long getNextExecutionTime()
	{
		return nextExecutionTime;
	}
	
	/**
	 * Sets the next execution time.
	 * 
	 * @see #getNextExecutionTime
	 */
	public void updateNextExecutionTime()
	{
		this.nextExecutionTime = System.currentTimeMillis() + getPeriod();
	}
	
	/**
	 * Marks this task as finished or not. When a task is finished, its
	 * 
	 * @param value
	 *            the new finished {@link #run} method will not be called
	 *            anymore.
	 * @see #isFinished
	 */
	protected void setFinished(final boolean value)
	{
		isFinished = value;
	}
	
	/**
	 * Returns whether this task is finished.
	 * 
	 * @return true, if is finished
	 * @see #setFinished
	 */
	public boolean isFinished()
	{
		return isFinished;
	}
	
	/**
	 * Checks if is expired.
	 * 
	 * @return the isExpired
	 */
	public boolean isExpired()
	{
		return isExpired;
	}
	
	/**
	 * Sets the expired.
	 * 
	 * @param isExpired
	 *            the isExpired to set
	 */
	public void setExpired(boolean isExpired)
	{
		this.isExpired = isExpired;
	}
	
	/**
	 * Returns whether this task is canceled.
	 * 
	 * @return true, if is canceled
	 * @see #cancel
	 */
	public boolean isCanceled()
	{
		return isCanceled;
	}
	
	/**
	 * Cancels this {@link TimerTask}.
	 */
	public void cancel()
	{
		isCanceled = true;
	}
	
	/**
	 * Compares 2 TimeTasks by comparing their next execution times.
	 * 
	 * @param other
	 *            the other
	 * @return the int
	 * @see #getNextExecutionTime
	 */
	public int compareTo(final EnhancedTimerTask other)
	{
		int returnValue = 0;
		if (other == null)
		{
			returnValue = 1;
		}
		else if (other == this)
		{
			returnValue = 0;
		}
		else
		{
			if (getNextExecutionTime() > other.getNextExecutionTime())
			{
				returnValue = 1;
			}
			else if (getNextExecutionTime() < other.getNextExecutionTime())
			{
				returnValue = -1;
			}
		}
		return returnValue;
	}
	
	/**
	 * Sets the event.
	 * 
	 * @param event
	 *            the event to set
	 */
	public void setEvent(final Object event)
	{
		this.event = event;
	}
	
	/**
	 * Gets the event.
	 * 
	 * @return the event
	 */
	public Object getEvent()
	{
		return event;
	}
	
	/**
	 * Sets the prev execution time.
	 * 
	 * @param prevExecutionTime
	 *            the prevExecutionTime to set
	 */
	public void setPrevExecutionTime(final long prevExecutionTime)
	{
		this.prevExecutionTime = prevExecutionTime;
	}
	
	/**
	 * Gets the prev execution time.
	 * 
	 * @return the prevExecutionTime
	 */
	public long getPrevExecutionTime()
	{
		return prevExecutionTime;
	}
	
	/**
	 * Sets the no of times to execute this particular task.
	 * 
	 * @param noOfTimes
	 *            the noOfTimes to set
	 */
	public void setNoOfTimes(final int noOfTimes)
	{
		this.noOfTimes = noOfTimes;
	}
	
	/**
	 * Gets the no of times that executes this particular task.
	 * 
	 * @return the noOfTimes
	 */
	public int getNoOfTimes()
	{
		return noOfTimes;
	}
	
	/**
	 * Gets the no of pending times.
	 * 
	 * @return the noOfPendingTimes
	 */
	public int getNoOfPendingTimes()
	{
		if (isDaemon())
		{
			return ALWAYS_TRUE;
		}
		return noOfTimes - noOfExecTimes;
	}
	
	/**
	 * Increments the no of exec times.
	 */
	public void incrementNoOfExecTimes()
	{
		this.noOfExecTimes++;
	}
	
	/**
	 * Gets the no of exec times.
	 * 
	 * @return the noOfExecTimes
	 */
	public int getNoOfExecTimes()
	{
		return noOfExecTimes;
	}
	
	/**
	 * Gets the elapsed time.
	 * 
	 * @return the elapsedTime
	 */
	public long getElapsedTime()
	{
		if (prevExecutionTime == 0)
		{
			return 0;
		}
		return System.currentTimeMillis() - prevExecutionTime;
	}
	
	/**
	 * Checks if is daemon.
	 * 
	 * @return the isDaemon
	 */
	public boolean isDaemon()
	{
		return isDaemon;
	}
	
	/**
	 * Sets the daemon.
	 * 
	 * @param isDaemon
	 *            the isDaemon to set
	 */
	public void setDaemon(boolean isDaemon)
	{
		this.isDaemon = isDaemon;
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
	 * Sets this flag if something happens and needs to execute this immediately
	 * rather waiting till the interval expires. <br>
	 * Mostly useful in RealTime event processing, signal this flag when a event
	 * received. <br>
	 * Note: <b> This doesn't guarantee the immediate execution, rather its just
	 * like requesting, {@link GlobalThread} will run this as soon as it has
	 * free worker threads.</b>
	 * 
	 * @param executeImmediately
	 *            the executeImmediately to set
	 * @see #isExecuteImmediatelySet()
	 * @since 3.0
	 */
	public void setExecuteImmediately(boolean executeImmediately)
	{
		this.executeImmediately = executeImmediately;
	}
	
	/**
	 * Checks if this is set for immediate execution.
	 * 
	 * @return the executeImmediately
	 * @since 3.0
	 */
	public boolean isExecuteImmediatelySet()
	{
		return executeImmediately;
	}
	
	/**
	 * Sets this flag to avoid duplicate execution of this in case of the
	 * primary execution takes much time than expected.
	 * 
	 * @param running
	 *            the running to set
	 * @see #isRunning()
	 * @since 3.0
	 */
	public void setRunning(boolean running)
	{
		if (singleExecutable)
		{
			this.running = running;
		}
	}
	
	/**
	 * Checks if this is already in execution.
	 * 
	 * @return the running
	 * @since 3.0
	 */
	public boolean isRunning()
	{
		return running;
	}
	
	/**
	 * Sets the single executable to enable single execution of this instance at
	 * any time.
	 * 
	 * Note: <b> If one instance is being executed by any thread, any other
	 * thread can't execute this.</b>
	 * 
	 * @param singleExecutable
	 *            the singleExecutable to set
	 */
	public void setSingleExecutable(boolean singleExecutable)
	{
		this.singleExecutable = singleExecutable;
	}
	
	/**
	 * Checks if is single executable.
	 * 
	 * @return the singleExecutable
	 */
	public boolean isSingleExecutable()
	{
		return singleExecutable;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("EnhancedTimerTask [name=");
		builder.append(name);
		builder.append(", isDaemon=");
		builder.append(isDaemon);
		if (isPeriodic)
		{
			builder.append(", isPeriodic=");
			builder.append(isPeriodic);
			builder.append(", period=");
			builder.append(period);
		}
		builder.append(", Total noOfTimes=");
		builder.append(noOfTimes);
		builder.append(", noOfExecTimes=");
		builder.append(noOfExecTimes);
		builder.append(", nextExecutionTime=");
		builder.append(nextExecutionTime);
		builder.append(", prevExecutionTime=");
		builder.append(prevExecutionTime);
		builder.append(", isFinished=");
		builder.append(isFinished);
		builder.append(", singleExecutable=");
		builder.append(singleExecutable);
		builder.append(", running=");
		builder.append(running);
		builder.append(", isCanceled=");
		builder.append(isCanceled);
		builder.append(", event=");
		builder.append(event);
		builder.append("]");
		return builder.toString();
	}
}
