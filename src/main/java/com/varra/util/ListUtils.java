/*
 * utils4j - ListUtils.java, Sep 8, 2014 10:47:52 AM
 * 
 * Copyright 2014 Trimble Ltd, Inc. All rights reserved.
 * Trimble proprietary/confidential. Use is subject to license terms.
 */
package com.varra.util;

import static com.varra.props.VarraProperties.getPropertyAsGeneric;
import static com.varra.util.ObjectUtils.isNotNull;
import static com.varra.util.WrapperUtils.to;

import java.util.LinkedList;
import java.util.List;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;
import com.varra.log.Logger;

/**
 * TODO Description go here.
 * 
 * @author <a href="mailto:Rajakrishna_Reddy@Trimble.com">Rajakrishna V.
 *         Reddy</a>
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class ListUtils
{
	
	/** The logger to log the debugging messages as application runs. */
	private static final Logger logger = Logger.getLogger(ListUtils.class);
	
	/**
	 * Instantiates a new list utils.
	 */
	private ListUtils()
	{
		logger.info("Initialized successfully.");
	}
	
	/**
	 * Gets the property as list based on the delimiters provided, and uses
	 * {@link LinkedList}.
	 * 
	 * @param property
	 *            the property
	 * @param delimiter
	 *            the delimiter
	 * @return the list
	 */
	public static List<String> asList(String property, String delimiter)
	{
		return asList(property, delimiter, LinkedList.class, String.class); 
	}
	
	/**
	 * Gets the property as list based on the delimiter provided, and uses your
	 * specified class.
	 * 
	 * @param <E>
	 *            the element type
	 * @param property
	 *            the property
	 * @param delimiter
	 *            the delimiter
	 * @param klass
	 *            the klass
	 * @param elementClass
	 *            the element class
	 * @return the list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <E> List<E> asList(String property, String delimiter, Class<? extends List> klass, Class<E> elementClass)
	{
		try
		{
			final List<E> list = klass.newInstance();
			try
			{
				final String value = getPropertyAsGeneric(property);
				if (isNotNull(value))
				{
					System.out.println("Good...");
					for (String element : value.split(delimiter))
					{
						System.out.println("vERY GOOD");
						list.add(to(element.trim(), elementClass));
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return list;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
