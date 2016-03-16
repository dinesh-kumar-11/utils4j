/*
 * utils4j - JMXUtility.java, Oct 3, 2011 4:27:38 PM
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
package com.varra.jmx.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.varra.log.Logger;

import com.varra.jmx.exception.JMXWrapperException;
import com.varra.util.RapidFastMap;

/**
 * The Class JMXUtility.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 */
public class JMXUtility
{
	
	/** The logger. */
	final private Logger logger;
	
	/** The host. */
	private String host;
	
	/** The port. */
	private int port;
	
	/** The url. */
	private String url;
	
	/** The userName. */
	private String username = "";
	
	/** The password. */
	private String password = "";
	
	/** The connector. */
	public JMXConnector connector;
	
	/** The connection. */
	private MBeanServerConnection connection;
	
	/** The server restarted. */
	private boolean serverRestarted = true;
	
	/**
	 * Instantiates a new jMX utility.
	 * 
	 * @param host
	 *            the host
	 * @param port
	 *            the port
	 */
	public JMXUtility(String host, int port)
	{
		this.host = host;
		this.port = port;
		this.url = "service:jmx:rmi:///jndi/rmi://" + this.host + ":" + this.port + "/jmxrmi";
		
		logger = Logger.getLogger(this.getClass().getName());
		try
		{
			loadServer();
		}
		catch (JMXWrapperException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the uRL.
	 * 
	 * @return the uRL
	 */
	public String getURL()
	{
		return url;
	}
	
	/**
	 * Gets the details.
	 * 
	 * @param mBeanStrName
	 *            the m bean str name
	 * @return the details
	 * @throws JMXWrapperException
	 *             the jMX wrapper exception
	 */
	public RapidFastMap<String, String> getDetails(String mBeanStrName) throws JMXWrapperException
	{
		completePreRequisites(mBeanStrName);
		logger.info("Getting the details for " + mBeanStrName);
		return showAllAttributes(mBeanStrName);
	}
	
	/**
	 * Completes the pre requisites. Includes <br>
	 * <li>checks the applicationName provided is an valid application or not.
	 * <li>Checks the server status, whether it got rebooted by anyone in
	 * between, if so just get the new instance of server.<br>
	 * 
	 * @param mBeanName
	 *            the application name
	 * @throws JMXWrapperException
	 *             the jMX wrapper exception
	 */
	private void completePreRequisites(String mBeanName) throws JMXWrapperException
	{
		if (mBeanName != null && mBeanName.trim().length() > 0)
		{
			if (serverRestarted)
			{
				loadServer();
				serverRestarted = false;
			}
		}
		else
			throw new JMXWrapperException("MBean Name provided is not found. " + mBeanName);
	}
	
	/**
	 * Show all attributes.
	 * 
	 * @param mBeanStrName
	 *            the m bean str name
	 * @return the rapid fast map
	 * @throws JMXWrapperException
	 *             the jMX wrapper exception
	 */
	
	/**
	 * Show all attributes.
	 * 
	 * @param mBeanStrName
	 *            the m bean str name
	 * @return the rapid fast map
	 * @throws JMXWrapperException
	 *             the jMX wrapper exception
	 */
	private RapidFastMap<String, String> showAllAttributes(final String mBeanStrName) throws JMXWrapperException
	{
		logger.info("Attributes supported by this: " + mBeanStrName);
		
		final RapidFastMap<String, String> map = new RapidFastMap<String, String>();
		try
		{
			final ObjectName name = new ObjectName(mBeanStrName);
			final MBeanInfo mBeanInfo = getConnection().getMBeanInfo(name);
			final MBeanAttributeInfo[] attributes = mBeanInfo.getAttributes();
			
			for (final MBeanAttributeInfo mBeanAttributeInfo : attributes)
			{
				try
				{
					final Object attributeValue = getConnection().getAttribute(name, mBeanAttributeInfo.getName());
					map.put(mBeanAttributeInfo.getName(), attributeValue.toString());
				}
				catch (Exception e)
				{
					logger.error("Got error while trying to get the value of: " + mBeanAttributeInfo.getName()
							+ ", cause: " + e.getMessage());
				}
			}
			
		}
		catch (MalformedObjectNameException e)
		{
			throw new JMXWrapperException("Got " + e.getMessage() + " error while trying to get the details of: "
					+ mBeanStrName, e);
		}
		catch (NullPointerException e)
		{
			throw new JMXWrapperException("Got " + e.getMessage() + " error while trying to get the details of: "
					+ mBeanStrName, e);
		}
		catch (InstanceNotFoundException e)
		{
			throw new JMXWrapperException("Got " + e.getMessage() + " error while trying to get the details of: "
					+ mBeanStrName, e);
		}
		catch (IntrospectionException e)
		{
			throw new JMXWrapperException("Got " + e.getMessage() + " error while trying to get the details of: "
					+ mBeanStrName, e);
		}
		catch (ReflectionException e)
		{
			throw new JMXWrapperException("Got " + e.getMessage() + " error while trying to get the details of: "
					+ mBeanStrName, e);
		}
		catch (IOException e)
		{
			throw new JMXWrapperException("Got " + e.getMessage() + " error while trying to get the details of: "
					+ mBeanStrName, e);
		}
		
		logger.info("Attributes: " + map);
		return map;
	}
	
	/**
	 * Loads the server.
	 * 
	 * @throws JMXWrapperException
	 *             the jMX wrapper exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void loadServer() throws JMXWrapperException
	{
		logger.info("Restarted the server.. !!!, Hence getting the new instance.");
		try
		{
			final Map env = new LinkedHashMap();
			final String[] credentials = new String[] { username, password };
			env.put(JMXConnector.CREDENTIALS, credentials);
			
			connector = JMXConnectorFactory.newJMXConnector(new JMXServiceURL(getURL()), env);
			connector.connect();
			connection = connector.getMBeanServerConnection();
		}
		catch (final MalformedURLException e)
		{
			throw new JMXWrapperException("there is no provider for the protocol in: " + getURL(), e);
		}
		catch (final IOException e)
		{
			throw new JMXWrapperException("Connection exception while connecting to " + getURL(), e);
		}
	}
	
	/**
	 * Gets the {@link MBeanServerConnection}.
	 * 
	 * @return the rMI adaptor
	 */
	public MBeanServerConnection getConnection()
	{
		return connection;
	}
	
	/**
	 * Invoke.
	 * 
	 * @param objName
	 *            the obj name
	 * @param methodName
	 *            the method name
	 * @param args
	 *            the args
	 * @param signature
	 *            the signature
	 * @return the object
	 * @throws InstanceNotFoundException
	 *             the instance not found exception
	 * @throws MBeanException
	 *             the m bean exception
	 * @throws ReflectionException
	 *             the reflection exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Object invoke(ObjectName objName, String methodName, Object[] args, String[] signature)
			throws InstanceNotFoundException, MBeanException, ReflectionException, IOException
	{
		return getConnection().invoke(objName, methodName, args, signature);
	}
	
	/**
	 * Reload.
	 * 
	 * @param appName
	 *            the app name
	 * @throws InstanceNotFoundException
	 *             the instance not found exception
	 * @throws MalformedObjectNameException
	 *             the malformed object name exception
	 * @throws MBeanException
	 *             the m bean exception
	 * @throws ReflectionException
	 *             the reflection exception
	 * @throws NullPointerException
	 *             the null pointer exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void reload(String appName) throws InstanceNotFoundException, MalformedObjectNameException, MBeanException,
			ReflectionException, NullPointerException, IOException
	{
		final String beanName = "jboss.web:j2eeType=WebModule,name=//localhost/" + appName
				+ ",J2EEApplication=none,J2EEServer=none";
		invoke(new ObjectName(beanName), "reload", null, null);
	}
	
	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws JMXWrapperException
	 *             the jMX wrapper exception
	 * @throws InstanceNotFoundException
	 *             the instance not found exception
	 * @throws MalformedObjectNameException
	 *             the malformed object name exception
	 * @throws MBeanException
	 *             the m bean exception
	 * @throws ReflectionException
	 *             the reflection exception
	 * @throws NullPointerException
	 *             the null pointer exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws JMXWrapperException, InstanceNotFoundException,
			MalformedObjectNameException, MBeanException, ReflectionException, NullPointerException, IOException
	{
		JMXUtility utility = new JMXUtility("172.25.200.120", 8007);
		utility.reload("axis1");
		// System.out.println(utility.getDetails("jboss.system:type=Server"));
		// System.out.println(utility.getDetails("jboss.lang:type=Memory"));
	}
	
}