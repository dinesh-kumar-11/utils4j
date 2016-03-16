/*
 * utils4j - XMLUtils.java, Aug 29, 2013 3:29:13 PM
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

import static com.varra.util.StringPool.NEWLINE;
import static com.varra.util.StringPool.RETURN;
import static com.varra.util.StringPool.SPACE;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * XML related utilities
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 1.0
 * 
 */
public final class XMLUtils
{
	
	/** The Constant TYPE_REGEX. */
	public static final String TYPE_REGEX = ".*<.*type=\"(.*)\">.*";
	
	/** The type pattern. */
	private static final Pattern typePattern = Pattern.compile(TYPE_REGEX);
	
	/**
	 * Instantiates a new xML utils.
	 */
	private XMLUtils()
	{
		
	}
	
	/**
	 * Gets the xML.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param type
	 *            the type
	 * @return the xML
	 * @throws JAXBException
	 *             the jAXB exception
	 */
	public static <T> String getXML(T type) throws JAXBException
	{
		final JAXBContext context = JAXBContext.newInstance(type.getClass());
		final StringWriter stringWriter = new StringWriter();
		context.createMarshaller().marshal(type, stringWriter);
		final String result = stringWriter.toString();
		return result.substring(result.indexOf(">") + 1).replace(type.getClass().getSimpleName().toLowerCase(), type.getClass().getName());
	}
	
	/**
	 * Gets the.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param xml
	 *            the xml
	 * @param klass
	 *            the klass
	 * @return the t
	 * @throws JAXBException
	 *             the jAXB exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String xml, Class<T> klass) throws JAXBException
	{
		final JAXBContext context = JAXBContext.newInstance(klass);
		return (T) context.createUnmarshaller().unmarshal(new StringReader(xml));
	}
	
	/**
	 * Gets the object by converting the xml, type {@link #TYPE_REGEX} will be
	 * used to parse the class type from attribute in the message.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param xml
	 *            the xml
	 * @return the xML
	 * @throws JAXBException
	 *             the jAXB exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getByType(String xml) throws JAXBException
	{
		final String klass = parse(xml, typePattern);
		final JAXBContext context = JAXBContext.newInstance(klass);
		return (T) context.createUnmarshaller().unmarshal(new StringReader(xml));
	}
	
	/**
	 * Gets the Object by converting the xml, regex will be used to parse the
	 * class type from attribute in the message.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param xml
	 *            the xml
	 * @param regex
	 *            the regex
	 * @return the xML
	 * @throws JAXBException
	 *             the jAXB exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String xml, String regex) throws JAXBException
	{
		final Pattern pattern = Pattern.compile(regex);
		final String klass = parse(xml, pattern);
		final JAXBContext context = JAXBContext.newInstance(klass);
		return (T) context.createUnmarshaller().unmarshal(new StringReader(xml));
	}
	
	/**
	 * Parses the message for type attribute and get the result as list.
	 * 
	 * @param message
	 *            the message
	 * @param regex
	 *            the regex
	 * @return the list
	 */
	private static String parse(String message, Pattern pattern)
	{
		final Matcher m = pattern.matcher(message.replaceAll(NEWLINE, SPACE).replaceAll(RETURN, SPACE));
		if (m.matches())
		{
			return m.groupCount() > 0 ? m.group(1) : null;
		}
		return null;
	}
}
