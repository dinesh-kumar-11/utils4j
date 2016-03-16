/*
 * utils4j - TimerTaskReceiver.java, Jun 12, 2011 12:47:08 PM
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
 * An convenient interface which receives the {@link EnhancedTimerTask} events
 * to update the processing Engine.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public interface TimerTaskReceiver
{
	
	/**
	 * Updates the processing Engine with the {@link EnhancedTimerTask}, and it
	 * should be a light weight implementation and synchronized.
	 * 
	 * @param timerTask
	 *            the timer task
	 */
	public void onTimerTask(final EnhancedTimerTask timerTask);
}
