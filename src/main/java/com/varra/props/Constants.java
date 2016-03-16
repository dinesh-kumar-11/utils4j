/*
 * utils4j - Constants.java, May 6, 2011 2:22:14 PM
 * 
 * Copyright 2011 Varra Ltd, Inc. All rights reserved.
 * Varra proprietary/confidential. Use is subject to license terms.
 */
package com.varra.props;

/**
 * An convenient interface which is used to store all the constant property
 * names. No need to use it explicitly, use these constants as
 * {@link VarraProperties} constants.
 * 
 * <br>
 * Use them as shown below.<br><br>
 * VarraProperties.getIntegerProperty(<b>VarraProperties.SEND_RETRY_COUNT_PROPERTY</b>, DEFAULT_RETRY_COUNT);
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
public interface Constants
{
	
	/** The Constant RAPID_TYPE. */
	String RAPID_TYPE = "com.varra.rapid";
	
	/** The O s_ domain. */
	String OS_DOMAIN = "java.lang:type=OperatingSystem";
	
	/** The THREADIN g_ domain. */
	String THREADING_DOMAIN = "java.lang:type=Threading";
	
	/** The RUNTIM e_ domain. */
	String RUNTIME_DOMAIN = "java.lang:type=Runtime";
	
	/** The SPRIN g_ contex t_ file. */
	String SPRING_CONTEXT_FILE="contextFile";
	
	/** The LO g_ xm l_ file. */
	String LOG_XML_FILE="log4j.xml";
	
	/** The LO g_ prop s_ file. */
	String LOG_PROPS_FILE="log4j.properties";
	
	/** The Constant RAPIDAPPSERVER_HOME. */
	String RAPIDAPPSERVER_HOME = "RAPIDAPPSERVER_HOME";
	
	/** The Constant UTF_8. */
	String UTF_8 = "UTF-8";
	
	/** The NEw line. */
	String NEW_LINE = "\n";
	
	/** The TAB. */
	String TAB = "\t";
	
    /**
     * <code>\u000a</code> linefeed LF ('\n').
     * 
     * @see <a href="http://java.sun.com/docs/books/jls/third_edition/html/lexical.html#101089">JLF: Escape Sequences
     *      for Character and String Literals</a>
     * @since 2.2
     */
    char LF = '\n';

    /**
     * <code>\u000d</code> carriage return CR ('\r').
     * 
     * @see <a href="http://java.sun.com/docs/books/jls/third_edition/html/lexical.html#101089">JLF: Escape Sequences
     *      for Character and String Literals</a>
     * @since 2.2
     */
    char CR = '\r';
	
	/** The DEf_ monitoring_ interval. */
	int DEF_MONITORING_INTERVAL = 30 * 60 * 1000;
	
	/** The property to enable or disable the spring context "spring.context.enabled" */
	String SPRING_CONTEXT_ENABLED = "spring.context.enabled";
	
	String GT_KEEP_ALIVE_TIME_PROPERTY = "globalThread.keepAliveTime";
	
	long GT_KEEP_ALIVE_TIME_DEFAULT_VALUE = 1 * 60 * 1000;
	
	String THIS_INTERVAL_PROPERTY = "this.interval";
	
	//String ENV_VARIABLE_REGEX = ".*\\$\\{(.*)\\}.*";
	String ENV_VARIABLE_REGEX = ".*\\$\\{([^}]*)\\}.*";
	
	/** First part of the regex */
	String FIRST_PART = "\\$\\{";
	
	/** Second part of the regex */
	String SECOND_PART = "\\}";
	
	String RESOLVE_REQUIRED_PROPERTY = "resolve.required";
	
	String REPLACE_UNKNOWN_2_SPACE_PROPERTY = "replace.unknown.2.space";
}
