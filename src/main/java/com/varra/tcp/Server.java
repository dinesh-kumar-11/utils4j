/*
 * csc-request-dispatcher - TCPServer.java, Aug 30, 2013 9:33:51 AM
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
package com.varra.tcp;

import java.io.*;
import java.net.*;

/**
 * 
 * TODO Description go here.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 1.0
 * 
 */
public class Server
{
	
	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("resource")
	public static void main(String args[]) throws Exception
	{
		if (args.length > 0)
		{
			String clientSentence;
			String capitalizedSentence;
			final ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(args[0]));
			System.out.println("Welcome to Telnet serevr. This is echo server, for any issues, please contact Rajakrishna Reddy.");
			final Socket connectionSocket = welcomeSocket.accept();
			while (true)
			{
				final BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				final DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				clientSentence = inFromClient.readLine();
				//System.out.println("Client: " + clientSentence);
				if (clientSentence != null)
				{
					capitalizedSentence = clientSentence.toUpperCase() + '\n';
					outToClient.writeBytes("Server: " + capitalizedSentence);
					System.out.println("Server: " + capitalizedSentence);
				}
			}
		}
		else
		{
			System.out.println("Usage: Server port");
			System.exit(0);
		}
	}
}