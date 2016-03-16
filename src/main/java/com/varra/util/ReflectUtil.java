/*
 * utils4j - ReflectUtil.java, Aug 3, 2013 10:38:29 AM
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

import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.varra.log.Logger;
import com.varra.lang.Void;

import static com.varra.util.WrapperUtils.*;
import static com.varra.util.ObjectUtils.*;

/**
 * Various java.lang.reflect utilities.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ReflectUtil
{
	
	/** The Constant VOID. */
	private static final String VOID = "void";
	
	/** The Constant LOGGER. */
	private static final Logger logger = Logger.getLogger(ReflectUtil.class);
	
	/** an empty class array. */
	public static final Class[] NO_PARAMETERS = new Class[0];
	
	/** an empty object array. */
	public static final Object[] NO_ARGUMENTS = new Object[0];
	
	/** an empty object array. */
	public static final Type[] NO_TYPES = new Type[0];
	
	/** The Constant METHOD_GET_PREFIX. */
	public static final String METHOD_GET_PREFIX = "get";
	
	/** The Constant METHOD_IS_PREFIX. */
	public static final String METHOD_IS_PREFIX = "is";
	
	/** The Constant METHOD_SET_PREFIX. */
	public static final String METHOD_SET_PREFIX = "set";
	
	// ---------------------------------------------------------------- method0
	/** The _get method0. */
	private static Method _getMethod0;
	
	static
	{
		try
		{
			_getMethod0 = Class.class.getDeclaredMethod("getMethod0", String.class, Class[].class);
			_getMethod0.setAccessible(true);
		}
		catch (Exception ignore)
		{
			try
			{
				_getMethod0 = Class.class.getMethod("getMethod", String.class, Class[].class);
			}
			catch (Exception ignored)
			{
				_getMethod0 = null;
			}
		}
	}
	
	/**
	 * Invokes private <code>Class.getMethod0()</code> without throwing
	 * <code>NoSuchMethodException</code>. Returns only public methods or
	 * <code>null</code> if method not found. Since no exception is throwing, it
	 * works faster.
	 * 
	 * @param c
	 *            class to inspect
	 * @param name
	 *            name of method to find
	 * @param parameterTypes
	 *            parameter types
	 * @return founded method, or null
	 */
	public static Method getMethod0(Class c, String name, Class... parameterTypes)
	{
		try
		{
			return (Method) _getMethod0.invoke(c, name, parameterTypes);
		}
		catch (Exception ignore)
		{
			return null;
		}
	}
	
	// ---------------------------------------------------------------- find
	// method
	
	/**
	 * Returns method from an object, matched by name. This may be considered as
	 * a slow operation, since methods are matched one by one. Returns only
	 * accessible methods. Only first method is matched.
	 * 
	 * @param c
	 *            class to examine
	 * @param methodName
	 *            Full name of the method.
	 * @return null if method not found
	 */
	public static Method findMethod(Class c, String methodName)
	{
		return findDeclaredMethod(c, methodName, true);
	}
	
	/**
	 * Find declared method.
	 * 
	 * @param c
	 *            the c
	 * @param methodName
	 *            the method name
	 * @return the method
	 */
	public static Method findDeclaredMethod(Class c, String methodName)
	{
		return findDeclaredMethod(c, methodName, false);
	}
	
	/**
	 * Find declared method.
	 * 
	 * @param c
	 *            the c
	 * @param methodName
	 *            the method name
	 * @param publicOnly
	 *            the public only
	 * @return the method
	 */
	private static Method findDeclaredMethod(Class c, String methodName, boolean publicOnly)
	{
		if ((methodName == null) || (c == null))
		{
			return null;
		}
		Method[] ms = publicOnly ? c.getMethods() : c.getDeclaredMethods();
		for (Method m : ms)
		{
			if (m.getName().equals(methodName))
			{
				return m;
			}
		}
		return null;
	}
	
	// ---------------------------------------------------------------- classes
	
	/**
	 * Returns classes from array of specified objects.
	 * 
	 * @param objects
	 *            the objects
	 * @return the classes
	 */
	public static Class[] getClasses(Object... objects)
	{
		if (objects == null)
		{
			return null;
		}
		Class[] result = new Class[objects.length];
		for (int i = 0; i < objects.length; i++)
		{
			if (objects[i] != null)
			{
				result[i] = objects[i].getClass();
			}
		}
		return result;
	}
	
	// ---------------------------------------------------------------- invoke
	
	/**
	 * Invokes accessible method of an object.
	 * 
	 * @param c
	 *            class that contains method
	 * @param obj
	 *            object to execute
	 * @param method
	 *            method to invoke
	 * @param paramClasses
	 *            classes of parameters
	 * @param params
	 *            parameters �
	 * @return the object
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	public static Object invoke(Class c, Object obj, String method, Class[] paramClasses, Object[] params)
			throws IllegalAccessException, NoSuchMethodException, InvocationTargetException
	{
		final Method m = c.getMethod(method, paramClasses);
		return m.invoke(obj, params);
	}
	
	/**
	 * Invokes accessible method of an object.
	 * 
	 * @param obj
	 *            object
	 * @param method
	 *            name of the objects method
	 * @param paramClasses
	 *            method parameter types
	 * @param params
	 *            method parameters
	 * @return the object
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	public static Object invoke(Object obj, String method, Class[] paramClasses, Object[] params)
			throws IllegalAccessException, NoSuchMethodException, InvocationTargetException
	{
		Method m = obj.getClass().getMethod(method, paramClasses);
		return m.invoke(obj, params);
	}
	
	/**
	 * Invokes accessible method of an object without specifying parameter
	 * types.
	 * 
	 * @param obj
	 *            object
	 * @param method
	 *            method of an object
	 * @param params
	 *            method parameters
	 * @return the object
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	public static Object invoke(Object obj, String method, Object[] params) throws IllegalAccessException,
			NoSuchMethodException, InvocationTargetException
	{
		Class[] paramClass = getClasses(params);
		return invoke(obj, method, paramClass, params);
	}
	
	/**
	 * Invoke.
	 * 
	 * @param c
	 *            the c
	 * @param obj
	 *            the obj
	 * @param method
	 *            the method
	 * @param params
	 *            the params
	 * @return the object
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	public static Object invoke(Class c, Object obj, String method, Object[] params) throws IllegalAccessException,
			NoSuchMethodException, InvocationTargetException
	{
		Class[] paramClass = getClasses(params);
		return invoke(c, obj, method, paramClass, params);
	}
	
	// ----------------------------------------------------------------
	// invokeDeclared
	
	/**
	 * Invokes any method of a class, even private ones.
	 * 
	 * @param c
	 *            class to examine
	 * @param obj
	 *            object to inspect
	 * @param method
	 *            method to invoke
	 * @param paramClasses
	 *            parameter types
	 * @param params
	 *            parameters
	 * @return the object
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	public static Object invokeDeclared(Class c, Object obj, String method, Class[] paramClasses, Object[] params)
			throws IllegalAccessException, NoSuchMethodException, InvocationTargetException
	{
		final Method m = c.getDeclaredMethod(method, paramClasses);
		m.setAccessible(true);
		return m.invoke(obj, params);
	}
	
	/**
	 * Invokes any method of a class suppressing java access checking.
	 * 
	 * @param obj
	 *            object to inspect
	 * @param method
	 *            method to invoke
	 * @param paramClasses
	 *            parameter types
	 * @param params
	 *            parameters
	 * @return the object
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	public static Object invokeDeclared(Object obj, String method, Class[] paramClasses, Object[] params)
			throws IllegalAccessException, NoSuchMethodException, InvocationTargetException
	{
		Method m = obj.getClass().getDeclaredMethod(method, paramClasses);
		m.setAccessible(true);
		return m.invoke(obj, params);
	}
	
	/**
	 * Invoke declared.
	 * 
	 * @param obj
	 *            the obj
	 * @param method
	 *            the method
	 * @param params
	 *            the params
	 * @return the object
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	public static Object invokeDeclared(Object obj, String method, Object[] params) throws IllegalAccessException,
			NoSuchMethodException, InvocationTargetException
	{
		Class[] paramClass = getClasses(params);
		return invokeDeclared(obj, method, paramClass, params);
	}
	
	/**
	 * Invoke declared.
	 * 
	 * @param c
	 *            the c
	 * @param obj
	 *            the obj
	 * @param method
	 *            the method
	 * @param params
	 *            the params
	 * @return the object
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	public static Object invokeDeclared(Class c, Object obj, String method, Object[] params)
			throws IllegalAccessException, NoSuchMethodException, InvocationTargetException
	{
		Class[] paramClass = getClasses(params);
		return invokeDeclared(c, obj, method, paramClass, params);
	}
	
	// ---------------------------------------------------------------- match
	// classes
	
	/**
	 * Determines if first class match the destination and simulates kind of
	 * <code>instanceof</code>. All subclasses and interface of first class are
	 * examined against second class. Method is not symmetric.
	 * 
	 * @param thisClass
	 *            the this class
	 * @param target
	 *            the target
	 * @return true, if is subclass
	 */
	public static boolean isSubclass(Class thisClass, Class target)
	{
		if (target.isInterface() != false)
		{
			return isInterfaceImpl(thisClass, target);
		}
		for (Class x = thisClass; x != null; x = x.getSuperclass())
		{
			if (x == target)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns <code>true</code> if provided class is interface implementation.
	 * 
	 * @param thisClass
	 *            the this class
	 * @param targetInterface
	 *            the target interface
	 * @return true, if is interface impl
	 */
	public static boolean isInterfaceImpl(Class thisClass, Class targetInterface)
	{
		for (Class x = thisClass; x != null; x = x.getSuperclass())
		{
			Class[] interfaces = x.getInterfaces();
			for (Class i : interfaces)
			{
				if (i == targetInterface)
				{
					return true;
				}
				if (isInterfaceImpl(i, targetInterface))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Dynamic version of <code>instanceof</code>.
	 * 
	 * @param o
	 *            object to match
	 * @param target
	 *            target class
	 * @return <code>true</code> if object is an instance of target class
	 */
	public static boolean isInstanceOf(Object o, Class target)
	{
		return isSubclass(o.getClass(), target);
	}
	
	// ----------------------------------------------------------------
	// accessible methods
	
	/**
	 * Returns array of all methods that are accessible from given class.
	 * 
	 * @param clazz
	 *            the clazz
	 * @return the accessible methods
	 * @see #getAccessibleMethods(Class, Class)
	 */
	public static Method[] getAccessibleMethods(Class clazz)
	{
		return getAccessibleMethods(clazz, Object.class);
	}
	
	/**
	 * Returns array of all methods that are accessible from given class, upto
	 * limit (usually <code>Object.class</code>). Abstract methods are ignored.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param limit
	 *            the limit
	 * @return the accessible methods
	 */
	public static Method[] getAccessibleMethods(Class clazz, Class limit)
	{
		Package topPackage = clazz.getPackage();
		List<Method> methodList = new ArrayList<Method>();
		int topPackageHash = topPackage == null ? 0 : topPackage.hashCode();
		boolean top = true;
		do
		{
			if (clazz == null)
			{
				break;
			}
			Method[] declaredMethods = clazz.getDeclaredMethods();
			for (Method method : declaredMethods)
			{
				if (Modifier.isVolatile(method.getModifiers()))
				{
					continue;
				}
				// if (Modifier.isAbstract(method.getModifiers())) {
				// continue;
				// }
				if (top == true)
				{ // add all top declared methods
					methodList.add(method);
					continue;
				}
				int modifier = method.getModifiers();
				if (Modifier.isPrivate(modifier) == true)
				{
					continue; // ignore super private methods
				}
				if (Modifier.isAbstract(modifier) == true)
				{ // ignore super abstract methods
					continue;
				}
				if (Modifier.isPublic(modifier) == true)
				{
					addMethodIfNotExist(methodList, method); // add super public
																// methods
					continue;
				}
				if (Modifier.isProtected(modifier) == true)
				{
					addMethodIfNotExist(methodList, method); // add super
																// protected
																// methods
					continue;
				}
				// add super default methods from the same package
				Package pckg = method.getDeclaringClass().getPackage();
				int pckgHash = pckg == null ? 0 : pckg.hashCode();
				if (pckgHash == topPackageHash)
				{
					addMethodIfNotExist(methodList, method);
				}
			}
			top = false;
		} while ((clazz = clazz.getSuperclass()) != limit);
		
		Method[] methods = new Method[methodList.size()];
		for (int i = 0; i < methods.length; i++)
		{
			methods[i] = methodList.get(i);
		}
		return methods;
	}
	
	/**
	 * Adds the method if not exist.
	 * 
	 * @param allMethods
	 *            the all methods
	 * @param newMethod
	 *            the new method
	 */
	private static void addMethodIfNotExist(List<Method> allMethods, Method newMethod)
	{
		for (Method m : allMethods)
		{
			if (compareSignatures(m, newMethod) == true)
			{
				return;
			}
		}
		allMethods.add(newMethod);
	}
	
	// ----------------------------------------------------------------
	// accessible fields
	
	/**
	 * Gets the accessible fields.
	 * 
	 * @param clazz
	 *            the clazz
	 * @return the accessible fields
	 */
	public static Field[] getAccessibleFields(Class clazz)
	{
		return getAccessibleFields(clazz, Object.class);
	}
	
	/**
	 * Gets the accessible fields.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param limit
	 *            the limit
	 * @return the accessible fields
	 */
	public static Field[] getAccessibleFields(Class clazz, Class limit)
	{
		Package topPackage = clazz.getPackage();
		List<Field> fieldList = new ArrayList<Field>();
		int topPackageHash = topPackage == null ? 0 : topPackage.hashCode();
		boolean top = true;
		do
		{
			if (clazz == null)
			{
				break;
			}
			Field[] declaredFields = clazz.getDeclaredFields();
			for (Field field : declaredFields)
			{
				if (top == true)
				{ // add all top declared fields
					fieldList.add(field);
					continue;
				}
				int modifier = field.getModifiers();
				if (Modifier.isPrivate(modifier) == true)
				{
					continue; // ignore super private fields
				}
				if (Modifier.isPublic(modifier) == true)
				{
					addFieldIfNotExist(fieldList, field); // add super public
															// methods
					continue;
				}
				if (Modifier.isProtected(modifier) == true)
				{
					addFieldIfNotExist(fieldList, field); // add super protected
															// methods
					continue;
				}
				// add super default methods from the same package
				Package pckg = field.getDeclaringClass().getPackage();
				int pckgHash = pckg == null ? 0 : pckg.hashCode();
				if (pckgHash == topPackageHash)
				{
					addFieldIfNotExist(fieldList, field);
				}
			}
			top = false;
		} while ((clazz = clazz.getSuperclass()) != limit);
		
		Field[] fields = new Field[fieldList.size()];
		for (int i = 0; i < fields.length; i++)
		{
			fields[i] = fieldList.get(i);
		}
		return fields;
	}
	
	/**
	 * Adds the field if not exist.
	 * 
	 * @param allFields
	 *            the all fields
	 * @param newField
	 *            the new field
	 */
	private static void addFieldIfNotExist(List<Field> allFields, Field newField)
	{
		for (Field f : allFields)
		{
			if (compareSignatures(f, newField) == true)
			{
				return;
			}
		}
		allFields.add(newField);
	}
	
	// ----------------------------------------------------------------
	// supported methods
	
	/**
	 * Gets the supported methods.
	 * 
	 * @param clazz
	 *            the clazz
	 * @return the supported methods
	 */
	public static Method[] getSupportedMethods(Class clazz)
	{
		return getSupportedMethods(clazz, Object.class);
	}
	
	/**
	 * Returns a <code>Method</code> array of the methods to which instances of
	 * the specified respond except for those methods defined in the class
	 * specified by limit or any of its superclasses. Note that limit is usually
	 * used to eliminate them methods defined by <code>java.lang.Object</code>.
	 * If limit is <code>null</code> then all methods are returned.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param limit
	 *            the limit
	 * @return the supported methods
	 */
	public static Method[] getSupportedMethods(Class clazz, Class limit)
	{
		ArrayList<Method> supportedMethods = new ArrayList<Method>();
		for (Class c = clazz; c != limit; c = c.getSuperclass())
		{
			Method[] methods = c.getDeclaredMethods();
			for (Method method : methods)
			{
				boolean found = false;
				for (Method supportedMethod : supportedMethods)
				{
					if (compareSignatures(method, supportedMethod))
					{
						found = true;
						break;
					}
				}
				if (found == false)
				{
					supportedMethods.add(method);
				}
			}
		}
		return supportedMethods.toArray(new Method[supportedMethods.size()]);
	}
	
	/**
	 * Gets the supported fields.
	 * 
	 * @param clazz
	 *            the clazz
	 * @return the supported fields
	 */
	public static Field[] getSupportedFields(Class clazz)
	{
		return getSupportedFields(clazz, Object.class);
	}
	
	/**
	 * Gets the supported fields.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param limit
	 *            the limit
	 * @return the supported fields
	 */
	public static Field[] getSupportedFields(Class clazz, Class limit)
	{
		ArrayList<Field> supportedFields = new ArrayList<Field>();
		for (Class c = clazz; c != limit; c = c.getSuperclass())
		{
			Field[] fields = c.getDeclaredFields();
			for (Field field : fields)
			{
				boolean found = false;
				for (Field supportedField : supportedFields)
				{
					if (compareSignatures(field, supportedField))
					{
						found = true;
						break;
					}
				}
				if (found == false)
				{
					supportedFields.add(field);
				}
			}
		}
		return supportedFields.toArray(new Field[supportedFields.size()]);
	}
	
	// ---------------------------------------------------------------- compare
	
	/**
	 * Compares method declarations: signature and return types.
	 * 
	 * @param first
	 *            the first
	 * @param second
	 *            the second
	 * @return true, if successful
	 */
	public static boolean compareDeclarations(Method first, Method second)
	{
		if (first.getReturnType() != second.getReturnType())
		{
			return false;
		}
		return compareSignatures(first, second);
	}
	
	/**
	 * Compares method signatures: names and parameters.
	 * 
	 * @param first
	 *            the first
	 * @param second
	 *            the second
	 * @return true, if successful
	 */
	public static boolean compareSignatures(Method first, Method second)
	{
		if (first.getName().equals(second.getName()) == false)
		{
			return false;
		}
		return compareParameters(first.getParameterTypes(), second.getParameterTypes());
	}
	
	/**
	 * Compares constructor signatures: names and parameters.
	 * 
	 * @param first
	 *            the first
	 * @param second
	 *            the second
	 * @return true, if successful
	 */
	public static boolean compareSignatures(Constructor first, Constructor second)
	{
		if (first.getName().equals(second.getName()) == false)
		{
			return false;
		}
		return compareParameters(first.getParameterTypes(), second.getParameterTypes());
	}
	
	/**
	 * Compare signatures.
	 * 
	 * @param first
	 *            the first
	 * @param second
	 *            the second
	 * @return true, if successful
	 */
	public static boolean compareSignatures(Field first, Field second)
	{
		return first.getName().equals(second.getName());
	}
	
	/**
	 * Compares method or ctor parameters.
	 * 
	 * @param first
	 *            the first
	 * @param second
	 *            the second
	 * @return true, if successful
	 */
	public static boolean compareParameters(Class[] first, Class[] second)
	{
		if (first.length != second.length)
		{
			return false;
		}
		for (int i = 0; i < first.length; i++)
		{
			if (first[i] != second[i])
			{
				return false;
			}
		}
		return true;
	}
	
	// ---------------------------------------------------------------- force
	
	/**
	 * Suppress access check against a reflection object. SecurityException is
	 * silently ignored. Checks first if the object is already accessible.
	 * 
	 * @param accObject
	 *            the acc object
	 */
	public static void forceAccess(AccessibleObject accObject)
	{
		if (accObject.isAccessible() == true)
		{
			return;
		}
		try
		{
			accObject.setAccessible(true);
		}
		catch (SecurityException sex)
		{
			// ignore
		}
	}
	
	// ---------------------------------------------------------------- is
	// public
	
	/**
	 * Returns <code>true</code> if class member is public.
	 * 
	 * @param member
	 *            the member
	 * @return true, if is public
	 */
	public static boolean isPublic(Member member)
	{
		return Modifier.isPublic(member.getModifiers());
	}
	
	/**
	 * Returns <code>true</code> if class member is public and if its declaring
	 * class is also public.
	 * 
	 * @param member
	 *            the member
	 * @return true, if is public public
	 */
	public static boolean isPublicPublic(Member member)
	{
		if (Modifier.isPublic(member.getModifiers()) == true)
		{
			if (Modifier.isPublic(member.getDeclaringClass().getModifiers()))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns <code>true</code> if class is public.
	 * 
	 * @param c
	 *            the c
	 * @return true, if is public
	 */
	public static boolean isPublic(Class c)
	{
		return Modifier.isPublic(c.getModifiers());
	}
	
	// ---------------------------------------------------------------- create
	
	/**
	 * Creates new instances including for common mutable classes that do not
	 * have a default constructor. more user-friendly. It examines if class is a
	 * map, list, String, Character, Boolean or a Number. Immutable instances
	 * are cached and not created again. Arrays are also created with no
	 * elements. Note that this bunch of ifs is faster then a hashmap.
	 * 
	 * @param type
	 *            the type
	 * @return the object
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InstantiationException
	 *             the instantiation exception
	 */
	public static Object newInstance(Class type) throws IllegalAccessException, InstantiationException
	{
		if (type.isPrimitive())
		{
			if (type == int.class)
			{
				return Integer.valueOf(0);
			}
			if (type == long.class)
			{
				return Long.valueOf(0);
			}
			if (type == boolean.class)
			{
				return Boolean.FALSE;
			}
			if (type == float.class)
			{
				return Float.valueOf(0);
			}
			if (type == double.class)
			{
				return Double.valueOf(0);
			}
			if (type == byte.class)
			{
				return Byte.valueOf((byte) 0);
			}
			if (type == short.class)
			{
				return Short.valueOf((short) 0);
			}
			if (type == char.class)
			{
				return Character.valueOf((char) 0);
			}
			throw new IllegalArgumentException("Invalid primitive type: " + type);
		}
		if (type == Integer.class)
		{
			return Integer.valueOf(0);
		}
		if (type == String.class)
		{
			return StringPool.EMPTY;
		}
		if (type == Long.class)
		{
			return Long.valueOf(0);
		}
		if (type == Boolean.class)
		{
			return Boolean.FALSE;
		}
		if (type == Float.class)
		{
			Float.valueOf(0);
		}
		if (type == Double.class)
		{
			Double.valueOf(0);
		}
		
		if (type == Map.class)
		{
			return new HashMap();
		}
		if (type == List.class)
		{
			return new ArrayList();
		}
		if (type == Set.class)
		{
			return new LinkedHashSet();
		}
		if (type == Collection.class)
		{
			return new ArrayList();
		}
		
		if (type == Byte.class)
		{
			return Byte.valueOf((byte) 0);
		}
		if (type == Short.class)
		{
			return Short.valueOf((short) 0);
		}
		if (type == Character.class)
		{
			return Character.valueOf((char) 0);
		}
		
		if (type.isEnum() == true)
		{
			return type.getEnumConstants()[0];
		}
		
		if (type.isArray() == true)
		{
			return Array.newInstance(type.getComponentType(), 0);
		}
		
		return type.newInstance();
	}
	
	// ---------------------------------------------------------------- misc
	
	/**
	 * Returns <code>true</code> if the first member is accessible from second
	 * one.
	 * 
	 * @param member1
	 *            the member1
	 * @param member2
	 *            the member2
	 * @return true, if is assignable from
	 */
	public static boolean isAssignableFrom(Member member1, Member member2)
	{
		return member1.getDeclaringClass().isAssignableFrom(member2.getDeclaringClass());
	}
	
	/**
	 * Returns all superclasses.
	 * 
	 * @param type
	 *            the type
	 * @return the superclasses
	 */
	public static Class[] getSuperclasses(Class type)
	{
		int i = 0;
		for (Class x = type.getSuperclass(); x != null; x = x.getSuperclass())
		{
			i++;
		}
		Class[] result = new Class[i];
		i = 0;
		for (Class x = type.getSuperclass(); x != null; x = x.getSuperclass())
		{
			result[i] = x;
			i++;
		}
		return result;
	}
	
	/**
	 * Returns <code>true</code> if method is user defined and not defined in
	 * <code>Object</code> class.
	 * 
	 * @param method
	 *            the method
	 * @return true, if is user defined method
	 */
	public static boolean isUserDefinedMethod(final Method method)
	{
		return method.getDeclaringClass() != Object.class;
	}
	
	/**
	 * Returns <code>true</code> if method defined in <code>Object</code> class.
	 * 
	 * @param method
	 *            the method
	 * @return true, if is object method
	 */
	public static boolean isObjectMethod(final Method method)
	{
		return method.getDeclaringClass() == Object.class;
	}
	
	/**
	 * Returns <code>true</code> if method is a bean property.
	 * 
	 * @param method
	 *            the method
	 * @return true, if is bean property
	 */
	public static boolean isBeanProperty(Method method)
	{
		if (isObjectMethod(method))
		{
			return false;
		}
		String methodName = method.getName();
		Class returnType = method.getReturnType();
		Class[] paramTypes = method.getParameterTypes();
		if (methodName.startsWith(METHOD_GET_PREFIX))
		{ // getter method must starts with 'get' and it is not getClass()
			if ((returnType != null) && (paramTypes.length == 0))
			{ // getter must have a return type and no arguments
				return true;
			}
		}
		else if (methodName.startsWith(METHOD_IS_PREFIX))
		{ // ister must starts with 'is'
			if ((returnType != null) && (paramTypes.length == 0))
			{ // ister must have return type and no arguments
				return true;
			}
		}
		else if (methodName.startsWith(METHOD_SET_PREFIX))
		{ // setter must start with a 'set'
			if (paramTypes.length == 1)
			{ // setter must have just one argument
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns <code>true</code> if method is bean getter.
	 * 
	 * @param method
	 *            the method
	 * @return true, if is bean property getter
	 */
	public static boolean isBeanPropertyGetter(Method method)
	{
		return getBeanPropertyGetterPrefixLength(method) != 0;
	}
	
	/**
	 * Gets the bean property getter prefix length.
	 * 
	 * @param method
	 *            the method
	 * @return the bean property getter prefix length
	 */
	private static int getBeanPropertyGetterPrefixLength(Method method)
	{
		if (isObjectMethod(method))
		{
			return 0;
		}
		String methodName = method.getName();
		Class returnType = method.getReturnType();
		Class[] paramTypes = method.getParameterTypes();
		if (methodName.startsWith(METHOD_GET_PREFIX))
		{ // getter method must starts with 'get' and it is not getClass()
			if ((returnType != null) && (paramTypes.length == 0))
			{ // getter must have a return type and no arguments
				return 3;
			}
		}
		else if (methodName.startsWith(METHOD_IS_PREFIX))
		{ // ister must starts with 'is'
			if ((returnType != null) && (paramTypes.length == 0))
			{ // ister must have return type and no arguments
				return 2;
			}
		}
		return 0;
	}
	
	/**
	 * Returns beans property getter name or <code>null</code> if method is not
	 * a real getter.
	 * 
	 * @param method
	 *            the method
	 * @return the bean property getter name
	 */
	public static String getBeanPropertyGetterName(Method method)
	{
		int prefixLength = getBeanPropertyGetterPrefixLength(method);
		if (prefixLength == 0)
		{
			return null;
		}
		String methodName = method.getName().substring(prefixLength);
		return Introspector.decapitalize(methodName);
	}
	
	/**
	 * Returns <code>true</code> if method is bean setter.
	 * 
	 * @param method
	 *            the method
	 * @return true, if is bean property setter
	 */
	public static boolean isBeanPropertySetter(Method method)
	{
		return getBeanPropertySetterPrefixLength(method) != 0;
	}
	
	/**
	 * Gets the bean property setter prefix length.
	 * 
	 * @param method
	 *            the method
	 * @return the bean property setter prefix length
	 */
	private static int getBeanPropertySetterPrefixLength(Method method)
	{
		if (isObjectMethod(method))
		{
			return 0;
		}
		String methodName = method.getName();
		Class[] paramTypes = method.getParameterTypes();
		if (methodName.startsWith(METHOD_SET_PREFIX))
		{ // setter must start with a 'set'
			if (paramTypes.length == 1)
			{ // setter must have just one argument
				return 3;
			}
		}
		return 0;
	}
	
	/**
	 * Returns beans property setter name or <code>null</code> if method is not
	 * a real setter.
	 * 
	 * @param method
	 *            the method
	 * @return the bean property setter name
	 */
	public static String getBeanPropertySetterName(Method method)
	{
		int prefixLength = getBeanPropertySetterPrefixLength(method);
		if (prefixLength == 0)
		{
			return null;
		}
		String methodName = method.getName().substring(prefixLength);
		return Introspector.decapitalize(methodName);
	}
	
	// ---------------------------------------------------------------- generics
	
	/**
	 * Returns component type of the given <code>type</code>. For
	 * <code>ParameterizedType</code> it returns the last type in array.
	 * 
	 * @param type
	 *            the type
	 * @return the component type
	 */
	public static Class getComponentType(Type type)
	{
		return getComponentType(type, -1);
	}
	
	/**
	 * Returns the component type of the given <code>type</code>.<br>
	 * For example the following types all have the component-type MyClass:
	 * <ul>
	 * <li>MyClass[]</li>
	 * <li>List&lt;MyClass&gt;</li>
	 * <li>Foo&lt;? extends MyClass&gt;</li>
	 * <li>Bar&lt;? super MyClass&gt;</li>
	 * <li>&lt;T extends MyClass&gt; T[]</li>
	 * </ul>
	 * 
	 * @param type
	 *            is the type where to get the component type from.
	 * @param index
	 *            the index
	 * @return the component type of the given <code>type</code> or
	 *         <code>null</code> if the given <code>type</code> does NOT have a
	 *         single (component) type.
	 */
	public static Class getComponentType(Type type, int index)
	{
		if (type instanceof Class)
		{
			Class clazz = (Class) type;
			if (clazz.isArray())
			{
				return clazz.getComponentType();
			}
		}
		else if (type instanceof ParameterizedType)
		{
			ParameterizedType pt = (ParameterizedType) type;
			Type[] generics = pt.getActualTypeArguments();
			if (index < 0)
			{
				index = generics.length + index;
			}
			if (index < generics.length)
			{
				return toClass(generics[index]);
			}
		}
		else if (type instanceof GenericArrayType)
		{
			GenericArrayType gat = (GenericArrayType) type;
			return toClass(gat.getGenericComponentType());
		}
		return null;
	}
	
	/**
	 * Gets the generic supertype.
	 * 
	 * @param type
	 *            the type
	 * @param index
	 *            the index
	 * @return the generic supertype
	 */
	public static Class getGenericSupertype(Class type, int index)
	{
		return getComponentType(type.getGenericSuperclass(), index);
	}
	
	/**
	 * Gets the generic supertype.
	 * 
	 * @param type
	 *            the type
	 * @return the generic supertype
	 */
	public static Class getGenericSupertype(Class type)
	{
		return getComponentType(type.getGenericSuperclass());
	}
	
	/**
	 * Returns {@link Class} for the given <code>type</code>.<br>
	 * Examples: <br>
	 * <table border="1" summary="">
	 * <tr>
	 * <th><code>type</code></th>
	 * <th><code>getSimpleType(type)</code></th>
	 * </tr>
	 * <tr>
	 * <td><code>String</code></td>
	 * <td><code>String</code></td>
	 * <tr>
	 * <td><code>List&lt;String&gt;</code></td>
	 * <td><code>List</code></td>
	 * <tr>
	 * <td><code>&lt;T extends MyClass&gt; T[]</code></td>
	 * <td><code>MyClass[]</code></td>
	 * </table>
	 * 
	 * @param type
	 *            is the type to convert.
	 * @return the closest class representing the given <code>type</code>.
	 */
	public static Class toClass(Type type)
	{
		if (type instanceof Class)
		{
			return (Class) type;
		}
		if (type instanceof ParameterizedType)
		{
			ParameterizedType pt = (ParameterizedType) type;
			return toClass(pt.getRawType());
		}
		if (type instanceof WildcardType)
		{
			WildcardType wt = (WildcardType) type;
			Type[] lower = wt.getLowerBounds();
			if (lower.length == 1)
			{
				return toClass(lower[0]);
			}
			Type[] upper = wt.getUpperBounds();
			if (upper.length == 1)
			{
				return toClass(upper[0]);
			}
		}
		else if (type instanceof GenericArrayType)
		{
			GenericArrayType gat = (GenericArrayType) type;
			Class componentType = toClass(gat.getGenericComponentType());
			// this is sort of stupid but there seems no other way...
			return Array.newInstance(componentType, 0).getClass();
		}
		else if (type instanceof TypeVariable)
		{
			TypeVariable tv = (TypeVariable) type;
			Type[] bounds = tv.getBounds();
			if (bounds.length == 1)
			{
				return toClass(bounds[0]);
			}
		}
		return null;
	}
	
	// ----------------------------------------------------------------
	// annotations
	
	/**
	 * Reads annotation value. Returns <code>null</code> on error.
	 * 
	 * @param annotation
	 *            the annotation
	 * @param name
	 *            the name
	 * @return the object
	 */
	public static Object readAnnotationValue(Annotation annotation, String name)
	{
		try
		{
			Method method = annotation.annotationType().getDeclaredMethod(name);
			return method.invoke(annotation);
		}
		catch (Exception ignore)
		{
			return null;
		}
	}
	
	/**
	 * Checks the method for the given type of annotation, whether it contains
	 * it.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param type
	 *            the type
	 * @param methodName
	 *            the method name
	 * @param equalsIgnoreCase
	 *            the equals ignore case
	 * @param annotation
	 *            the annotation
	 * @return true, if successful
	 */
	public static <T> boolean has(T type, String methodName, boolean equalsIgnoreCase, Class<? extends Annotation> annotation)
	{
		try
		{
			final Method method = getDeclaredMethod(type, methodName, equalsIgnoreCase);
			if (isNotNull(method))
			{
				final Annotation[] annotations = method.getDeclaredAnnotations();
				for (Annotation annotation2 : annotations)
				{
					if (annotation2.annotationType().isAssignableFrom(annotation))
					{
						return true;
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.debug("Error while getting the method of type: " + annotation + ", from: " + type, e);
		}
		return false;
	}
	
	/**
	 * Gets the methods that has.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param type
	 *            the type
	 * @param annotation
	 *            the annotation
	 * @return the methods that has
	 */
	public static <T> List<String> getMethodsThatHas(T type, Class<? extends Annotation> annotation)
	{
		final List<String> list = new ArrayList<String>();
		try
		{
			final Method[] methods = type.getClass().getMethods();
			for (int i = 0; i < methods.length; i++)
			{
				final Method method = methods[i];
				final Annotation[] annotations = method.getDeclaredAnnotations();
				for (Annotation annotation2 : annotations)
				{
					if (annotation2.annotationType().isAssignableFrom(annotation))
					{
						list.add(method.getName());
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.debug("Error while getting the list of methods: " + type, e);
		}
		return list;
	}
	
	/**
	 * Finds the method based on the name provided and executes the method by
	 * passing the arguments.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param <V>
	 *            the value type
	 * @param type
	 *            the object
	 * @param methodName
	 *            the method Name
	 * @param equalsIgnoreCase
	 *            the equals ignore case for method search
	 * @param args
	 *            the args
	 * @return the method value if it returns proper value like Integer, Void,
	 *         Float, null if it is not executed success fully.
	 */
	public static <T, V> V executeMethod(T type, String methodName, boolean equalsIgnoreCase, Object... args)
	{
		try
		{
			final Method method = getDeclaredMethod(type, methodName, equalsIgnoreCase);
			method.setAccessible(true);
			if (method.getReturnType() == Void.class || method.getReturnType().toString().equals(VOID))
			{
				method.invoke(type, args);
				return (V) Void.getInstance();
			}
			else
			{
				return (V) method.invoke(type, args);
			}
		}
		catch (Exception e)
		{
			logger.debug("Error while getting the field value of: " + methodName, e);
		}
		return null;
	}
	
	/**
	 * Finds the method based on the name provided and executes the method by
	 * passing the arguments.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param <V>
	 *            the value type
	 * @param type
	 *            the object
	 * @param methodName
	 *            the method Name
	 * @param equalsIgnoreCase
	 *            the equals ignore case for method search
	 * @param args
	 *            the args
	 * @return the method value if it returns proper value like Integer, Void,
	 *         Float, null if it is not executed success fully.
	 */
	public static <T, V> V executeMethod(T type, String methodName, boolean equalsIgnoreCase, String... args)
	{
		try
		{
			final Method method = getDeclaredMethod(type, methodName, equalsIgnoreCase);
			method.setAccessible(true);
			final Class<?>[] paramTypes = method.getParameterTypes();
			final List<Object> newArgs = new ArrayList<Object>(args.length);
			for (int j = 0; j < args.length; j++)
			{
				newArgs.add(to(args[j], paramTypes[j]));
			}
			if (method.getReturnType() == Void.class || method.getReturnType().toString().equals(VOID))
			{
				method.invoke(type, newArgs.toArray());
				return (V) Void.getInstance();
			}
			else
			{
				return (V) method.invoke(type, newArgs.toArray());
			}
		}
		catch (Exception e)
		{
			logger.debug("Error while getting the field value of: " + methodName, e);
		}
		return null;
	}
	
	/**
	 * Gets the declared method.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param type
	 *            the type
	 * @param methodName
	 *            the method name
	 * @param equalsIgnoreCase
	 *            the equals ignore case
	 * @return the declared method
	 */
	public static <T> Method getDeclaredMethod(T type, String methodName, boolean equalsIgnoreCase)
	{
		final Method[] methods = type.getClass().getMethods();
		for (int i = 0; i < methods.length; i++)
		{
			final Method method = methods[i];
			final String name = method.getName();
			final boolean isMethodAvailable = equalsIgnoreCase ? name.equalsIgnoreCase(methodName) : name.equals(methodName);
			if (isMethodAvailable)
			{
				return method;
			}
		}
		return null;
	}
	
	/**
	 * Gets the field value.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param <V>
	 *            the value type
	 * @param type
	 *            the object
	 * @param name
	 *            the name
	 * @param equalsIgnoreCase
	 *            the equals ignore case
	 * @return the field value
	 */
	public static <T, V> V getFieldValue(T type, String name, boolean equalsIgnoreCase)
	{
		try
		{
			final Field field = getDeclaredField(type, name, equalsIgnoreCase);
			field.setAccessible(true);
			return (V) field.get(type);
		}
		catch (Exception e)
		{
			logger.debug("Error while setting the field value of: " + name, e);
		}
		return null;
	}
	
	/**
	 * Sets the field value.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param <V>
	 *            the value type
	 * @param type
	 *            the object
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public static <T, V> void setFieldValue(T type, String name, V value)
	{
		try
		{
			final Field field = getDeclaredField(type, name, false);
			field.setAccessible(true);
			field.set(type, value);
		}
		catch (Exception e)
		{
			logger.debug("Error while setting the field value of: " + name, e);
		}
	}
	
	/**
	 * Sets the field value by converting the string type of value to the
	 * required type of the field.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param type
	 *            the object
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return true, if successful
	 */
	public static <T> boolean setFieldValue(T type, String name, String value)
	{
		try
		{
			final Field field = getDeclaredField(type, name, false);
			field.setAccessible(true);
			field.set(type, to(value, field.getType()));
			return true;
		}
		catch (Exception e)
		{
			logger.debug("Error while setting the field value of: " + name, e);
		}
		return false;
	}
	
	/**
	 * Gets the declared field.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param type
	 *            the object
	 * @param name
	 *            the name
	 * @param equalsIgnoreCase
	 *            the equals ignore case
	 * @return the declared field
	 */
	public static <T> Field getDeclaredField(T type, String name, boolean equalsIgnoreCase)
	{
		try
		{
			final Field[] declaredFields = type.getClass().getDeclaredFields();
			for (Field field : declaredFields)
			{
				if (equalsIgnoreCase)
				{
					if (field.getName().equalsIgnoreCase(name))
					{
						return field;
					}
				}
				else
				{
					if (field.getName().equals(name))
					{
						return field;
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.debug("Error while getting the field value of: " + name, e);
		}
		return null;
	}
	
	/**
	 * Gets the declared field name as String.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param type
	 *            the object
	 * @param name
	 *            the name
	 * @param equalsIgnoreCase
	 *            the equals ignore case
	 * @return the declared field
	 */
	public static <T> String getFieldName(T type, String name, boolean equalsIgnoreCase)
	{
		try
		{
			final Field[] declaredFields = type.getClass().getDeclaredFields();
			for (Field field : declaredFields)
			{
				if (equalsIgnoreCase)
				{
					if (field.getName().equalsIgnoreCase(name))
					{
						return field.getName();
					}
				}
				else
				{
					if (field.getName().equals(name))
					{
						return field.getName();
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.debug("Error while getting the field value of: " + name, e);
		}
		return null;
	}
	
	/**
	 * Gets the declared field name as String.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param type
	 *            the object
	 * @param name
	 *            the name
	 * @param equalsIgnoreCase
	 *            the equals ignore case
	 * @return the declared field
	 */
	public static <T> String getFieldName(Class<T> type, String name, boolean equalsIgnoreCase)
	{
		try
		{
			final Field[] declaredFields = type.getDeclaredFields();
			for (Field field : declaredFields)
			{
				if (equalsIgnoreCase)
				{
					if (field.getName().equalsIgnoreCase(name))
					{
						return field.getName();
					}
				}
				else
				{
					if (field.getName().equals(name))
					{
						return field.getName();
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.debug("Error while getting the field value of: " + name, e);
		}
		return null;
	}
}