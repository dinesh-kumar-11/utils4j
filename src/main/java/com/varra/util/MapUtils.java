/*
 * MWatchEventProcessingEngine - MapUtils.java, Mar 23, 2013 1:23:47 PM
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

import static com.varra.props.VarraProperties.getPropertyAsGeneric;
import static com.varra.util.ObjectUtils.isNotNull;
import static com.varra.util.WrapperUtils.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;

/**
 * TODO Description go here.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V. Reddy</a>
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public final class MapUtils
{
	
	/**
	 * Gets the key based on value.
	 * 
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param map
	 *            the map
	 * @param value
	 *            the value
	 * @return the key
	 */
	public static <K, V> K getKey(Map<K, V> map, V value)
	{
		if (ObjectUtils.isNotNull(map) && ObjectUtils.isNotNull(value))
		{
			for (Entry<K, V> entry : map.entrySet())
			{
				if (ObjectUtils.isNotNull(entry.getValue()) && entry.getValue().equals(value))
				{
					return entry.getKey();
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets the property as map based on the delimiters provided, and uses {@link LinkedHashMap}.
	 * 
	 * @param property
	 *            the property
	 * @param entryDelimiter
	 *            the entry delimiter
	 * @param keyValueDelimiter
	 *            the key value delimiter
	 * @return the property as map
	 */
	public static Map<String, String> toMap(String property, String entryDelimiter, String keyValueDelimiter)
	{
		return toMap(property, entryDelimiter, keyValueDelimiter, LinkedHashMap.class, String.class, String.class); 
	}
	
	/**
	 * Gets the property as map based on the delimiters provided, and uses your
	 * specified class.
	 * 
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param property
	 *            the property
	 * @param entryDelimiter
	 *            the entry delimiter
	 * @param keyValueDelimiter
	 *            the key value delimiter
	 * @param klass
	 *            the klass
	 * @param keyClass
	 *            the key class
	 * @param valueClass
	 *            the value class
	 * @return the property as map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <K, V> Map<K, V> toMap(String property, String entryDelimiter, String keyValueDelimiter, Class<? extends Map> klass, Class<K> keyClass, Class<V> valueClass)
	{
		try
		{
			final Map<K, V> map = klass.newInstance();
			try
			{
				final String value = getPropertyAsGeneric(property);
				if (isNotNull(value) && value.trim().length() > 0)
				{
					for (String keyNvalue : value.split(entryDelimiter))
					{
						final String[] keyvalues = keyNvalue.split(keyValueDelimiter);
						map.put(to(keyvalues[0].trim(), keyClass), to(keyvalues[1].trim(), valueClass));
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return map;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
