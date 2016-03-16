/*
 * utils4j - HealthCheckerMBean.java, Feb 3, 2011 4:00:31 PM
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
package com.varra.jmx.mbean;

/**
 * <p>
 * Describes the management interface exposed by an MBean; that is, the set of
 * attributes and operations which are available for management operations to
 * let rest of the world know about the status of this particular service's
 * health check.
 * <p>
 * This should be implemented by the service, interested in, to expose it's
 * health status.
 * <p>
 * It is preferred to be an immutable service implementation.
 * <p>
 * Provides a method which tells the health status of the service that is being
 * exposed by this MBean.
 * </p>
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
public interface HealthCheckerMBean
{
	
	/**
	 * Checks if this service is alive, and functioning well. Implementation of
	 * this method should be very light weight and synchronized.
	 * 
	 * @return true, if is alive
	 */
	public boolean isAlive();
}
