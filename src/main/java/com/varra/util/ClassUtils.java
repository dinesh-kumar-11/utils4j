/*
 * utils4j - ClassUtils.java, Aug 3, 2012 12:53:27 PM
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.varra.log.Logger;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;


/**
 * Class related utilities. 
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 *
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class ClassUtils
{
	
	/**
	 * Gets the valid field name.
	 * 
	 * @param className
	 *            the class name
	 * @param primaryKey
	 *            the primary key
	 * @return true, if successful
	 */
	public static String getValidFieldName(Class<?> className, String primaryKey)
	{
		final Field[] declaredFields = className.getDeclaredFields();
		for (Field field : declaredFields)
		{
			if (field.getName().equalsIgnoreCase(primaryKey))
			{
				return field.getName();
			}
		}
		return null;
	}
	
	/**
	 * Gets the field value.
	 * 
	 * @param className
	 *            the class name
	 * @param object
	 *            the object
	 * @param methodName
	 *            the primary key
	 * @return the field value
	 */
	@Deprecated
	public static String getFieldValue(Class<?> className, Object object, String methodName)
	{
		final Method[] methods = className.getMethods();
		for (int i = 0; i < methods.length; i++)
		{
			final Method method = methods[i];
			final String name = method.getName();
			if (name.toUpperCase().endsWith(methodName.toUpperCase()))
			{
				try
				{
					return method.invoke(object).toString();
				}
				catch (Exception e)
				{
					Logger.getLogger(ClassUtils.class).error("Error while getting the field value of: " + methodName, e);
				}
			}
		}
		return null;
	}
}

