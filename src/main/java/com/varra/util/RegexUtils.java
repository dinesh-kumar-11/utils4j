/*
 * utils4j - RegexParser.java, Sep 17, 2013 2:53:44 PM
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

import static com.varra.props.Constants.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.varra.util.StringUtils.*;

/**
 * Regular expression utils.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
public class RegexUtils
{
	
	/**
	 * Parses the provided string with the REGEX given and returns the groups as list {@link List}.
	 * 
	 * @param input
	 *            the input
	 * @param regex
	 *            the regex
	 * @return the list {@link List}.
	 */
	public static List<String> parse(String input, String regex)
	{
		final List<String> groups = new LinkedList<String>();
		final Pattern p = Pattern.compile(regex);
		final Matcher m = p.matcher(input.replaceAll(NEWLINE, SPACE).replaceAll(RETURN, SPACE));
		if (m.matches())
		{
			for (int i = 1; i <= m.groupCount(); i++)
			{
				groups.add(m.group(i));
			}
		}
		
		return groups;
	}
	
	/**
	 * Parses the provided string with the REGEX given and returns the first occurrence.
	 * 
	 * @param input
	 *            the input
	 * @param regex
	 *            the regex
	 * @return the first one.
	 */
	public static String parseNgetFirst(String input, String regex)
	{
		final Pattern p = Pattern.compile(regex);
		final Matcher m = p.matcher(input.replaceAll(NEWLINE, SPACE).replaceAll(RETURN, SPACE));
		if (m.matches())
		{
			return m.groupCount() > 0 ? m.group(1) : null; 
		}
		return null;
	}
	
	/**
	 * Resolves the property to the environment variables value.
	 * 
	 * @param property
	 *            the property
	 * @return the string
	 */
	public static String resolve2Env(String property)
	{
		final Map<String, String> envMap = System.getenv();
		for (Entry<String, String> entry : envMap.entrySet())
		{
			final String key = entry.getKey();
			final String value = entry.getValue();
			property = property.replaceAll(FIRST_PART + key + SECOND_PART, value);
		}
		return property;
	}
}
