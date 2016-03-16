/*
 * utils4j - RandomPasswordUtils.java, Sep 5, 2013 2:58:55 PM
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

import java.util.Random;

import com.varra.classification.InterfaceAudience;
import com.varra.classification.InterfaceStability;

/**
 * TODO Description go here.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V. Reddy</a>
 * @version 1.0
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
final class RandomPasswordUtils
{
	
	/** The Constant ALL_DIGITS. */
	final static String ALL_DIGITS = "0123456789";
	
	/** The Constant ALL_LOWER_CASE_CHARS. */
	final static String ALL_LOWER_CASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
	
	/** The Constant ALL_UPPER_CASE_CHARS. */
	final static String ALL_UPPER_CASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/** The Constant ALL_SPECIAL_CHARS. */
	final static String ALL_SPECIAL_CHARS = "!@#$%^&*()+=";
	
	/**
	 * Returns the random password of length: length.
	 * 
	 * @param length
	 *            the length
	 * @return the string
	 */
	public static String password(int length)
	{
		return new String(password(length, ALL_DIGITS, ALL_LOWER_CASE_CHARS, ALL_UPPER_CASE_CHARS, ALL_SPECIAL_CHARS));
	}
	
	/**
	 * Returns the random password of length: length.
	 * 
	 * @param length
	 *            the length
	 * @return the string
	 */
	public static char[] passwordAsCharArray(int length)
	{
		return password(length, ALL_DIGITS, ALL_LOWER_CASE_CHARS, ALL_UPPER_CASE_CHARS, ALL_SPECIAL_CHARS);
	}
	
	/**
	 * Length will tell the length of the password need to be generated number
	 * will hold numbers(0-9) lowerCaseAlphabets will hold lower case
	 * alphabets(a-z) upperCaseAlphabets will hold upper case case
	 * alphabets(A-Z) specialCharacters will hold characters like !@#$%^&*().
	 * 
	 * @param length
	 *            the password length
	 * @param numbers
	 *            the number
	 * @param lowerChars
	 *            the lower case alphabets
	 * @param upperChars
	 *            the upper case alphabets
	 * @param specialChars
	 *            the special characters
	 * @return the random password
	 */
	private static char[] password(int length, String numbers, String lowerChars, String upperChars, String specialChars)
	{
		final char[] password = new char[length];
		for (int i = 0, j = 0; i < length; i++)
		{
			password[i] = randomCharacter(++j == 4 ? j = 0 : j, numbers, lowerChars, upperChars, specialChars);
		}
		return shuffleNget(password);
	}
	
	/**
	 * Gets the random password character.
	 * 
	 * @param position
	 *            the position
	 * @param number
	 *            the number
	 * @param lowerCase
	 *            the lower case
	 * @param upperCase
	 *            the upper case
	 * @param specialChar
	 *            the special char
	 * @return the random password characters
	 */
	private static char randomCharacter(int position, String number, String lowerCase, String upperCase, 	String specialChar)
	{
		final Random randomNum = new Random();
		switch (position)
		{
			case 0:
				return upperCase.charAt(randomNum.nextInt(upperCase.length() - 1));
			case 1:
				return number.charAt(randomNum.nextInt(number.length() - 1));
			case 2:
				return lowerCase.charAt(randomNum.nextInt(lowerCase.length() - 1));
			case 3:
				return specialChar.charAt(randomNum.nextInt(specialChar.length() - 1));
		}
		return 1;
	}
	
	/**
	 * Shuffles the actual generated password for more efficiency.
	 * 
	 * @param password
	 *            the password
	 * @return the string
	 */
	private static char[] shuffleNget(char[] password)
	{
		final char[] output = new char[password.length];
		int i = 0; 
		while (password.length > 0)
		{
			final int randPicker = (int) (Math.random() * password.length);
			output[i++] = password[randPicker];
			password = getNewArray(password, randPicker);
		}
		return output;
	}
	
	/**
	 * Gets the new array by skipping the char at given position.
	 * 
	 * @param oldArray
	 *            the old array
	 * @param position
	 *            the position
	 * @return the new array
	 */
	private static char[] getNewArray(char[] oldArray, int position)
	{
		final char[] newArray = new char[oldArray.length - 1];
		for (int i = 0; i < newArray.length; i++)
		{
			newArray[i] = oldArray[i >= position ? i + 1 : i];
		}
		return newArray;
	}
}
