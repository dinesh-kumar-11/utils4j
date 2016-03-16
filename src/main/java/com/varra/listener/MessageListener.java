/*
 * utils4j - MessageListener.java, Nov 18, 2012 11:59:09 AM
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
 * An Asynchronized loosely coupled interface to provide the Messages to the
 * interested clients.
 * 
 * @param <T>
 *            the generic type
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 3.0
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public interface MessageListener<T>
{
	
	/**
	 * Notifies the message as it happens. <b>Very light weight code should go
	 * here, because the same method is used to notify the other listeners and
	 * it is a synchronized call.</b><br>
	 * So Receiver should not delay time like by processing the message ..etc.
	 * 
	 * @param message
	 *            the message
	 */
	void onMessage(T message);
}
