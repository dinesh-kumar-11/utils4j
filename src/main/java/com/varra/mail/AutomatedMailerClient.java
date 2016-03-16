/*
 * utils4j - AutomatedMailerClient.java, Sep 29, 2011 12:25:23 PM
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

package com.varra.mail;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.varra.log.Logger;

/**
 * The Class AutomatedMailSenderClient.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 */
public class AutomatedMailerClient
{
	
	/** The logger. */
	private final Logger logger;
	
	/**
	 * Instantiates a new automated mail sender client.
	 */
	public AutomatedMailerClient()
	{
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	/**
	 * Method for connecting to the Automated Mailer Application.
	 * 
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 * @param cc
	 *            the cc
	 * @param bcc
	 *            the bcc
	 * @param mailType
	 *            the mail type
	 * @param subject
	 *            the subject
	 * @param mailContent
	 *            the mail content
	 * @param targetUrl
	 *            the target url
	 */
	private void makeSOAPCall(final String from, final String to, final String cc, final String bcc, final String mailType, final String subject,
			final String mailContent, final String targetUrl)
	{
		try
		{
			final URL url = new URL(targetUrl);
			final URLConnection conn = url.openConnection();
			
			// Set connection parameters.
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-type", mailType);
			final String soapXML = constructSOAPXML(from, to, cc, bcc, subject, mailContent);
			final DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			// Write out the bytes of the content string to the stream.
			out.writeBytes(soapXML);
			out.flush();
			out.close();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String temp = null;
			final StringBuilder response = new StringBuilder();
			while ((temp = reader.readLine()) != null)
			{
				response.append(temp).append("\n");
			}
			reader.close();
			logger.info("Server response:" + response);
		}
		catch (MalformedURLException ex)
		{
			logger.error("Mailer URL incorrect", ex);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			logger.error("IO Error - Unable to send mail", e);
		}
		
	}
	
	/**
	 * constructSOAPXML - Method to construct the Mailer Package.
	 * 
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 * @param cc
	 *            the cc
	 * @param bcc
	 *            the bcc
	 * @param subject
	 *            the subject
	 * @param mailContent
	 *            the mail content
	 * @return the string
	 */
	private String constructSOAPXML(final String from, final String to, final String cc, final String bcc, final String subject, final String mailContent)
	{
		final String soapEnvelopeStart = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.email.com/\"><soapenv:Header/><soapenv:Body><ser:sendHTMLMail><info><info>";
		final String soapEnvelopeEnd = "</info></info></ser:sendHTMLMail></soapenv:Body></soapenv:Envelope>";
		final String soapEmailFrom = "<entry><key>FROM</key><value>" + from + "</value></entry>";
		final String soapEmailTo = "<entry><key>TO</key><value>" + to + "</value></entry>";
		String soapEmailCC = null;
		String soapEmailBCC = null;
		if (cc != null)
		{
			soapEmailCC = "<entry><key>CC</key><value>" + cc + "</value></entry>";
		}
		if (bcc != null)
		{
			soapEmailBCC = "<entry><key>BCC</key><value>" + bcc + "</value></entry>";
		}
		final String soapEmailSubject = "<entry><key>SUBJECT</key><value>" + subject + "</value></entry>";
		final String soapEmailContent = "<entry><key>CONTENT</key><value><![CDATA[" + mailContent + "]]></value></entry>";
		
		return (soapEnvelopeStart + soapEmailFrom + soapEmailTo + (soapEmailCC == null ? "" : soapEmailCC) + (soapEmailBCC == null ? "" : soapEmailBCC) + soapEmailSubject
				+ soapEmailContent + soapEnvelopeEnd);
	}
	
	/**
	 * sendMail method - Enables to connect to the Automated mailer and send
	 * email.
	 * 
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 * @param cc
	 *            the cc
	 * @param bcc
	 *            the bcc
	 * @param mailType
	 *            the mail type
	 * @param subject
	 *            the subject
	 * @param mailContent
	 *            the mail content
	 * @param targetUrl
	 *            the target url
	 */
	public void sendMail(final String from, final String to, final String cc, final String bcc, final String mailType, final String subject,
			final String mailContent, final String targetUrl)
	{
		logger.info("Sending the mail with the given Details: "+to+", cc: "+cc+", bcc: "+bcc);
		makeSOAPCall(from, to, cc, bcc, mailType, subject, mailContent, targetUrl);
	}
	
	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(final String[] args)
	{
		//final AutomatedMailerClient mailSenderClient = new AutomatedMailerClient();
		//mailSenderClient.sendMail("varra@outlook.com",  "varra@outlook.com", "varra@outlook.com", "varra@outlook.com", "text/plain", "Hi .. !!", "Hi .. !!", "http://192.168.1.57:8080/automated-mailer-0.1/services/mail-engine");
	}
}
