/*
 * utils4j - IpSubnetCheck.java, Nov 16, 2012 11:34:50 AM
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
import java.net.UnknownHostException;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;

/**
 * An IP Utility class to check whether the Given IP is in the range!.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V. Reddy</a>
 * @version 3.0
 *
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class IpSubnetCheck
{
	
	/** The begin feilds. */
	private final IpFeilds beginFeilds;
	
	/** The end feilds. */
	private final IpFeilds endFeilds;
	
	/**
	 * Instantiates a new iP subnet check.
	 * 
	 * @param begin
	 *            the begin
	 * @param end
	 *            the end
	 */
	public IpSubnetCheck(InetAddress begin, InetAddress end)
	{
		beginFeilds = new IpFeilds(begin);
		endFeilds = new IpFeilds(end);
	}
	
	/**
	 * Checks if is it in range.
	 * 
	 * @param check
	 *            the check
	 * @return true, if is it in range
	 */
	public boolean isItInRange(InetAddress check)
	{
		return IPUtils.isItInRange(beginFeilds, endFeilds, new IpFeilds(check));
	}
	
	/**
	 * Check.
	 * 
	 * @param cFeilds
	 *            the c feilds
	 * @return true, if successful
	 */
	@SuppressWarnings("unused")
	private boolean check(IpFeilds cFeilds)
	{
		boolean inRange = Boolean.FALSE;
		if (cFeilds.getA() >= beginFeilds.getA() && cFeilds.getA() <= endFeilds.getA())
		{
			if (cFeilds.getA() != beginFeilds.getA() && cFeilds.getA() != endFeilds.getA())
			{
				inRange = Boolean.TRUE;
			}
			else if (cFeilds.getB() >= beginFeilds.getB() && cFeilds.getB() <= endFeilds.getB())
			{
				if (cFeilds.getB() != beginFeilds.getB() && cFeilds.getB() != endFeilds.getB())
				{
					inRange = Boolean.TRUE;
				}
				else if (cFeilds.getC() >= beginFeilds.getC() && cFeilds.getC() <= endFeilds.getC())
				{
					if (cFeilds.getC() != beginFeilds.getC() && cFeilds.getC() != endFeilds.getC())
					{
						inRange = Boolean.TRUE;
					}
					else
					{
						inRange = cFeilds.getD() >= beginFeilds.getD() && cFeilds.getD() <= endFeilds.getD();
					}
				}
			}
		}
		return inRange;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("IpSubnetCheck [beginFeilds=");
		builder.append(beginFeilds);
		builder.append(", endFeilds=");
		builder.append(endFeilds);
		builder.append("]");
		return builder.toString();
	}
	
	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws UnknownHostException
	 *             the unknown host exception
	 */
	public static void main(String[] args) throws UnknownHostException
	{
		final InetAddress begin = InetAddress.getByName("192.168.1.45");
		final InetAddress end = InetAddress.getByName("195.179.2.145");
		final InetAddress check = InetAddress.getByName("192.168.0.1");
		final IpSubnetCheck ipSubnetCheck = new IpSubnetCheck(begin, end);
		System.out.println(ipSubnetCheck.isItInRange(check));
		
		long x = Integer.MAX_VALUE + ((-1062731775) * -1);
		System.out.println(x);
		
		int ipAsInt = IPUtils.getIpAsInt(check);
		System.out.println(ipAsInt);
		long l = 3232235521l;
		long a =  l>>24;
		long b = l%(256*256*256)/(256*256);
		long c = l%(256*256)/256;
		long d = l%256;
		System.out.println(a+"."+b+"."+c+"."+d);
		System.out.println(IPUtils.getIpAsLong(check));
		System.out.println(1*256*256);
		System.out.println(1<<16);
		//1*256^0 + 0*256^1 + 168*256^2 + 192*256^3 = 3232235521 --> 192.168.0.1
		//System.out.println(IPUtils.getIntAsIP(ipAsInt));
	}
}
