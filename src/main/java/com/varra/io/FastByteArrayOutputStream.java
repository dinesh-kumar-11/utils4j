/*
 * util4J - FastByteArrayOutputStream.java, Oct 8, 2012 2:56:51 PM
 * 
 * Copyright 2012 Varra Ltd, Inc. All rights reserved.
 * Varra proprietary/confidential. Use is subject to license terms.
 */
package com.varra.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * This class implements an output stream in which the data is written into a
 * byte array. The buffer automatically grows as data is written to it.
 * <p>
 * The data can be retrieved using <code>toByteArray()</code> and
 * <code>toString()</code>.
 * <p>
 * Closing a <code>FastByteArrayOutputStream</code> has no effect. The methods
 * in this class can be called after the stream has been closed without
 * generating an <code>IOException</code>.
 * <p>
 * This is an alternative implementation of the
 * java.io.FastByteArrayOutputStream class. The original implementation only
 * allocates 32 bytes at the beginning. As this class is designed for heavy duty
 * it starts at 1024 bytes. In contrast to the original it doesn't reallocate
 * the whole memory block but allocates additional buffers. This way no buffers
 * need to be garbage collected and the contents don't have to be copied to the
 * new buffer. This class is designed to behave exactly like the original. The
 * only exception is the depreciated toString(int) method that has been ignored.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 */
public class FastByteArrayOutputStream extends OutputStream
{
	
	/** The buffer. */
	private final FastByteBuffer buffer;
	
	/**
	 * Creates a new byte array output stream. The buffer capacity is initially
	 * 1024 bytes, though its size increases if necessary.
	 */
	public FastByteArrayOutputStream()
	{
		this(1024);
	}
	
	/**
	 * Creates a new byte array output stream, with a buffer capacity of the
	 * specified size, in bytes.
	 *
	 * @param size the initial size.
	 */
	public FastByteArrayOutputStream(int size)
	{
		buffer = new FastByteBuffer(size);
	}
	
	/**
	 * Write.
	 *
	 * @param b the b
	 * @param off the off
	 * @param len the len
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	@Override
	public void write(byte[] b, int off, int len)
	{
		buffer.append(b, off, len);
	}
	
	/**
	 * Writes single byte.
	 *
	 * @param b the b
	 */
	@Override
	public void write(int b)
	{
		buffer.append((byte) b);
	}
	
	/**
	 * Size.
	 *
	 * @return the int
	 * @see java.io.ByteArrayOutputStream#size()
	 */
	public int size()
	{
		return buffer.size();
	}
	
	/**
	 * Closing a <code>FastByteArrayOutputStream</code> has no effect. The
	 * methods in this class can be called after the stream has been closed
	 * without generating an <code>IOException</code>.
	 */
	@Override
	public void close()
	{
		// nop
	}
	
	/**
	 * Reset.
	 *
	 * @see java.io.ByteArrayOutputStream#reset()
	 */
	public void reset()
	{
		buffer.clear();
	}
	
	/**
	 * Write to.
	 *
	 * @param out the out
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see java.io.ByteArrayOutputStream#writeTo(OutputStream)
	 */
	public void writeTo(OutputStream out) throws IOException
	{
		int index = buffer.index();
		for (int i = 0; i < index; i++)
		{
			byte[] buf = buffer.array(i);
			out.write(buf);
		}
		out.write(buffer.array(index), 0, buffer.offset());
	}
	
	/**
	 * To byte array.
	 *
	 * @return the byte[]
	 * @see java.io.ByteArrayOutputStream#toByteArray()
	 */
	public byte[] toByteArray()
	{
		return buffer.toArray();
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 * @see java.io.ByteArrayOutputStream#toString()
	 */
	@Override
	public String toString()
	{
		return new String(toByteArray());
	}
	
	/**
	 * To string.
	 *
	 * @param enc the enc
	 * @return the string
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @see java.io.ByteArrayOutputStream#toString(String)
	 */
	public String toString(String enc) throws UnsupportedEncodingException
	{
		return new String(toByteArray(), enc);
	}
	
}