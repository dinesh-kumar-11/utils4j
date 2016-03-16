/*
 * utils4j - FileUtils.java, Jan 8, 2013 2:34:34 PM
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;

/**
 * TODO Description go here.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public final class FileUtils
{
	
	/** The Constant Q. */
	private static final String[] TYPES = new String[] { "bytes", "K", "M", "G", "T", "P", "E" };
	
	/**
	 * Gets the as string.
	 * 
	 * @param size
	 *            the bytes
	 * @return the as string
	 */
	public static String getASizeAsHumanReadableString(final long size)
	{
		for (int i = 6; i >= 0; i--)
		{
			final double step = Math.pow(1024, i);
			if (size > step)
			{
				return String.format("%3.1f %s", size / step, TYPES[i]);
			}
		}
		return Long.toString(size);
	}
	
	/**
	 * Touch.
	 * 
	 * @param file
	 *            the file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void touch(File file) throws IOException
	{
		if (!file.exists())
		{
			final OutputStream out = openOutputStream(file);
			if (ObjectUtils.isNotNull(out))
			{
				out.close();
			}
		}
		boolean success = file.setLastModified(System.currentTimeMillis());
		if (!success)
			throw new IOException("Unable to set the last modification time for " + file);
		else
			return;
	}
	
	/**
	 * Open output stream.
	 * 
	 * @param file
	 *            the file
	 * @return the file output stream
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static FileOutputStream openOutputStream(File file) throws IOException
	{
		if (file.exists())
		{
			if (file.isDirectory())
				throw new IOException("File '" + file + "' exists but is a directory");
			if (!file.canWrite())
				throw new IOException("File '" + file + "' cannot be written to");
		}
		else
		{
			File parent = file.getParentFile();
			if (parent != null && !parent.exists() && !parent.mkdirs())
				throw new IOException("File '" + file + "' could not be created");
		}
		return new FileOutputStream(file);
	}
}
