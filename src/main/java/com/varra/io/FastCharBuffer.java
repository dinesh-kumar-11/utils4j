/*
 * util4J - FastCharBuffer.java, Jun 13, 2012 3:26:54 PM
 * 
 * Copyright 2012 Varra Ltd, Inc. All rights reserved.
 * Varra proprietary/confidential. Use is subject to license terms.
 */

package com.varra.io;

/**
 * Fast, fast <code>char</code> buffer with some additional features.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 */
public class FastCharBuffer implements CharSequence, Appendable
{
	
	/** The buffers. */
	private char[][] buffers = new char[16][];
	
	/** The buffers count. */
	private int buffersCount;
	
	/** The current buffer index. */
	private int currentBufferIndex = -1;
	
	/** The current buffer. */
	private char[] currentBuffer;
	
	/** The offset. */
	private int offset;
	
	/** The count. */
	private int count;
	
	/**
	 * Creates a new <code>char</code> buffer. The buffer capacity is initially
	 * 1024 bytes, though its size increases if necessary.
	 */
	public FastCharBuffer()
	{
		this(1024);
	}
	
	/**
	 * Creates a new <code>char</code> buffer, with a buffer capacity of the
	 * specified size, in bytes.
	 * 
	 * @param size
	 *            the initial size.
	 */
	public FastCharBuffer(int size)
	{
		if (size < 0)
		{
			throw new IllegalArgumentException("Invalid size: " + size);
		}
		needNewBuffer(size);
	}
	
	/**
	 * Need new buffer.
	 * 
	 * @param newCount
	 *            the new count
	 */
	private void needNewBuffer(int newCount)
	{
		if (currentBufferIndex < buffersCount - 1)
		{ // recycling old buffer
			offset = 0;
			currentBufferIndex++;
			currentBuffer = buffers[currentBufferIndex];
		}
		else
		{ // creating new buffer
			int newBufferSize;
			if (currentBuffer == null)
			{
				newBufferSize = newCount;
			}
			else
			{
				newBufferSize = Math.max(currentBuffer.length << 1, newCount - count); // this
																						// will
																						// give
																						// no
																						// free
																						// additional
																						// space
				
			}
			
			currentBufferIndex++;
			currentBuffer = new char[newBufferSize];
			offset = 0;
			
			// add buffer
			if (currentBufferIndex >= buffers.length)
			{
				int newLen = buffers.length << 1;
				char[][] newBuffers = new char[newLen][];
				System.arraycopy(buffers, 0, newBuffers, 0, buffers.length);
				buffers = newBuffers;
			}
			buffers[currentBufferIndex] = currentBuffer;
			buffersCount++;
		}
	}
	
	/**
	 * Appends <code>char</code> array to buffer.
	 * 
	 * @param array
	 *            the array
	 * @param off
	 *            the off
	 * @param len
	 *            the len
	 * @return the fast char buffer
	 */
	public FastCharBuffer append(char[] array, int off, int len)
	{
		int end = off + len;
		if ((off < 0) || (off > array.length) || (len < 0) || (end > array.length) || (end < 0))
		{
			throw new IndexOutOfBoundsException();
		}
		if (len == 0)
		{
			return this;
		}
		int newCount = count + len;
		int remaining = len;
		while (remaining > 0)
		{
			int part = Math.min(remaining, currentBuffer.length - offset);
			System.arraycopy(array, end - remaining, currentBuffer, offset, part);
			remaining -= part;
			offset += part;
			count += part;
			if (remaining > 0)
			{
				needNewBuffer(newCount);
			}
		}
		return this;
	}
	
	/**
	 * Appends <code>char</code> array to buffer.
	 * 
	 * @param array
	 *            the array
	 * @return the fast char buffer
	 */
	public FastCharBuffer append(char[] array)
	{
		return append(array, 0, array.length);
	}
	
	/**
	 * Appends single <code>char</code> to buffer.
	 * 
	 * @param element
	 *            the element
	 * @return the fast char buffer
	 */
	public FastCharBuffer append(char element)
	{
		if (offset == currentBuffer.length)
		{
			needNewBuffer(count + 1);
		}
		
		currentBuffer[offset] = element;
		offset++;
		count++;
		
		return this;
	}
	
	/**
	 * Appends another fast buffer to this one.
	 * 
	 * @param buff
	 *            the buff
	 * @return the fast char buffer
	 */
	public FastCharBuffer append(FastCharBuffer buff)
	{
		for (int i = 0; i < buff.currentBufferIndex; i++)
		{
			append(buff.buffers[i]);
		}
		append(buff.currentBuffer, 0, buff.offset);
		return this;
	}
	
