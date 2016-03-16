/*
 * utils4j - BufferOverflowListener.java, Oct 31, 2012 11:38:55 AM
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

/**
 * This is a convenient interface to get the removed items from the backing {@link FIFOQueue}.
 * 
 * @param <E>
 *            the element type.
 * @author Rajakrishna V. Reddy
 * @version 1.0
 */
public interface BufferOverflowListener<E>
{
	
	/**
	 * This is the method to notify the user with the removed item. It is an
	 * synchronous call, so this should not be a heavy loaded one.<br>
	 * must have a simple implementation to take and utilize the removed item.
	 * 
	 * @param item
	 *            the item
	 */
	public void itemRemoved(E item);
}
