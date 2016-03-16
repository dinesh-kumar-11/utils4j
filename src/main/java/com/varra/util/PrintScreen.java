/*
 * utils4j - PrintScreen.java, Aug 16, 2015 5:03:43 PM
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

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

/**
 * Modified for adding java doc.
 * 
 * @author Rajakrishna V. Reddy
 * @version 1.0
 * 
 */
public class PrintScreen
{
	
	/**
	 * Gets the screen.
	 */
	public void getScreen()
	{
		try
		{
			final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
			final Robot robot = new Robot();
			int i = 0;
			while (true)
			{
				String dir = "./screens/";
				final BufferedImage img = robot.createScreenCapture(new Rectangle(size));
				final File newFile = new File(createDirWithTodayDate(dir) + "/screenShot" + (i++) + ".jpg");
				ImageIO.write(img, "JPG", newFile);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the screen captured and stored in the given directory.
	 * 
	 * @param dir
	 *            the dir
	 */
	public void getScreen(String dir)
	{
		try
		{
			String screensDir = "./screens/";
			int i = 0;
			String dirName = null;
			if (dir != null && dir.trim().length() > 0)
			{
				dirName = createDirWithTodayDate("/krishna/Misc/pictures/PrintScreen/screens/"+dir+"/");
			}
			else
			{
				dirName = createDirWithTodayDate(screensDir);
			}
			final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
			final Robot robot = new Robot();
			while (true)
			{
				final BufferedImage img = robot.createScreenCapture(new Rectangle(size));
				final File newFile = new File(dirName+ "/screenShot" + (i++) + ".jpg");
				ImageIO.write(img, "JPG", newFile);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates the dir with today date.
	 *
	 * @param dir
	 *            the dir
	 * @return the string
	 */
	private static String createDirWithTodayDate(String dir)
	{
		final SimpleDateFormat format = new SimpleDateFormat("dd-MMMMM-yyyy HH:mm:ss");
		final File newFile = new File(dir + format.format(new Date(System.currentTimeMillis())));
		if (!newFile.exists())
		{
			newFile.mkdirs();
		}
		
		return newFile.getAbsolutePath();
	}
	
	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args)
	{
		if (args.length == 1)
		{
			new PrintScreen().getScreen(args[0]);
		}
		else
		{
			new PrintScreen().getScreen();
		}
	}
}
