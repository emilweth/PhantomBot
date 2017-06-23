/*
 * Copyright (C) 2016-2017 phantombot.tv
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.scaniatv;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;

import java.util.Date;
import java.text.SimpleDateFormat;

import tv.phantombot.PhantomBot;

/*
 * @Author scaniatv
 */
public class GenerateLogs {

	/*
	 * Method that will write the latest errors to one file.
	 */
	public static void writeLogs() {
		com.gmt2001.Console.out.println("Generating latest error logs...");

		String logData = "";

		logData += readFile("logs/error/" + getDate(false) + ".txt");
		logData += readFile("logs/core-error/" + getDate(true) + ".txt");

		String fileName = ("errors_" + getDate(false) + "@" + System.currentTimeMillis() + ".txt");

		com.gmt2001.Console.out.println("Log file \"" + fileName + "\" was created in the main bot folder.");

		writeToFile(fileName, logData);
	}

	/*
	 * Method that will print the latest errors in the console.
	 */
	public static void printLogs() {
		com.gmt2001.Console.out.println("Generating latest error logs...");

		String logData = "";

		logData += readFile("logs/error/" + getDate(false) + ".txt");
		logData += readFile("logs/core-error/" + getDate(true) + ".txt");

		com.gmt2001.Console.out.println(logData);
	}

	/*
	 * Method that will read the log files.
	 *
	 * @param  {String} file
	 * @return {String}
	 */
	private static String readFile(String file) {
		BufferedReader bufferedReader = null;
		String data = "";
		String line = "";

		try {
			if (new File(file).exists()) {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		
				while ((line = bufferedReader.readLine()) != null) {
					data += (line + "\r\n");
				}
			}
		} catch (IOException ex) {
			com.gmt2001.Console.err.println("Failed to read log file: [" + file + "] [IOException] " + ex.getMessage());
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					com.gmt2001.Console.printStackStrace(ex);
				}
			}
		}

		return data;
	}

	/*
	 * Method used to write the log data to a file in the main bot folder.
	 *
	 * @param {String} file
	 * @param {String} data
	 */
	private static void writeToFile(String file, String data) {
		BufferedWriter bufferedWriter = null;

		try {
			bufferedWriter = new BufferedWriter(new FileWriter(file));

			bufferedWriter.write(data);
		} catch (IOException ex) {
			com.gmt2001.Console.err.println("Failed to write log file: [" + file + "] [IOException] " + ex.getMessage());
		} finally {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException ex) {
					com.gmt2001.Console.out.printStackStrace(ex);
				}
			}
		}
	}

	/*
	 * Method to get the current date of log files.
	 *
	 * @return {String}
	 */
	private static String getDate(boolean isGMT) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		dateFormat.setTimeZone(java.util.TimeZone.getTimeZone((isGMT ? "GMT" : PhantomBot.timeZone)));
        return dateFormat.format(new Date());
	}
}
