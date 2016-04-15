/*
 * utils4j - PropertyFileReaderTest.java, Apr 14, 2016 1:45:51 PM
 * 
 * Copyright 2016 Trimble Ltd, Inc. All rights reserved.
 * Trimble proprietary/confidential. Use is subject to license terms.
 */
package utils4j;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;
import com.varra.exception.PropertyFileLoaderException;
import com.varra.log.ConsoleLogger;
import com.varra.log.Log4jLogger;
import com.varra.log.Logger;
import com.varra.log.LoggerConstants;
import com.varra.props.ApplicationProperties;
import com.varra.props.VarraProperties;

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
public class PropertyFileReaderTest
{
	
	
	/** The logger to log the debugging messages as application runs. */
	private final Logger logger = Logger.getLogger(PropertyFileReaderTest.class);
	
	/**
	 * Instantiates a new property file reader test.
	 */
	public PropertyFileReaderTest()
	{
		logger.info("Initialized successfully.");
	}
	
	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws PropertyFileLoaderException 
	 */
	public static void main(String[] args) throws PropertyFileLoaderException
	{
		//String file = "/krishna/git/tpaas/tid/4.1.0/identity/trimble-identity/trimble-service/tools/bootstrap/src/main/resources/identity.properties";
		String file = "/krishna/varra/workspaces/own/RD/utils4j-git/utils4j/src/main/resources/my.properties";
		VarraProperties.setProperty(LoggerConstants.LOG_CLASS_FQ_NAME, Log4jLogger.class.getName());
		VarraProperties.addApplicationProperties(new ApplicationProperties(file));
		
		System.out.println(VarraProperties.getProperty("name"));
	}
}
