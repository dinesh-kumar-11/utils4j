/*
 * utils4j - LisetenerManager.java, Nov 18, 2012 12:53:22 PM
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
package com.varra.listener;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;

/**
 * An utility interface which will be used for adding/removing the listeners.
 * Very helpful to maintain a protocol between the modules.
 * 
 * @param <T>
 *            the generic type of the listener
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 3.0
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public interface ListenerManager<T>
{
	
	/**
	 * Adds the listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	void addListener(T listener);
	
	/**
	 * Removes the listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	void removeListener(T listener);
}
