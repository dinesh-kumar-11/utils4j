/*
 * utils4j - Shutdownable.java, Nov 16, 2012 3:15:28 PM
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
 * A Shutdownable is an service or an object that can be shutdown. The shutdown
 * method is invoked to release resources that the object is holding <code>(</code>such as
 * sockets<code>)</code>.
 * 
 * @see ShutdownMode for defining the Shutdown mode.
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 3.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface Shutdownable
{
	
	/**
	 * Shutdowns this entity properly by performing all the necessary steps to
	 * make sure that all the used resources are released properly based on the
	 * {@link ShutdownMode} specified.
	 * 
	 * @param mode
	 *            the mode
	 */
	void shutdown(ShutdownMode mode);
}