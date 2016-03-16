/*
 * utils4j - ArrayUtils.java, Oct 14, 2013 10:31:34 AM
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
import static com.varra.util.IntegerUtils.*;

import java.util.HashSet;
import java.util.Set;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;

/**
 * Array related general day to day used operations.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
@SuppressWarnings("unchecked")
public final class ArrayUtils
{
	
	/**
	 * The index value when an element is not found in a list or array:
	 * <code>-1</code>. This value is returned by methods in this class and can
	 * also be used in comparisons with values returned by various method from
	 * {@link java.util.List}.
	 */
	public static final int INDEX_NOT_FOUND = -1;
	
	/**
	 * Instantiates a new array utils.
	 */
	private ArrayUtils()
	{
		
	}
	
	/**
	 * Checks whether the given object to find is in the array.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param objectToFind
	 *            the object to find
	 * @param array
	 *            the array
	 * @return true, if successful
	 */
	public static <T> boolean contains(T objectToFind, T[] array)
	{
		return indexOf(objectToFind, array) >= 0 ? true : false;
	}
	
	/**
	 * <p>
	 * Finds the index of the given object in the array.
	 * </p>
	 * 
	 * <p>
	 * This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a
	 * <code>null</code> input array.
	 * </p>
	 * 
	 * @param <T>
	 *            the generic type
	 * @param objectToFind
	 *            the object to find, may be <code>null</code>
	 * @param array
	 *            the array to search through for the object, may be
	 *            <code>null</code>
	 * @return the index of the object within the array,
	 *         {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or
	 *         <code>null</code> array input
	 */
	public static <T> int indexOf(T objectToFind, T[] array)
	{
		return indexOf(objectToFind, 0, array);
	}
	
	/**
	 * <p>
	 * Finds the index of the given object in the array starting at the given
	 * index.
	 * </p>
	 * 
	 * <p>
	 * This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a
	 * <code>null</code> input array.
	 * </p>
	 * 
	 * <p>
	 * A negative startIndex is treated as zero. A startIndex larger than the
	 * array length will return {@link #INDEX_NOT_FOUND} (<code>-1</code>).
	 * </p>
	 * 
	 * @param <T>
	 *            the generic type
	 * @param objectToFind
	 *            the object to find, may be <code>null</code>
	 * @param startIndex
	 *            the index to start searching at
	 * @param array
	 *            the array to search through for the object, may be
	 *            <code>null</code>
	 * @return the index of the object within the array starting at the index,
	 *         {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or
	 *         <code>null</code> array input
	 */
	public static <T> int indexOf(T objectToFind, int startIndex, T[] array)
	{
		if (isNotNull(array) && isPositive(ArrayUtils.<T, Integer> length(array)))
		{
			startIndex = isPositive(startIndex) ? startIndex : 0;
			for (int i = startIndex; i < array.length; i++)
			{
				if (equalsEx(array[i], objectToFind))
				{
					return i;
				}
			}
		}
		return INDEX_NOT_FOUND;
	}
	
	/**
	 * Is used to get the array size in {@link Integer} or {@link Long} or.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param <SizeType>
	 *            the generic type
	 * @param array
	 *            the array
	 * @return the size type {@link Double} based on the request.
	 */
	public static <T, SizeType> SizeType length(T... array)
	{
		final Object size = array.length;
		return (SizeType) size;
	}
	
	/**
	 * Checks whether the given array is empty.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param array
	 *            the a
	 * @return true, if successful
	 * @see #isEmpty(Object...)
	 */
	@Deprecated
	public static <T> boolean empty(T... array)
	{
		return isNull(array) || isZero(ArrayUtils.<T, Integer> length(array));
	}
	
	/**
	 * Checks whether the given array is empty.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param array
	 *            the a
	 * @return true, if successful
	 */
	public static <T> boolean isEmpty(T... array)
	{
		return isNull(array) || isZero(ArrayUtils.<T, Integer> length(array));
	}
	
	/**
	 * Converts the array to set of same type.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param indexes
	 *            the indexes
	 * @return the set
	 */
	public static <T> Set<T> toSet(T[] indexes)
	{
		final Set<T> set = new HashSet<T>();
		for (T t : indexes)
		{
			if (isNotNull(t))
			{
				set.add(t);
			}
		}
		return set;
	}
}
