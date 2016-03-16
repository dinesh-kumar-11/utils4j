/*
 * utils4j - Client.java, Sep 4, 2014 4:43:50 PM
 * 
 * Copyright 2014 Trimble Ltd, Inc. All rights reserved.
 * Varra proprietary/confidential. Use is subject to license terms.
 */
package com.varra.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;
import com.varra.log.Logger;

/**
 * TODO Description go here.
 * 
 * @author <a href="mailto:Rajakrishna_Reddy@Trimble.com">Rajakrishna V.
 *         Reddy</a>
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class Client
{
	
	/** The Constant DEFAULT_CONNECTION_TIMEOUT. */
	private static final int DEFAULT_CONNECTION_TIMEOUT = 0;
	
	
	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args)
	{
		final Logger logger = Logger.getLogger(Client.class);
		final String host = args[0];
		final int port = Integer.parseInt(args[1]);
		logger.info("Going to test the port opening check on: " + host + " for port: " + port+", Opened: "+isPortOpened(host, port));
	}

	/**
	 * Checks if port is opened.
	 * 
	 * @param host
	 *            the host
	 * @param port
	 *            the port
	 * @return the boolean
	 */
	public static Boolean isPortOpened(final String host, final int port)
	{
		try
		{
			final Socket socket = new Socket();
			final SocketAddress endpoint = new InetSocketAddress(host, port);
			socket.connect(endpoint, DEFAULT_CONNECTION_TIMEOUT);
			socket.close();
			return Boolean.TRUE;
		}
		catch (IOException e)
		{
			Logger.getLogger(Client.class).error("Error while trying to open connection.", e);
		}
		return Boolean.FALSE;
	}
}
