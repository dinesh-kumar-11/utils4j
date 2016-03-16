/*
 * utils4j - TimerTaskListener.java, Sep 15, 2011 3:00:31 PM
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
import com.varra.util.EnhancedTimerTask;
import com.varra.util.GlobalThread;

/**
 * An convenient interface which listens to the processing Engine for the
 * completed/canceled/Expired {@link EnhancedTimerTask} events from the
 * processing Engine.
 * 
 * It starts receiving the events once it registers with the {@link GlobalThread}.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public interface TimerTaskListener
{
	
	/**
	 * Omits the {@link EnhancedTimerTask} when it gets canceled.
	 * 
	 * @param task
	 *            the task
	 */
	void omitOnCancel(final EnhancedTimerTask task);
	
	/**
	 * Omits the {@link EnhancedTimerTask} when it gets expired.
	 * 
	 * @param task
	 *            the task
	 */
	void omitOnExpiry(final EnhancedTimerTask task);
	
	/**
	 * Omits the {@link EnhancedTimerTask} when it gets Finished.
	 * 
	 * @param task
	 *            the task
	 */
	void omitOnFinish(final EnhancedTimerTask task);
}