	/**
	 * Returns buffer size.
	 * 
	 * @return the int
	 */
	public int size()
	{
		return count;
	}
	
	/**
	 * Tests if this buffer has no elements.
	 * 
	 * @return true, if is empty
	 */
	public boolean isEmpty()
	{
		return count == 0;
	}
	
	/**
	 * Returns current index of inner <code>char</code> array chunk. Represents
	 * the index of last used inner array chunk.
	 * 
	 * @return the int
	 */
	public int index()
	{
		return currentBufferIndex;
	}
	
	/**
	 * Returns the offset of last used element in current inner array chunk.
	 * 
	 * @return the int
	 */
	public int offset()
	{
		return offset;
	}
	
	/**
	 * Returns <code>char</code> inner array chunk at given index. May be used
	 * for iterating inner chunks in fast manner.
	 * 
	 * @param index
	 *            the index
	 * @return the char[]
	 */
	public char[] array(int index)
	{
		return buffers[index];
	}
	
	/**
	 * Resets the buffer content.
	 */
	public void clear()
	{
		count = 0;
		offset = 0;
		currentBufferIndex = 0;
		currentBuffer = buffers[currentBufferIndex];
		buffersCount = 1;
	}
	
	/**
	 * Creates <code>char</code> array from buffered content.
	 * 
	 * @return the char[]
	 */
	public char[] toArray()
	{
		int remaining = count;
		int pos = 0;
		char[] array = new char[count];
		for (char[] buf : buffers)
		{
			int c = Math.min(buf.length, remaining);
			System.arraycopy(buf, 0, array, pos, c);
			pos += c;
			remaining -= c;
			if (remaining == 0)
			{
				break;
			}
		}
		return array;
	}
	
	/**
	 * Creates <code>char</code> subarray from buffered content.
	 * 
	 * @param start
	 *            the start
	 * @param len
	 *            the len
	 * @return the char[]
	 */
	public char[] toArray(int start, int len)
	{
		int remaining = len;
		int pos = 0;
		char[] array = new char[len];
		
		if (len == 0)
		{
			return array;
		}
		
		int i = 0;
		while (start >= buffers[i].length)
		{
			start -= buffers[i].length;
			i++;
		}
		
		while (i < buffersCount)
		{
			char[] buf = buffers[i];
			int c = Math.min(buf.length - start, remaining);
			System.arraycopy(buf, start, array, pos, c);
			pos += c;
			remaining -= c;
			if (remaining == 0)
			{
				break;
			}
			start = 0;
			i++;
		}
		return array;
	}
	
	/**
	 * Returns <code>char</code> element at given index.
	 * 
	 * @param index
	 *            the index
	 * @return the char
	 */
	public char get(int index)
	{
		if (index >= count)
		{
			throw new IndexOutOfBoundsException();
		}
		int ndx = 0;
		while (true)
		{
			char[] b = buffers[ndx];
			if (index < b.length)
			{
				return b[index];
			}
			ndx++;
			index -= b.length;
		}
	}
	
	// @@generated
	
	/**
	 * Returns buffer length, same as {@link #size()}.
	 * 
	 * @return the int
	 */
	public int length()
	{
		return count;
	}
	
	/**
	 * Returns buffer content as a string.
	 * 
	 * @return the string
	 */
	public String toString()
	{
		return new String(toArray());
	}
	
	/**
	 * Returns char at given index.
	 * 
	 * @param index
	 *            the index
	 * @return the char
	 */
	public char charAt(int index)
	{
		return get(index);
	}
	
	/**
	 * Returns sub sequence.
	 * 
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * @return the char sequence
	 */
	public CharSequence subSequence(int start, int end)
	{
		int len = end - start;
		return new StringBuilder(len).append(toArray(start, len));
	}
	
	// ----------------------------------------------------------------
	// additional appenders
	
	/**
	 * Appends string content to buffer.
	 * 
	 * @param string
	 *            the string
	 * @return the fast char buffer
	 */
	public FastCharBuffer append(String string)
	{
		return append(string.toCharArray());
	}
	
	/**
	 * Appends character sequence to buffer.
	 * 
	 * @param csq
	 *            the csq
	 * @return the fast char buffer
	 */
	public FastCharBuffer append(CharSequence csq)
	{
		append(csq, 0, csq.length());
		return this;
	}
	
	/**
	 * Appends character sequence to buffer.
	 * 
	 * @param csq
	 *            the csq
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * @return the fast char buffer
	 */
	public FastCharBuffer append(CharSequence csq, int start, int end)
	{
		for (int i = start; i < end; i++)
		{
			append(csq.charAt(i));
		}
		return this;
	}
	
}
