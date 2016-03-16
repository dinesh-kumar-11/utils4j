/*
 * utils4j - XMLUtility.java, Feb 9, 2011 10:27:39 AM
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
package com.varra.jmx.util;

import com.varra.util.RapidFastMap;

/**
 * TODO Description go here.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
public class XMLUtility
{
	
	/**
	 * Gets the jVM details html.
	 * 
	 * @param header
	 *            the header
	 * @param properties
	 *            the properties
	 * @return the jVM details html
	 */
	public static String getJVMDetailsHTML(final String header, final RapidFastMap<String, String> properties)
	{
		final StringBuffer buffer = new StringBuffer();
		buffer.append(getHeader(header, 3));
		if (properties != null)
		{
			for (String key : properties.keySet())
			{
				buffer.append(getJVMPropRowEntry(key, properties.get(key)));
			}
		}
		return getTable(buffer.toString());
	}
	
	/**
	 * Gets the rapid app details html.
	 * 
	 * @param header
	 *            the header
	 * @param apps
	 *            the apps
	 * @return the rapid app details html
	 */
	public static String getRapidAppDetailsHTML(final String header, final RapidFastMap<String, RapidFastMap<String, String>> apps)
	{
		final StringBuffer buffer = new StringBuffer();
		buffer.append(getHeader(header, 4));
		if (apps != null)
		{
			for (String app : apps.keySet())
			{
				final RapidFastMap<String, String> props = (RapidFastMap<String, String>) apps.get(app);
				buffer.append(getAppEntry(app, props.size()));
				for (String key : props.keySet())
				{
					buffer.append(getAppPropRowEntry(key, props.get(key)));
				}
				buffer.append(getBreakForAppEntry());
			}
		}
		return getTable(buffer.toString());
	}
	
	/**
	 * Gets the table.
	 * 
	 * @param content
	 *            the content
	 * @return the table
	 */
	protected static String getTable(final String content)
	{
		final String bgColor = "#E9F2EE";
		return "<table border=\"2\" bgcolor=\"" + bgColor + "\" datapagesize=\"auto\" cellpadding=\"0\" cellspacing=\"0\">"+content+"</table>";
	}
	
	/**
	 * Gets the table.
	 * 
	 * @param content
	 *            the content
	 * @return the table
	 */
	protected static String getTable(final String bgColor, final String content)
	{
		//return "<table border=\"1\" bgcolor=\"" + bgColor + "\" datapagesize=\"auto\" cellpadding=\"0\" cellspacing=\"0\">"+content+"</table>";
		return "<table border=\"1\" bgcolor=\"" + bgColor + "\" datapagesize=\"auto\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-size: 14px;\" style=\"font-family: arial,helvetica,sans-serif;\">"+content+"</table>";
	}
	
	/**
	 * Gets the header.
	 * 
	 * @param header
	 *            the header
	 * @return the header
	 */
	protected static String getHeader(final String header, final int noOfCols)
	{
		return "<tr><th colspan=\""+noOfCols+"\">" + header + "</th></tr>";
	}
	
	/**
	 * Gets the app row entry.
	 * 
	 * @param appName
	 *            the app name
	 * @param noOfRows
	 *            the no of rows
	 * @return the app row entry
	 */
	protected static String getAppEntry(final String appName, final int noOfRows)
	{
		return "<tr><th width=\"20%\" height=\"50%\" rowspan=" + (noOfRows+2) + ">" + appName + "</th></tr>";
	}
	
	/**
	 * Gets the app prop row entry.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the app prop row entry
	 */
	protected static String getAppPropRowEntry(final String name, final String value)
	{
		return "<tr><th colspan=\"2\" align=\"left\">" + name + "</th><td>" + value + "</td></tr>";
	}
	
	/**
	 * Gets the jVM prop row entry.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the jVM prop row entry
	 */
	protected static String getJVMPropRowEntry(final String name, final String value)
	{
		return "<tr><th colspan=\"2\" width=\"20%\" align=\"left\">" + name + "</th><td>" + value + "</td></tr>";
	}
	
	/**
	 * Gets the break for app entry.
	 * 
	 * @return the break for app entry
	 */
	protected static String getBreakForAppEntry()
	{
		return "<!-- To give a break between each application --><tr><th width=\"20%\" height=\"50%\" rowspan=\"1\"/></tr><tr><th width=\"20%\" height=\"50%\" rowspan=\"1\"/></tr>";
	}
}
