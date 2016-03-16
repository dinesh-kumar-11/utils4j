/*
 * utils4j - FIFOQueue.java, Aug 16, 2015 4:55:06 PM
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

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * It adds the thread-safe functionality to the {@link LinkedList} where all the
 * operations from/to this list are synchronized.<br>
 * And it adds the fixed size functionality too to {@link LinkedList} where as
 * {@link LinkedList} does not provide it by default.
 *
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * @param <E>
 *            the element type {@link LinkedList} does not provide
 *            synchronization by default. And it behaves like fixed and not
 *            fixed.
 * 
 *            We can make it as fixed by providing the isFixed tag as true.
 *            something as below.. <br>
 *            {@link FIFOQueue} fifoQueue = new {@link FIFOQueue}(1000, true);
 *            <br>
 *            It has buffer overflow notifications too. And notifies the
 *            listeners with the removed event if any registered to it.
 * 			
 *            <br>
 *            <br>
 *            by default it is Not fixed.
 */
public class FIFOQueue<E> implements Collection<E>, List<E>, Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5863956630898112193L;

	/**
	 * Used to lock the list to protect the list from the concurrent writes.
	 */
	private final Lock lock = new ReentrantLock();
	
	/** The condition. */
	private final Condition condition = lock.newCondition();
	
	/** Actual back-end storage for the elements. */
	private final LinkedList<E> list;
	
	/** Specifies whether it is fixed or not. */
	private boolean isFixed = false;
	
	/** The capacity. */
	private int capacity = -1;
	
	/** to hold the Listeners List. */
	private final transient LinkedList<BufferOverflowListener<E>> fifoQueueListeners = new LinkedList<BufferOverflowListener<E>>();
	
	/**
	 * Creates a list of small initial capacity.
	 */
	public FIFOQueue()
	{
		list = new LinkedList<E>();
	}
	
	/**
	 * Creates a list of specified initial capacity if the {@link FIFOQueue} is
	 * not fixed, else it creates a Queue of the specified length; unless the
	 * list size reaches the specified capacity, operations on this list will
	 * not allocate memory (no lazy object creation).
	 * 
	 * @param capacity
	 *            the initial capacity.
	 * @param isFixed
	 *            the flag to tell whether it is gonna be fixed or not.
	 */
	public FIFOQueue(int capacity, boolean isFixed)
	{
		list = new LinkedList<E>();
		this.setFixed(isFixed);
		if (isFixed())
		{
			this.capacity = capacity;
		}
	}
	
	/**
	 * Creates a list containing the specified values, in the order they are
	 * returned by the collection's iterator.
	 * 
	 * @param values
	 *            the values to be placed into this list.
	 */
	public FIFOQueue(Collection<? extends E> values)
	{
		list = new LinkedList<E>(values);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	public boolean remove(Object value)
	{
		lock.lock();
		try
		{
			return list.remove(value);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	/**
	 * Adds the all.
	 * 
	 * @param c
	 *            the c
	 * @return true, if successful
	 */
	public boolean addAll(Collection<? extends E> c)
	{
		boolean modified = false;
		Iterator<? extends E> itr = c.iterator();
		while (itr.hasNext())
		{
			if (add(itr.next()))
			{
				modified = true;
			}
		}
		return modified;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c)
	{
		lock.lock();
		try
		{
			return list.containsAll(c);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c)
	{
		lock.lock();
		try
		{
			return list.removeAll(c);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c)
	{
		lock.lock();
		try
		{
			return list.retainAll(c);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#toArray()
	 */
	public Object[] toArray()
	{
		lock.lock();
		try
		{
			return list.toArray();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#toArray(T[])
	 */
	public <T> T[] toArray(T[] array)
	{
		lock.lock();
		try
		{
			return list.toArray(array);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		lock.lock();
		try
		{
			return list.equals(obj);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	/**
	 * Adds the all.
	 * 
	 * @param index
	 *            the index
	 * @param values
	 *            the values
	 * @return true, if successful
	 */
	public boolean addAll(int index, Collection<? extends E> values)
	{
		Iterator<? extends E> i = values.iterator();
		while (i.hasNext())
		{
			add(index, i.next());
		}
		return values.size() != 0;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#get(int)
	 */
	public E get(int index)
	{
		lock.lock();
		try
		{
			return list.get(index);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	/**
	 * Sets the.
	 * 
	 * @param index
	 *            the index
	 * @param element
	 *            the element
	 * @return the e
	 */
	public E set(int index, E element)
	{
		lock.lock();
		try
		{
			return list.set(index, element);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	/**
	 * Adds the.
	 * 
	 * @param index
	 *            the index
	 * @param element
	 *            the element
	 */
	public void add(int index, E element)
	{
		final int size = list.size();
		lock.lock();
		try
		{
			if (isFixed())
			{
				if (capacity != -1 && size == capacity)
				{
					final E e = list.removeLast();
					notifyListeners(e);
				}
			}
			list.add(index, element);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * Notifies the {@link FIFOQueueItemRemovedListener} listeners with the item
	 * removed.
	 * 
	 * @param e
	 *            the e
	 */
	private void notifyListeners(final E e)
	{
		for (final BufferOverflowListener<E> listener : fifoQueueListeners)
		{
			listener.itemRemoved(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#remove(int)
	 */
	public E remove(int index)
	{
		lock.lock();
		try
		{
			return list.remove(index);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object value)
	{
		lock.lock();
		try
		{
			return list.indexOf(value);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object value)
	{
		lock.lock();
		try
		{
			return list.lastIndexOf(value);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator<E> listIterator(int index)
	{
		lock.lock();
		try
		{
			return list.listIterator(index);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#subList(int, int)
	 */
	public List<E> subList(int fromIndex, int toIndex)
	{
		lock.lock();
		try
		{
			return list.subList(fromIndex, toIndex);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#size()
	 */
	public int size()
	{
		lock.lock();
		try
		{
			return list.size();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#isEmpty()
	 */
	public boolean isEmpty()
	{
		lock.lock();
		try
		{
			return list.isEmpty();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	public boolean contains(Object o)
	{
		lock.lock();
		try
		{
			return list.contains(o);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#iterator()
	 */
	public Iterator<E> iterator()
	{
		lock.lock();
		try
		{
			return list.iterator();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	public boolean add(E e)
	{
		return addLast(e);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#clear()
	 */
	public void clear()
	{
		lock.lock();
		try
		{
			list.clear();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#listIterator()
	 */
	public ListIterator<E> listIterator()
	{
		lock.lock();
		try
		{
			return list.listIterator();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * Returns the first value of this list.
	 * 
	 * @return this list's first value.
	 */
	public final E getFirst()
	{
		lock.lock();
		try
		{
			return list.getFirst();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * Returns the last value of this list.
	 * 
	 * @return this list's last value.
	 */
	public final E getLast()
	{
		lock.lock();
		try
		{
			return list.getLast();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * Inserts the specified value at the beginning of this list.
	 * 
	 * @param value
	 *            the value to be inserted.
	 */
	public final void addFirst(E value)
	{
		final int size = list.size();
		lock.lock();
		try
		{
			if (isFixed())
			{
				if (capacity != -1 && size == capacity)
				{
					final E e = list.removeLast();
					notifyListeners(e);
				}
			}
			list.addFirst(value);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * Appends the specified value to the end of this list <i>(fast)</i>.
	 *
	 * @param value
	 *            the value to be inserted.
	 * @return true, if successful
	 */
	public boolean addLast(E value)
	{
		final int size = list.size();
		lock.lock();
		try
		{
			if (isFixed())
			{
				if (capacity != -1 && size == capacity)
				{
					final E e = list.removeLast();
					notifyListeners(e);
				}
			}
			list.addLast(value);
			return true;
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * Pushes the specified value at the beginning of this Queue. This is same
	 * as {@link #addFirst(Object)} , but kept for naming conventions.
	 * 
	 * @param value
	 *            the value to be inserted.
	 */
	public final void push(E value)
	{
		addFirst(value);
	}
	
	/**
	 * Pops the element from the Queue. This is same as {@link #removeLast()},
	 * but kept for naming conventions.
	 * 
	 * @return The value to be popped.
	 */
	public E pop()
	{
		return removeLast();
	}
	
	/**
	 * Removes and returns the first value of this list.
	 * 
	 * @return this list's first value before this call.
	 */
	public final E removeFirst()
	{
		lock.lock();
		try
		{
			return list.removeFirst();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * Removes and returns the last value of this list <i>(fast)</i>.
	 * 
	 * @return this list's last value before this call.
	 */
	public final E removeLast()
	{
		lock.lock();
		try
		{
			return list.removeLast();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * Sets the fixed.
	 * 
	 * @param isFixed
	 *            the isFixed to set
	 */
	private void setFixed(boolean isFixed)
	{
		this.isFixed = isFixed;
	}
	
	/**
	 * Checks if is fixed.
	 * 
	 * @return the isFixed
	 */
	public boolean isFixed()
	{
		return isFixed;
	}
	
	/**
	 * Adds the FIFOQueueItemRemovedListener to the list so that it can be
	 * notified with an item that is to be removed.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addFIFOQueueItemRemovedListener(BufferOverflowListener<E> listener)
	{
		lock.lock();
		try
		{
			fifoQueueListeners.add(listener);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * Removes the FIFOQueueItemRemovedListener from the list.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void removeFIFOQueueItemRemovedListener(BufferOverflowListener<E> listener)
	{
		lock.lock();
		try
		{
			fifoQueueListeners.remove(listener);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * Causes the current thread to wait until it is signalled or.
	 * 
	 * @throws InterruptedException
	 *             the interrupted exception {@linkplain Thread#interrupt
	 *             interrupted}.
	 */
	public void await() throws InterruptedException
	{
		lock.lock();
		try
		{
			if (Thread.interrupted())
				throw new InterruptedException();
			condition.await();
		}
		catch (Exception e)
		{
			condition.signal();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * Causes the current thread to wait until it is signalled or specified time
	 * is elapsed.
	 *
	 * @param time
	 *            the time
	 * @param unit
	 *            the unit
	 * @throws InterruptedException
	 *             the interrupted exception {@linkplain Thread#interrupt
	 *             interrupted}.
	 */
	public void await(long time, TimeUnit unit) throws InterruptedException
	{
		lock.lock();
		try
		{
			if (Thread.interrupted())
				throw new InterruptedException();
			condition.await(time, unit);
		}
		catch (Exception e)
		{
			condition.signal();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * Wakes up one waiting thread.
	 * 
	 * <p>
	 * If any threads are waiting on this condition then one is selected for
	 * waking up. That thread must then re-acquire the lock before returning
	 * from {@code await}.
	 */
	public void signal()
	{
		lock.lock();
		try
		{
			condition.signal();
		}
		catch (Exception e)
		{
			
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * Wakes up all waiting threads.
	 * 
	 * <p>
	 * If any threads are waiting on this condition then they are all woke up.
	 * Each thread must re-acquire the lock before it can return from
	 * {@code await}.
	 */
	public void signalAll()
	{
		lock.lock();
		try
		{
			condition.signalAll();
		}
		catch (Exception e)
		{
			
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return this.getClass().getSimpleName() + ": " + list.toString();
	}
}
