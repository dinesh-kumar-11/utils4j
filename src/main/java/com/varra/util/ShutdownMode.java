/*
 * utils4j - ShutdownMode.java, Nov 15, 2012 12:36:19 PM
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
 * /** An {@link Enum} that lists shutdown modes. Any Service or Object instance
 * can be shutdown either gracefully or forcibly.
 * <p>
 * When a Service or Object instance is shutdown gracefully, it can perform some
 * prerequisite operations like notifying another nodes or releasing the
 * connections properly etc...Graceful shutdown may take time.
 * <p>
 * When a Service or Object instance is shutdown forcibly, it will immediately
 * stop responding to requests and closes all the connections abruptly. This
 * mode is little bit faster.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 3.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public enum ShutdownMode
{
	
	/** The Graceful_Shutdown. */
	GRACEFUL_SHUTDOWN(((byte) 0), "Graceful"),

	/** The Forced_ Shutdown. */
	FORCED_SHUTDOWN(((byte) 1), "Forced");
	
	/** The code. */
	private final byte code;
	
	/** The description. */
	private final String description;
	
	/**
	 * Instantiates a new ShutdownMode.
	 * 
	 * @param code
	 *            numeric code for shutdown mode.
	 * @param description
	 *            shutdown mode description.
	 */
	private ShutdownMode(final byte code, final String description)
	{
		this.code = code;
		this.description = description;
	}
	
	/**
	 * Gets the code.
	 * 
	 * @return the code
	 */
	public byte getCode()
	{
		return code;
	}
	
	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append(this.getClass());
		builder.append(" [mode=");
		builder.append(code);
		builder.append(", description=");
		builder.append(description);
		return builder.toString();
	}
}
