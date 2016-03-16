/*
 * utils4j - RapidFastMap.java, Feb 4, 2011 12:05:59 PM
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

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * A {@link RapidFastMap} which provides a fast map with concurrency. A thread
 * safe implementation is added on top of the Hash Map mechanism.
 * 
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 * @author Rajakrishna V. Reddy
 * @version 1.0
 */
public class RapidFastMap<K, V> implements Cloneable, Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The map. */
	@XmlElementWrapper
	final private LinkedHashMap<K, V> map;
	
	/**
	 * Instantiates a new empty rapid fast map with a default initial capacity,
	 * load factor, and concurrencyLevel.
	 */
	public RapidFastMap()
	{
		map = new LinkedHashMap<K, V>();
	}
	
	/**
	 * Instantiates a new rapid fast map with the given map elements and its
	 * capacity.
	 * 
	 * @param tempMap
	 *            the temp map
	 */
	public RapidFastMap(Map<? extends K, ? extends V> tempMap)
	{
		map = new LinkedHashMap<K, V>(tempMap);
	}
	
	/**
	 * Returns <tt>true</tt> if this map contains no key-value mappings.
	 * <p>
	 * 
	 * This implementation returns <tt>size() == 0</tt>.
	 * 
	 * @return <tt>true</tt> if this map contains no key-value mappings.
	 */
	public boolean isEmpty()
	{
		return map.isEmpty();
	}
	
	/**
	 * Returns the number of key-value mappings in this map. If the map contains
	 * more than <tt>Integer.MAX_VALUE</tt> elements, returns
	 * <tt>Integer.MAX_VALUE</tt>.
	 * <p>
	 * 
	 * This implementation returns <tt>entrySet().size()</tt>.
	 * 
	 * @return the number of key-value mappings in this map.
	 */
	public int size()
	{
		return map.size();
	}
	
	/**
	 * Returns the value to which the specified key is mapped in this table.
	 * 
	 * @param key
	 *            a key in the table.
	 * @return the value to which the key is mapped in this table; <tt>null</tt>
	 *         if the key is not mapped to any value in this table.
	 * @throws NullPointerException
	 *             if the key is <tt>null</tt>.
	 */
	public V get(K key)
	{
		return map.get(key);
	}
	
	/**
	 * Returns this Object as {@link Map}.
	 * 
	 * @return this as map
	 */
	public Map<K, V> getAsMap()
	{
		return map;
	}
	
	/**
	 * Tests if the specified object is a key in this table.
	 * 
	 * @param key
	 *            possible key.
	 * @return <tt>true</tt> if and only if the specified object is a key in
	 *         this table, as determined by the <tt>equals</tt> method;
	 *         <tt>false</tt> otherwise.
	 * @throws NullPointerException
	 *             if the key is <tt>null</tt>.
	 */
	public boolean containsKey(K key)
	{
		return map.containsKey(key);
	}
	
	/**
	 * Returns <tt>true</tt> if this map maps one or more keys to the specified
	 * value. Note: This method requires a full internal traversal of the hash
	 * table, and so is much slower than method <tt>containsKey</tt>.
	 * 
	 * @param value
	 *            value whose presence in this map is to be tested.
	 * @return <tt>true</tt> if this map maps one or more keys to the specified
	 *         value.
	 * @throws NullPointerException
	 *             if the value is <tt>null</tt>.
	 */
	public boolean containsValue(V value)
	{
		return map.containsValue(value);
	}
	
	/**
	 * Maps the specified <tt>key</tt> to the specified <tt>value</tt> in this
	 * table. Neither the key nor the value can be <tt>null</tt>.
	 * 
	 * <p>
	 * The value can be retrieved by calling the <tt>get</tt> method with a key
	 * that is equal to the original key.
	 * 
	 * @param key
	 *            the table key.
	 * @param value
	 *            the value.
	 * @return the previous value of the specified key in this table, or
	 *         <tt>null</tt> if it did not have one.
	 * @throws NullPointerException
	 *             if the key or value is <tt>null</tt>.
	 */
	public V put(K key, V value)
	{
		return map.put(key, value);
	}
	
	/**
	 * If the specified key is not already associated with a value, associate it
	 * with the given value. This is equivalent to
	 * 
	 * <pre>
	 * if (!map.containsKey(key))
	 * 	return map.put(key, value);
	 * else
	 * 	return map.get(key);
	 * </pre>
	 * 
	 * Except that the action is performed atomically.
	 * 
	 * @param key
	 *            key with which the specified value is to be associated.
	 * @param value
	 *            value to be associated with the specified key.
	 * @return previous value associated with specified key, or <tt>null</tt> if
	 *         there was no mapping for key.
	 * @throws NullPointerException
	 *             if the specified key or value is <tt>null</tt>.
	 */
	public V putIfAbsent(K key, V value)
	{
		if (map.containsKey(key) && map.get(key).equals(value))
			return map.put(key, value);
		return null;
	}
	
	/**
	 * Copies all of the mappings from the specified map to this one.
	 * 
	 * These mappings replace any mappings that this map had for any of the keys
	 * currently in the specified Map.
	 * 
	 * @param t
	 *            Mappings to be stored in this map.
	 */
	public void putAll(Map<? extends K, ? extends V> t)
	{
		map.putAll(t);
	}
	
	/**
	 * Removes the key (and its corresponding value) from this table. This
	 * method does nothing if the key is not in the table.
	 * 
	 * @param key
	 *            the key that needs to be removed.
	 * @return the value to which the key had been mapped in this table, or
	 *         <tt>null</tt> if the key did not have a mapping.
	 * @throws NullPointerException
	 *             if the key is <tt>null</tt>.
	 */
	public V remove(K key)
	{
		return map.remove(key);
	}
	
	/**
	 * Remove entry for key only if currently mapped to given value. Acts as
	 * 
	 * <pre>
	 * if (map.get(key).equals(value))
	 * {
	 * 	map.remove(key);
	 * 	return true;
	 * }
	 * else
	 * 	return false;
	 * </pre>
	 * 
	 * except that the action is performed atomically.
	 * 
	 * @param key
	 *            key with which the specified value is associated.
	 * @param value
	 *            value associated with the specified key.
	 * @return true if the value was removed
	 * @throws NullPointerException
	 *             if the specified key is <tt>null</tt>.
	 */
	public boolean remove(K key, V value)
	{
		if (map.containsKey(key) && map.get(key).equals(value))
		{
			map.remove(key);
			return true;
		}
		return false;
	}
	
	/**
	 * Replace entry for key only if currently mapped to given value. Acts as
	 * 
	 * <pre>
	 * if (map.get(key).equals(oldValue))
	 * {
	 * 	map.put(key, newValue);
	 * 	return true;
	 * }
	 * else
	 * 	return false;
	 * </pre>
	 * 
	 * except that the action is performed atomically.
	 * 
	 * @param key
	 *            key with which the specified value is associated.
	 * @param oldValue
	 *            value expected to be associated with the specified key.
	 * @param newValue
	 *            value to be associated with the specified key.
	 * @return true if the value was replaced
	 * @throws NullPointerException
	 *             if the specified key or values are <tt>null</tt>.
	 */
	public boolean replace(K key, V oldValue, V newValue)
	{
		V value = map.put(key, newValue);
		return value != null && value.equals(oldValue);
	}
	
	/**
	 * Replace entry for key only if currently mapped to some value. Acts as
	 * 
	 * <pre>
	 *  if ((map.containsKey(key)) {
	 *     return map.put(key, value);
	 * } else return null;
	 * </pre>
	 * 
	 * except that the action is performed atomically.
	 * 
	 * @param key
	 *            key with which the specified value is associated.
	 * @param value
	 *            value to be associated with the specified key.
	 * @return previous value associated with specified key, or <tt>null</tt> if
	 *         there was no mapping for key.
	 * @throws NullPointerException
	 *             if the specified key or value is <tt>null</tt>.
	 */
	public V replace(K key, V value)
	{
		return map.put(key, value);
	}
	
	/**
	 * Removes all mappings from this map.
	 */
	public void clear()
	{
		map.clear();
	}
	
	/**
	 * Returns a set view of the keys contained in this map. The set is backed
	 * by the map, so changes to the map are reflected in the set, and
	 * vice-versa. The set supports element removal, which removes the
	 * corresponding mapping from this map, via the <tt>Iterator.remove</tt>,
	 * <tt>Set.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt>, and
	 * <tt>clear</tt> operations. It does not support the <tt>add</tt> or
	 * <tt>addAll</tt> operations. The view's returned <tt>iterator</tt> is a
	 * "weakly consistent" iterator that will never throw
	 * {@link java.util.ConcurrentModificationException}, and guarantees to
	 * traverse elements as they existed upon construction of the iterator, and
	 * may (but is not guaranteed to) reflect any modifications subsequent to
	 * construction.
	 * 
	 * @return a set view of the keys contained in this map.
	 */
	public Set<K> keySet()
	{
		return map.keySet();
	}
	
	/**
	 * Returns a collection view of the values contained in this map. The
	 * collection is backed by the map, so changes to the map are reflected in
	 * the collection, and vice-versa. The collection supports element removal,
	 * which removes the corresponding mapping from this map, via the
	 * <tt>Iterator.remove</tt>, <tt>Collection.remove</tt>, <tt>removeAll</tt>,
	 * <tt>retainAll</tt>, and <tt>clear</tt> operations. It does not support
	 * the <tt>add</tt> or <tt>addAll</tt> operations. The view's returned
	 * <tt>iterator</tt> is a "weakly consistent" iterator that will never throw
	 * {@link java.util.ConcurrentModificationException}, and guarantees to
	 * traverse elements as they existed upon construction of the iterator, and
	 * may (but is not guaranteed to) reflect any modifications subsequent to
	 * construction.
	 * 
	 * @return a collection view of the values contained in this map.
	 */
	public Collection<V> values()
	{
		return map.values();
	}
	
	/**
	 * Returns a collection view of the mappings contained in this map. Each
	 * element in the returned collection is a <tt>Map.Entry</tt>. The
	 * collection is backed by the map, so changes to the map are reflected in
	 * the collection, and vice-versa. The collection supports element removal,
	 * which removes the corresponding mapping from the map, via the
	 * <tt>Iterator.remove</tt>, <tt>Collection.remove</tt>, <tt>removeAll</tt>,
	 * <tt>retainAll</tt>, and <tt>clear</tt> operations. It does not support
	 * the <tt>add</tt> or <tt>addAll</tt> operations. The view's returned
	 * <tt>iterator</tt> is a "weakly consistent" iterator that will never throw
	 * {@link java.util.ConcurrentModificationException}, and guarantees to
	 * traverse elements as they existed upon construction of the iterator, and
	 * may (but is not guaranteed to) reflect any modifications subsequent to
	 * construction.
	 * 
	 * @return a collection view of the mappings contained in this map.
	 */
	public Set<Map.Entry<K, V>> entrySet()
	{
		return map.entrySet();
	}
	
	/**
	 * Returns an enumeration of the keys in this table.
	 * 
	 * @return an enumeration of the keys in this table.
	 * @see #keySet
	 */
	public Set<K> keys()
	{
		return map.keySet();
	}
	
	/**
	 * Returns an enumeration of the values in this table.
	 * 
	 * @return an enumeration of the values in this table.
	 * @see #values
	 */
	public Collection<V> elements()
	{
		return map.values();
	}
	
	/**
	 * Compares the specified object with this map for equality. Returns
	 * <tt>true</tt> if the given object is also a map and the two maps
	 * represent the same mappings. More formally, two maps <tt>t1</tt> and
	 * <tt>t2</tt> represent the same mappings if
	 * <tt>t1.keySet().equals(t2.keySet())</tt> and for every key <tt>k</tt> in
	 * <tt>t1.keySet()</tt>, <tt> (t1.get(k)==null ? t2.get(k)==null :
	 * t1.get(k).equals(t2.get(k))) </tt>. This ensures that the <tt>equals</tt>
	 * method works properly across different implementations of the map
	 * interface.
	 * <p>
	 * 
	 * This implementation first checks if the specified object is this map; if
	 * so it returns <tt>true</tt>. Then, it checks if the specified object is a
	 * map whose size is identical to the size of this set; if not, it returns
	 * <tt>false</tt>. If so, it iterates over this map's <tt>entrySet</tt>
	 * collection, and checks that the specified map contains each mapping that
	 * this map contains. If the specified map fails to contain such a mapping,
	 * <tt>false</tt> is returned. If the iteration completes, <tt>true</tt> is
	 * returned.
	 * 
	 * @param o
	 *            object to be compared for equality with this map.
	 * @return <tt>true</tt> if the specified object is equal to this map.
	 */
	public boolean equals(Object o)
	{
		return map.equals(o);
	}
	
	/**
	 * Returns the hash code value for this map. The hash code of a map is
	 * defined to be the sum of the hash codes of each entry in the map's
	 * <tt>entrySet()</tt> view. This ensures that <tt>t1.equals(t2)</tt>
	 * implies that <tt>t1.hashCode()==t2.hashCode()</tt> for any two maps
	 * <tt>t1</tt> and <tt>t2</tt>, as required by the general contract of
	 * Object.hashCode.
	 * <p>
	 * 
	 * This implementation iterates over <tt>entrySet()</tt>, calling
	 * <tt>hashCode</tt> on each element (entry) in the Collection, and adding
	 * up the results.
	 * 
	 * @return the hash code value for this map.
	 * @see java.util.Map.Entry#hashCode()
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 * @see Set#equals(Object)
	 */
	public int hashCode()
	{
		return map.hashCode();
	}
	
	/**
	 * Returns a string representation of this map. The string representation
	 * consists of a list of key-value mappings in the order returned by the
	 * map's <tt>entrySet</tt> view's iterator, enclosed in braces (
	 * <tt>"{}"</tt>). Adjacent mappings are separated by the characters
	 * <tt>", "</tt> (comma and space). Each key-value mapping is rendered as
	 * the key followed by an equals sign (<tt>"="</tt>) followed by the
	 * associated value. Keys and values are converted to strings as by
	 * <tt>String.valueOf(Object)</tt>.
	 * <p>
	 * 
	 * This implementation creates an empty string buffer, appends a left brace,
	 * and iterates over the map's <tt>entrySet</tt> view, appending the string
	 * representation of each <tt>map.entry</tt> in turn. After appending each
	 * entry except the last, the string <tt>", "</tt> is appended. Finally a
	 * right brace is appended. A string is obtained from the stringBuffer, and
	 * returned.
	 * 
	 * @return a String representation of this map.
	 */
	public String toString()
	{
		return this.getClass().getName() + ": " + map.toString();
	}
}
