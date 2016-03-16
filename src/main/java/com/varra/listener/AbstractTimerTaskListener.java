/*
 * utils4j - AbstractTimerTaskListener.java, Sep 15, 2011 3:02:24 PM
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
import com.varra.util.SafeThread;

/**
 * An convenient abstract class for listening the {@link EnhancedTimerTask}
 * tasks ejects from {@link SafeThread}.
 * 
 * Has to be implemented by sub classes for proper handling of {@link EnhancedTimerTask} tasks.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public abstract class AbstractTimerTaskListener implements TimerTaskListener
{
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.varra.listener.TimerTaskListener#omitOnCancel(com.varra.util.EnhancedTimerTask
	 * )
	 */
	public void omitOnCancel(EnhancedTimerTask task)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.varra.listener.TimerTaskListener#omitOnExpiry(com.varra.util.EnhancedTimerTask
	 * )
	 */
	public void omitOnExpiry(EnhancedTimerTask task)
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.varra.listener.TimerTaskListener#omitOnFinish(com.varra.util.EnhancedTimerTask
	 * )
	 */
	public void omitOnFinish(EnhancedTimerTask task)
	{
		
	}
}
