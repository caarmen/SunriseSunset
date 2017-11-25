/*
 * Sunrise Sunset Calculator.
 * Copyright (C) 2013-2015 Carmen Alvarez
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package ca.rmen.sunrisesunset.cli;

import ca.rmen.sunrisesunset.SunriseSunset;

import java.io.File;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.*;
import java.text.*;

/**
 * Example program that illustrates how to use the SunriseSunset class.
 */
class SunriseSunsetCLI {
	public static void main(String[] args) throws Throwable {
		if (args.length != 4) {
			usage();
			return;
		}

		// Read the input
		int i = 0;
		String timeZoneString = args[i++];
		String inputDayString = args[i++];
		double latitude = Double.parseDouble(args[i++]);
		double longitude = Double.parseDouble(args[i]);

		// Create a Calendar for the given date at noon.
		TimeZone tz = TimeZone.getTimeZone(timeZoneString);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		sdf.setTimeZone(tz);
		Date date = sdf.parse(inputDayString);
		Calendar day = Calendar.getInstance(tz);
		day.setTime(date);
		day.set(Calendar.HOUR_OF_DAY, 12);

		// For info, print the current time at the location.
		Calendar now = Calendar.getInstance(tz);
		System.out.println("Current time at: " + latitude + "," + longitude + ":");
		printCalendar(tz, now);

		System.out.println("Current day period is " + SunriseSunset.getDayPeriod(now, latitude, longitude));
		long dayLength = SunriseSunset.getDayLength(now, latitude, longitude);
		System.out.println("Day is " + dayLength + " milliseconds long");

		System.out.println("Lookup for date:");
		printCalendar(tz, day);

		// Get the results and print them
		Calendar[] result = SunriseSunset.getSunriseSunset(day, latitude, longitude);
		System.out.println("Sunrise, Sunset:");
		printCalendars(tz, result);

		result = SunriseSunset.getCivilTwilight(day, latitude, longitude);
		System.out.println("Civil twilight:");
		printCalendars(tz, result);

		result = SunriseSunset.getNauticalTwilight(day, latitude, longitude);
		System.out.println("Nautical twilight:");
		printCalendars(tz, result);

		result = SunriseSunset.getAstronomicalTwilight(day, latitude, longitude);
		System.out.println("Astronomical twilight:");
		printCalendars(tz, result);

		Calendar solarNoon = SunriseSunset.getSolarNoon(day, latitude, longitude);
		System.out.println("Solar noon:");
		printCalendar(tz, solarNoon);
	}

	private static void printCalendar(TimeZone tz, Calendar cal) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzzz");
		simpleDateFormat.setTimeZone(tz);
		System.out.println("  " + simpleDateFormat.format(cal.getTime()));
	}

	private static void printCalendars(TimeZone tz, Calendar[] calendars) {
		if (calendars == null) {
			System.out.println("N/A");
			return;
		}
		printCalendar(tz, calendars[0]);
		printCalendar(tz, calendars[1]);
	}

	private static void usage() {
		System.err.println(getProgramName() + " <timezone> <yyyyMMdd> <latitude> <longitude>");
		System.exit(1);
	}

	/**
	 * Try to return a string like "java -jar target/sunrise-sunset-cli.jar" to be used within the usage.
	 */
	private static String getProgramName() {
		try {
			ProtectionDomain protectionDomain = SunriseSunsetCLI.class.getProtectionDomain();
			if (protectionDomain != null) {
				CodeSource codeSource = protectionDomain.getCodeSource();
				if (codeSource != null) {
					URL location = codeSource.getLocation();
					if (location != null) {
						String path = location.getPath();
						if (path != null) {
							File file = new File(path);
							if (file.isFile()) {
								String relativeFile = new File(".").toURI().relativize(file.toURI()).getPath();
								return "java -jar " + relativeFile;
							}
						}
					}
				}
			}
		} catch (Throwable t) {
			// don't really care
		}
		return "java " + SunriseSunsetCLI.class.getName();
	}
}
