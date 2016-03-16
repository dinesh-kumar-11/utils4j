/*
 * utils4j - WrapperUtils.java, Aug 16, 2015 5:22:15 PM
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

import static com.varra.util.ObjectUtils.*;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;

/**
 * Utility methods required for wrapper classes.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 1.0
 * @since 3.0
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
@SuppressWarnings("unchecked")
public final class WrapperUtils
{
	
	/**
	 * Converts the value to the specified object type provided and returns,
	 * else returns the default value provided.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param value
	 *            the value
	 * @param defValue
	 *            the default value
	 * @return the property
	 */
	public static <T> T to(final Object value, final T defValue)
	{
		try
		{
			if (isNotNull(value) && isNotNull(defValue))
			{
				if (defValue instanceof Boolean)
				{
					return (T) (isNull(value) ? defValue : Boolean.parseBoolean(value.toString()));
				}
				else if (defValue instanceof Byte)
				{
					return (T) (isNull(value) ? defValue : Byte.parseByte(value.toString()));
				}
				else if (defValue instanceof Short)
				{
					return (T) (isNull(value) ? defValue : Short.parseShort(value.toString()));
				}
				else if (defValue instanceof Integer)
				{
					return (T) (isNull(value) ? defValue : Integer.parseInt(value.toString()));
				}
				else if (defValue instanceof Long)
				{
					return (T) (isNull(value) ? defValue : Long.parseLong(value.toString()));
				}
				else if (defValue instanceof Float)
				{
					return (T) (isNull(value) ? defValue : Float.parseFloat(value.toString()));
				}
				else if (defValue instanceof Double)
				{
					return (T) (isNull(value) ? defValue : Double.parseDouble(value.toString()));
				}
				else if (defValue instanceof String)
				{
					return (T) (isNull(value) ? defValue : value.toString());
				}
			}
		}
		catch (Exception e)
		{
			// What should I do .. !!!
		}
		return defValue;
	}
	
	/**
	 * Converts the value to the specified object type provided and returns,
	 * else returns the default value provided.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param value
	 *            the value
	 * @param defValue
	 *            the default value
	 * @return the property
	 */
	public static <T> T to(final String value, final T defValue)
	{
		try
		{
			if (isNotNull(value) && isNotNull(defValue))
			{
				if (defValue instanceof Boolean)
				{
					return (T) (isNull(value) ? defValue : Boolean.parseBoolean(value.toString()));
				}
				else if (defValue instanceof Byte)
				{
					return (T) (isNull(value) ? defValue : Byte.parseByte(value.toString()));
				}
				else if (defValue instanceof Short)
				{
					return (T) (isNull(value) ? defValue : Short.parseShort(value.toString()));
				}
				else if (defValue instanceof Integer)
				{
					return (T) (isNull(value) ? defValue : Integer.parseInt(value.toString()));
				}
				else if (defValue instanceof Long)
				{
					return (T) (isNull(value) ? defValue : Long.parseLong(value.toString()));
				}
				else if (defValue instanceof Float)
				{
					return (T) (isNull(value) ? defValue : Float.parseFloat(value.toString()));
				}
				else if (defValue instanceof Double)
				{
					return (T) (isNull(value) ? defValue : Double.parseDouble(value.toString()));
				}
				else if (defValue instanceof String)
				{
					return (T) (isNull(value) ? defValue : value.toString());
				}
			}
		}
		catch (Exception e)
		{
			// What should I do .. !!!
		}
		return defValue;
	}
	
	/**
	 * Converts the value to the specified object type provided and returns,
	 * else returns the default value provided.
	 *
	 * @param <T>
	 *            the generic type
	 * @param value
	 *            the value
	 * @param klass
	 *            the klass
	 * @return the property
	 */
	public static <T> T to(final String value, Class<T> klass)
	{
		try
		{
			Object newValue = value;
			if (klass.isPrimitive())
			{
				klass = getClassFromPrimitive(klass);
			}
			if (isNotNull(newValue))
			{
				if (Boolean.class.isAssignableFrom(klass))
				{
					return (T) (newValue = Boolean.parseBoolean(value.toString()));
				}
				else if (Byte.class.isAssignableFrom(klass))
				{
					return (T) (newValue = Byte.parseByte(value.toString()));
				}
				else if (Short.class.isAssignableFrom(klass))
				{
					return (T) (newValue = Short.parseShort(value.toString()));
				}
				else if (Integer.class.isAssignableFrom(klass))
				{
					return (T) (newValue = Integer.parseInt(value.toString()));
				}
				else if (Long.class.isAssignableFrom(klass))
				{
					return (T) (newValue = Long.parseLong(value.toString()));
				}
				else if (Float.class.isAssignableFrom(klass))
				{
					return (T) (newValue = Float.parseFloat(value.toString()));
				}
				else if (Double.class.isAssignableFrom(klass))
				{
					return (T) (newValue = Double.parseDouble(value.toString()));
				}
				else if (String.class.isAssignableFrom(klass))
				{
					return (T) (newValue = value.toString());
				}
			}
		}
		catch (Exception e)
		{
			// What should I do .. !!!
		}
		return null;
	}
	
	/**
	 * Gets the class of the specified type.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param type
	 *            the type
	 * @return the class
	 */
	public static <T> Class<T> getClass(T type)
	{
		return (Class<T>) type.getClass();
	}
	
	/**
	 * Gets the class of the specified type.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param type
	 *            the type
	 * @return the class
	 */
	public static <T> Class<T> getClassFromPrimitive(Class<T> type)
	{
		if (type.getName().equalsIgnoreCase("boolean"))
		{
			return (Class<T>) Boolean.class;
		}
		else if (type.getName().equalsIgnoreCase("int"))
		{
			return (Class<T>) Integer.class;
		}
		else if (type.getName().equalsIgnoreCase("long"))
		{
			return (Class<T>) Long.class;
		}
		else if (type.getName().equalsIgnoreCase("short"))
		{
			return (Class<T>) Short.class;
		}
		else if (type.getName().equalsIgnoreCase("float"))
		{
			return (Class<T>) Float.class;
		}
		else if (type.getName().equalsIgnoreCase("double"))
		{
			return (Class<T>) Double.class;
		}
		else if (type.getName().equalsIgnoreCase("string"))
		{
			return (Class<T>) String.class;
		}
		else if (type.getName().equalsIgnoreCase("char"))
		{
			return (Class<T>) Character.class;
		}
		else if (type.getName().equalsIgnoreCase("byte"))
		{
			return (Class<T>) Byte.class;
		}
		return type;
	}
}
