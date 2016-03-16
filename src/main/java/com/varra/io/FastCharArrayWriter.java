/*
 * util4J - FastCharArrayWriter.java, Jun 13, 2012 3:27:25 PM
 * 
 * Copyright 2012 Varra Ltd, Inc. All rights reserved.
 * Varra proprietary/confidential. Use is subject to license terms.
 */

package com.varra.io;

import java.io.IOException;
import java.io.Writer;

/**
 * Similar as {@link com.varra.io.FastByteArrayOutputStream} but for {@link Writer}.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 */
public class FastCharArrayWriter extends Writer
{
	
	/** The buffer. */
	private final FastCharBuffer buffer;
	
	/**
	 * Creates a new writer. The buffer capacity is initially 1024 bytes, though
	 * its size increases if necessary.
	 */
	public FastCharArrayWriter()
	{
		this(1024);
	}
	
	/**
	 * Creates a new char array writer, with a buffer capacity of the specified
	 * size, in bytes.
	 * 
	 * @param size
	 *            the initial size.
	 */
	public FastCharArrayWriter(int size)
	{
		buffer = new FastCharBuffer(size);
	}
	
	/**
	 * Write.
	 * 
	 * @param b
	 *            the b
	 * @param off
	 *            the off
	 * @param len
	 *            the len
	 * @see java.io.Writer#write(char[], int, int)
	 */
	@Override
	public void write(char[] b, int off, int len)
	{
		buffer.append(b, off, len);
	}
	
	/**
	 * Writes single byte.
	 * 
	 * @param b
	 *            the b
	 */
	@Override
	public void write(int b)
	{
		buffer.append((char) b);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Writer#write(java.lang.String, int, int)
	 */
	@Override
	public void write(String s, int off, int len)
	{
		write(s.toCharArray(), off, len);
	}
	
	/**
	 * Size.
	 * 
	 * @return the int
	 * @see java.io.CharArrayWriter#size()
	 */
	public int size()
	{
		return buffer.size();
	}
	
	/**
	 * Closing a <code>FastCharArrayWriter</code> has no effect. The methods in
	 * this class can be called after the stream has been closed without
	 * generating an <code>IOException</code>.
	 */
	@Override
	public void close()
	{
		// nop
	}
	
	/**
	 * Flushing a <code>FastCharArrayWriter</code> has no effects.
	 */
	@Override
	public void flush()
	{
		// nop
	}
	
	/**
	 * Reset.
	 * 
	 * @see java.io.CharArrayWriter#reset()
	 */
	public void reset()
	{
		buffer.clear();
	}
	
	/**
	 * Write to.
	 * 
	 * @param out
	 *            the out
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @see java.io.CharArrayWriter#writeTo(java.io.Writer)
	 */
	public void writeTo(Writer out) throws IOException
	{
		int index = buffer.index();
		for (int i = 0; i < index; i++)
		{
			char[] buf = buffer.array(i);
			out.write(buf);
		}
		out.write(buffer.array(index), 0, buffer.offset());
	}
	
	/**
	 * To char array.
	 * 
	 * @return the char[]
	 * @see java.io.CharArrayWriter#toCharArray()
	 */
	public char[] toCharArray()
	{
		return buffer.toArray();
	}
	
	/**
	 * To string.
	 * 
	 * @return the string
	 * @see java.io.CharArrayWriter#toString()
	 */
	@Override
	public String toString()
	{
		return new String(toCharArray());
	}
}