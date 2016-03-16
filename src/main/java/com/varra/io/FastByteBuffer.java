/*
 * util4J - FastByteBuffer.java, Jun 13, 2012 3:25:05 PM
 * 
 * Copyright 2012 Varra Ltd, Inc. All rights reserved.
 * Varra proprietary/confidential. Use is subject to license terms.
 */

package com.varra.io;

/**
 * Fast, fast <code>byte</code> buffer.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 */
public class FastByteBuffer
{
	
	/** The buffers. */
	private byte[][] buffers = new byte[16][];
	
	/** The buffers count. */
	private int buffersCount;
	
	/** The current buffer index. */
	private int currentBufferIndex = -1;
	
	/** The current buffer. */
	private byte[] currentBuffer;
	
	/** The offset. */
	private int offset;
	
	/** The count. */
	private int count;
	
	/**
	 * Creates a new <code>byte</code> buffer. The buffer capacity is initially
	 * 1024 bytes, though its size increases if necessary.
	 */
	public FastByteBuffer()
	{
		this(1024);
	}
	
	/**
	 * Creates a new <code>byte</code> buffer, with a buffer capacity of the
	 * specified size, in bytes.
	 * 
	 * @param size
	 *            the initial size.
	 */
	public FastByteBuffer(int size)
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
			currentBuffer = new byte[newBufferSize];
			offset = 0;
			
			// add buffer
			if (currentBufferIndex >= buffers.length)
			{
				int newLen = buffers.length << 1;
				byte[][] newBuffers = new byte[newLen][];
				System.arraycopy(buffers, 0, newBuffers, 0, buffers.length);
				buffers = newBuffers;
			}
			buffers[currentBufferIndex] = currentBuffer;
			buffersCount++;
		}
	}
	
	/**
	 * Appends <code>byte</code> array to buffer.
	 * 
	 * @param array
	 *            the array
	 * @param off
	 *            the off
	 * @param len
	 *            the len
	 * @return the fast byte buffer
	 */
	public FastByteBuffer append(byte[] array, int off, int len)
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
	 * Appends <code>byte</code> array to buffer.
	 * 
	 * @param array
	 *            the array
	 * @return the fast byte buffer
	 */
	public FastByteBuffer append(byte[] array)
	{
		return append(array, 0, array.length);
	}
	
	/**
	 * Appends single <code>byte</code> to buffer.
	 * 
	 * @param element
	 *            the element
	 * @return the fast byte buffer
	 */
	public FastByteBuffer append(byte element)
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
	 * @return the fast byte buffer
	 */
	public FastByteBuffer append(FastByteBuffer buff)
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
	 * Returns current index of inner <code>byte</code> array chunk. Represents
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
	 * Returns <code>byte</code> inner array chunk at given index. May be used
	 * for iterating inner chunks in fast manner.
	 * 
	 * @param index
	 *            the index
	 * @return the byte[]
	 */
	public byte[] array(int index)
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
	 * Creates <code>byte</code> array from buffered content.
	 * 
	 * @return the byte[]
	 */
	public byte[] toArray()
	{
		int remaining = count;
		int pos = 0;
		byte[] array = new byte[count];
		for (byte[] buf : buffers)
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
	 * Creates <code>byte</code> subarray from buffered content.
	 * 
	 * @param start
	 *            the start
	 * @param len
	 *            the len
	 * @return the byte[]
	 */
	public byte[] toArray(int start, int len)
	{
		int remaining = len;
		int pos = 0;
		byte[] array = new byte[len];
		
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
			byte[] buf = buffers[i];
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
	 * Returns <code>byte</code> element at given index.
	 * 
	 * @param index
	 *            the index
	 * @return the byte
	 */
	public byte get(int index)
	{
		if (index >= count)
		{
			throw new IndexOutOfBoundsException();
		}
		int ndx = 0;
		while (true)
		{
			byte[] b = buffers[ndx];
			if (index < b.length)
			{
				return b[index];
			}
			ndx++;
			index -= b.length;
		}
	}
	
}
