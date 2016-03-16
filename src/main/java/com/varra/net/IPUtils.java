/*
 * utils4j - IPUtils.java, Aug 16, 2015 4:16:56 PM
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
import java.util.ArrayList;
import java.util.List;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;

/**
 * An IP based Utility class, for saving the coding time.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 3.0
 * 
 * @see IpSubnetCheck
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class IPUtils
{
	
	/**
	 * Checks if is it in the given range.
	 * 
	 * @param begin
	 *            the begin
	 * @param end
	 *            the end
	 * @param check
	 *            the check
	 * @return true, if is it in range
	 */
	public static boolean isItInRange(InetAddress begin, InetAddress end, InetAddress check)
	{
		return isItInRange(new IpFeilds(begin), new IpFeilds(end), new IpFeilds(check));
	}
	
	/**
	 * Checks if is it in range.
	 *
	 * @param begin
	 *            the begin
	 * @param end
	 *            the end
	 * @param check
	 *            the check
	 * @return true, if is it in range
	 */
	public static boolean isItInRange(IpFeilds begin, IpFeilds end, IpFeilds check)
	{
		final int ipAsInt = getIpAsInt(check);
		return (ipAsInt >= getIpAsInt(begin) && ipAsInt <= getIpAsInt(end));
	}
	
	/**
	 * Gets the IpAddress as 32 bit signed integer.
	 * 
	 * @param host
	 *            the host
	 * @return the ip as int
	 */
	public static int getIpAsInt(InetAddress host)
	{
		return getIpAsInt(new IpFeilds(host));
	}
	
	/**
	 * Gets the IpAddress as 32 bit signed integer.
	 * 
	 * @param feilds
	 *            the feilds
	 * @return the ip as int
	 */
	public static int getIpAsInt(IpFeilds feilds)
	{
		return (feilds.getA() << 24) | (feilds.getB() << 16) | (feilds.getC() << 8) | (feilds.getD());
	}
	
	/**
	 * Gets the IpAddress as long.
	 * 
	 * @param host
	 *            the host
	 * @return the ip as int
	 */
	public static long getIpAsLong(InetAddress host)
	{
		return getIpAsLong(new IpFeilds(host));
	}
	
	/**
	 * Gets the IpAddress as long. TIP: //1*256^0 + 0*256^1 + 168*256^2 +
	 * 192*256^3
	 * 
	 * @param feilds
	 *            the feilds
	 * @return the ip as int
	 */
	public static long getIpAsLong(IpFeilds feilds)
	{
		return ((long) (feilds.getA() * 256 * 256 * 256)) + ((long) (feilds.getB() * 256 * 256)) + ((long) (feilds.getC() * 256)) + feilds.getD();
	}
	
	/**
	 * Gets all the IPs in the given range including the begin and end IP
	 * Addresses.
	 * 
	 * @param begin
	 *            the begin
	 * @param end
	 *            the end
	 * @return the IPs in range
	 */
	public static List<String> getIpsInRange(InetAddress begin, InetAddress end)
	{
		final long beginInt = getIpAsLong(begin);
		final long endInt = getIpAsLong(end);
		final List<String> ips = new ArrayList<String>();
		for (long ip = beginInt; ip <= endInt; ip++)
		{
			ips.add(getLongAsIPString(ip));
		}
		return ips;
	}
	
	/**
	 * Gets the long as ip.
	 * 
	 * @param longIp
	 *            the long ip
	 * @return the int as ip
	 * @throws UnknownHostException
	 *             the unknown host exception
	 */
	public static InetAddress getLongAsIP(long longIp) throws UnknownHostException
	{
		final long a = longIp >> 24;
		final long b = longIp % (256 * 256 * 256) / (256 * 256);
		final long c = longIp % (256 * 256) / 256;
		final long d = longIp % 256;
		return InetAddress.getByName(a + "." + b + "." + c + "." + d);
	}
	
	/**
	 * Gets the long as ip string.
	 * 
	 * @param longIp
	 *            the long ip
	 * @return the long as ip string
	 */
	public static String getLongAsIPString(long longIp)
	{
		final long a = longIp >> 24;
		final long b = longIp % (256 * 256 * 256) / (256 * 256);
		final long c = longIp % (256 * 256) / 256;
		final long d = longIp % 256;
		return a + "." + b + "." + c + "." + d;
	}
}
