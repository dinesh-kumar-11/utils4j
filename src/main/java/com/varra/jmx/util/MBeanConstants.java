/*
 * utils4j - MBeanConstants.java, Feb 3, 2011 6:10:17 PM
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

/**
 * Holds the JMX specified MBean constants and values. Is useful in the
 * Registration and unRegistration of an MBean with the MBean Server.
 * <p>
 * Mainly it holds 3 types of constants as enums.<ul>
 * <li><b>Port eg: com.varra.rapid<br>
 * </b><span style="padding-left:50px">Defines where this MBean should go in.</span>
 * <li><b>Type eg: Health<br>
 * </b><span style="padding-left:50px">Defines where this MBean should go as
 * which type in the above port. </span>
 * <li><b>Name eg: HypericAssetProcessor</b></ul><br>
 * <span style="padding-left:50px">Defines The name of the service of this
 * MBean belongs to.</span>
 * <p>
 * <br>
 * Needs to be subclassed by the interested stubs to provide the more specific
 * values/constants.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 1.0
 * 
 */
public interface MBeanConstants
{
	
	/**
	 * Port eg: com.varra.rapid<br>
	 * <b><span style="padding-left:50px">Defines where this MBean should go
	 * in.</span></b>
	 * 
	 * @author Rajakrishna V. Reddy
	 * @version 1.0
	 * 
	 */
	public enum Port
	{
		
		/** The DEFAULT Port. */
		VARRA("com.varra"),

		/** The RAPID Port. */
		RAPID("com.varra.rapid");
		
		/** The port name. */
		final private String portName;
		
		/**
		 * Instantiates a new port.
		 * 
		 * @param portName
		 *            the port name
		 */
		private Port(String portName)
		{
			this.portName = portName;
		}
		
		/**
		 * Gets the port name.
		 * 
		 * @return the portName
		 */
		public String getPortName()
		{
			return portName;
		}
	}
	
	/**
	 * The Enum Type.
	 * 
	 * @author Rajakrishna V. Reddy
	 * @version 1.0
	 * 
	 */
	public enum Type
	{
		
		/** The HEALTH. */
		HEALTH("Health"),

		/** The PERFORMANCE. */
		PERFORMANCE("Performance");
		
		/** The port name. */
		final private String typeName;
		
		/**
		 * Instantiates a new type.
		 * 
		 * @param typeName
		 *            the type name
		 */
		private Type(String typeName)
		{
			this.typeName = typeName;
		}
		
		/**
		 * Gets the type name.
		 * 
		 * @return the typeName
		 */
		public String getTypeName()
		{
			return typeName;
		}
	}
	
	/**
	 * The Enum Application.
	 * 
	 * @author Rajakrishna V. Reddy
	 * @version 1.0
	 * 
	 */
	public enum Application
	{
		
		/** The HYPERIC. */
		HYPERIC("Hyperic"),

		/** The hyperic asset processor. */
		HYPERIC_ASSET_PROCESSOR("AssetProcessor"),

		/** The hyperic performance processor. */
		HYPERIC_PERFORMANCE_PROCESSOR("HypericPerformance1"),

		/** The hyperic event server. */
		HYPERIC_EVENT_SERVER("event-server"),

		/** The hyperic performance processor diagonastics. */
		HYPERIC_PERFORMANCE_PROCESSOR_DIAGONASTICS("axis1"),

		/** The open nms asset processor. */
		OPENNMS_ASSET_PROCESSOR("OpenNmsAssetProcessor"),

		/** The rrd processor. */
		RRD_PROCESSOR("RRDProcessor"),

		/** The ping. */
		PING("Ping"),
		
		/** The PIN g_ server. */
		PING_SERVER("PingServer"),

		/** The correlation. */
		CORRELATION("correlation"),

		/** The sla. */
		SLA("sla-runtime-1.0"),

		/** The escalation. */
		ESCALATION_FW("escalation-fw"),
		
		/** The ESCALATIO n_ cte. */
		ESCALATION_CTE("escalation-cte"),

		/** The hqgce client. */
		HQGCE_CLIENT("HQGCEClient"),

		/** The automated mailer. */
		AUTOMATED_MAILER("AutomatedMailer"),

		/** The rapid trap processor. */
		RAPID_TRAP_PROCESSOR("RAPIDTrapProcessor"),

		/** The rapid trap collector. */
		//RAPID_TRAP_COLLECTOR("RAPIDTrapCollector"),

		/** The mail todo. */
		MAIL_TODO("rapid-mail-1.0"),

		/** The open nms event poller server. */
		OPENNMS_EVENT_SERVER("opennms-event-server"),

		/** The JVM. */
		JVM("JVM"),
		/** The server (placed here for the purpose of Application Monitor). */
		SERVER("server"),
		
		/** The OPERATIN g_ system. */
		OPERATING_SYSTEM("OperatingSystem"),
		
		/** The THREADING. */
		THREADING("ThreadDetails");
		/** The name. */
		final private String name;
		
		/**
		 * Instantiates a new application.
		 * 
		 * @param name
		 *            the name
		 */
		private Application(String name)
		{
			this.name = name;
		}
		
		/**
		 * Gets the name.
		 * 
		 * @return the name
		 */
		public String getName()
		{
			return name;
		}
	}
}
