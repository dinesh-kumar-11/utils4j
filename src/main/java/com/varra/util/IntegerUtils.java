/*
 * utils4j - InegerUtils.java, Dec 19, 2012 7:29:31 PM
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

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;

/**
 * <p>Operations on {@link java.lang.Integer} that are
 * <code>null</code> safe.</p>
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @since 3.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class IntegerUtils
{
	
	/**
	 * Checks if the given integer is holding a positive value i.e grater than
	 * zero.
	 * 
	 * @param integer
	 *            the integer
	 * @return true, if is positive
	 */
	public static boolean isPositive(Integer integer)
	{
		return ObjectUtils.isNotNull(integer) && integer > 0;
	}
	
	/**
	 * Checks if the given integer is holding a Negative value i.e less than
	 * zero.
	 * 
	 * @param integer
	 *            the integer
	 * @return true, if is Negative
	 */
	public static boolean isNegative(Integer integer)
	{
		return ObjectUtils.isNotNull(integer) && integer < 0;
	}
	
	/**
	 * Checks if the given integer is holding a ZERO i.e 0.
	 * 
	 * @param integer
	 *            the integer
	 * @return true, if is zero(0)
	 */
	public static boolean isZero(Integer integer)
	{
		return ObjectUtils.isNotNull(integer) && integer == 0;
	}
	
	/**
	 * Checks if the given first integer is less than the second integer value
	 * i.e first &lt; second.
	 * 
	 * @param first
	 *            the first
	 * @param second
	 *            the second
	 * @return true, if is zero(0)
	 */
	public static boolean isFirstLessThanSecond(Integer first, Integer second)
	{
		return ObjectUtils.isNotNull(first) && ObjectUtils.isNotNull(second) && first < second;
	}
	
	/**
	 * Checks if the given first integer is greater than the second integer value
	 * i.e first &gt; second.
	 * 
	 * @param first
	 *            the first
	 * @param second
	 *            the second
	 * @return true, if is zero(0)
	 */
	public static boolean isFirstGraterThanSecond(Integer first, Integer second)
	{
		return ObjectUtils.isNotNull(first) && ObjectUtils.isNotNull(second) && first > second;
	}
	
	/**
	 * Checks if the given values are EQUAL i.e first <code>==</code> second.
	 * 
	 * @param first
	 *            the first
	 * @param second
	 *            the second
	 * @return true, if is zero(0)
	 */
	public static boolean areBothEqual(Integer first, Integer second)
	{
		return ObjectUtils.isNotNull(first) && ObjectUtils.isNotNull(second) && first == second;
	}
}
