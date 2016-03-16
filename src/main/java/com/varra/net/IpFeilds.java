/*
 * utils4j - IpFeilds.java, Nov 16, 2012 12:21:27 PM
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
package com.varra.net;

import java.net.InetAddress;

/**
 * Holds the actual IP details as feilds a,b,c,d from <b> (a.b.c.d)</b>.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 3.0
 * 
 */
public final class IpFeilds
{
	
	/** The a. */
	private final int a;
	
	/** The b. */
	private final int b;
	
	/** The c. */
	private final int c;
	
	/** The d. */
	private final int d;
	
	/** The address. */
	private final InetAddress address;
	
	/**
	 * Instantiates a new iP feilds.
	 * 
	 * @param address
	 *            the address
	 */
	public IpFeilds(InetAddress address)
	{
		this.address = address;
		final String[] feilds = address.getHostAddress().split("\\.");
		a = Integer.parseInt(feilds[0]);
		b = Integer.parseInt(feilds[1]);
		c = Integer.parseInt(feilds[2]);
		d = Integer.parseInt(feilds[3]);
	}
	
	/**
	 * Gets the a (Indicates the first part of ip i.e <b>a.b.c.d</b> .) as
	 * integer.
	 * 
	 * @return the a
	 */
	public int getA()
	{
		return a;
	}
	
	/**
	 * Gets the b (Indicates the second part of ip i.e <b>a.b.c.d</b>.) as
	 * integer..
	 * 
	 * @return the b
	 */
	public int getB()
	{
		return b;
	}
	
	/**
	 * Gets the c (Indicates the third part of ip i.e <b>a.b.c.d</b>.) as
	 * integer..
	 * 
	 * @return the c
	 */
	public int getC()
	{
		return c;
	}
	
	/**
	 * Gets the d (Indicates the fourth part of ip i.e <b>a.b.c.d</b>.) as
	 * integer.
	 * 
	 * @return the d
	 */
	public int getD()
	{
		return d;
	}
	
	/**
	 * Gets the address.
	 * 
	 * @return the address
	 */
	public InetAddress getAddress()
	{
		return address;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("IpFeilds [a=");
		builder.append(a);
		builder.append(", b=");
		builder.append(b);
		builder.append(", c=");
		builder.append(c);
		builder.append(", d=");
		builder.append(d);
		builder.append(", address=");
		builder.append(address);
		builder.append("]");
		return builder.toString();
	}
}