/*
 * utils4j - AbstractConfigFileReader.java, Aug 16, 2015 4:48:52 PM
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;

import static com.varra.props.VarraProperties.*;

/**
 * Common configuration entry parser class. Used as more of a place-holder for
 * common functionality. The only method used so far is
 * {@link #readStreamContentAsString(InputStream) reading the input stream} .
 * 
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class AbstractConfigFileReader
{
	
	/** The Constant BUFFER_SIZE. */
	private static final int BUFFER_SIZE = 200;
	
	/**
	 * * Returns the content of the input stream. Reads the stream and creates a
	 * string out of it's content.
	 *
	 * @param stream
	 *            the stream
	 * @return the content of the input stream.
	 * @throws IOException
	 *             if the stream could not be read.
	 */
	public static String readStreamContentAsString(final InputStream stream) throws IOException
	{
		final StringBuffer contentBuffer = new StringBuffer(BUFFER_SIZE);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(stream, UTF_8));
		String line = null;
		while ((line = reader.readLine()) != null)
		{
			contentBuffer.append(line).append(NEW_LINE);
		}
		return contentBuffer.toString();
	}
	
}
