/*
 * Sunrise Sunset Calculator.
 * Copyright (C) 2013 Carmen Alvarez
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
package ca.rmen.sunrisesunset.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import junit.framework.Assert;

import org.junit.Test;

import ca.rmen.sunrisesunset.SunriseSunset;

/**
 * Unit tests for the Sunrise Sunset Calculator.
 * 
 * @author Carmen Alvarez
 * 
 */
public class SunriseSunsetTest {

	private static final SimpleDateFormat DATE_FORMAT_SECONDS = new SimpleDateFormat(
			"yyyyMMdd HH:mm:ss z");
	private static final SimpleDateFormat DATE_FORMAT_MINUTES = new SimpleDateFormat(
			"yyyyMMdd HH:mm");
	private static final SimpleDateFormat DATE_FORMAT_DAY = new SimpleDateFormat(
			"yyyyMMdd");

	/**
	 * Not a unit test, but helpful for troubleshooting and adding new unit
	 * tests. This method logs the list of Java timezone ids.
	 */
	@Test
	public void logTimezoneIds() {
		String[] timezoneIds = TimeZone.getAvailableIDs();
		Arrays.sort(timezoneIds);
		for (String timezoneId : timezoneIds)
			System.out.println(timezoneId);

	}

	/**
	 * Test conversion between Gregorian and Julian dates (both ways).
	 */
	@Test
	public void testDateConversions1() {
		testDateConversion("19760302 15:15:45 UTC", 2442840.1359375);
		testDateConversion("19760302 10:15:45 EST", 2442840.1359375);
		testDateConversion("19780427 12:00:42 CET", 2443625.9588194);
		testDateConversion("19010101 23:59:59 UTC", 2415386.4999884);
		testDateConversion("19010101 09:00:00 UTC", 2415385.875);
		testDateConversion("19190101 00:00:00 AKST", 2421959.916667);
		// After WWII, AKST was officially (not in practice) two hours slower
		// than PST (GMT-10). In practice Alaska had 4 time zones:
		// Bering Time, Alaska Time, Yukon Time, and Pacific Time.
		// http://www.alaskahistoricalsociety.org/index.cfm/discover-alaska/Glimpses-of-the-Past/98
		testDateConversion("19460101 00:00:00 AKST", 2431821.916667);
		testDateConversion("19670101 00:00:00 AKST", 2439491.916667);
		testDateConversion("19680101 00:00:00 AKST", 2439856.916667);
		// After April 1968, Alaska had the four official time zones
		// with AKST being UTC-10.
		testDateConversion("19690101 00:00:00 AKST", 2440222.916667);
		testDateConversion("19830101 00:00:00 AKST", 2445335.916667);

		// In October 1983, Alaska aligned with Yukon time (UTC-9)
		testDateConversion("19840101 00:00:00 AKST", 2445700.875);
		testDateConversion("19850101 00:00:00 AKST", 2446066.875);
		testDateConversion("19900101 00:00:00 AKST", 2447892.875);
		testDateConversion("20000101 00:00:00 AKST", 2451544.875);
		testDateConversion("20130101 00:00:00 AKST", 2456293.875);

		testDateConversion("19010101 00:00:00 UTC", 2415385.5);
		testDateConversion("19001231 23:59:59 UTC", 2415385.4999884);
		testDateConversion("19000701 00:00:00 UTC", 2415201.5);
		testDateConversion("19000401 00:00:00 UTC", 2415110.5);
		testDateConversion("19000301 00:00:00 UTC", 2415079.5);
		testDateConversion("19000228 00:00:00 UTC", 2415078.5);
		testDateConversion("19000225 00:00:00 UTC", 2415075.5);
		testDateConversion("19000221 00:00:00 UTC", 2415071.5);
		testDateConversion("19000214 00:00:00 UTC", 2415064.5);
		testDateConversion("19000201 00:00:00 UTC", 2415051.5);
		testDateConversion("18800516 10:19:00 UTC", 2407851.929861111);
		testDateConversion("18010101 09:00:00 UTC", 2378861.875);
	}

	/**
	 * Test conversion between Gregorian and Julian dates (both ways).
	 */
	@Test
	public void testDateConversions2() {
		testDateConversion("19000131 00:03:18 UTC", 2415050.502291667);
		testDateConversion("18500712 00:17:18 UTC", 2396950.512013889);
	}

	/**
	 * Test the conversion between the Gregorian date and the Julian date. The
	 * conversion both ways is tested.
	 * 
	 * @param gregorianDateStr
	 *            a Gregorian date in any time zone of the format
	 *            {@link #DATE_FORMAT_SECONDS}.
	 * @param julianDate
	 *            A Julian date which should be equivalent to the Gregorian
	 *            date.
	 */
	private void testDateConversion(String gregorianDateStr, double julianDate) {
		testGregorianToJulianConversion(gregorianDateStr, julianDate);
		testJulianToGregorianConversion(julianDate, gregorianDateStr);
	}

	/**
	 * Raises an assert failure if the two dates are not equivalent.
	 * 
	 * @param inputGregorianDateStr
	 *            a Gregorian date in any time zone of the format
	 *            {@link #DATE_FORMAT_SECONDS}.
	 * @param expectedJulianDate
	 *            A Julian date which should be equivalent to the Gregorian
	 *            date.
	 */
	private void testGregorianToJulianConversion(String inputGregorianDateStr,
			double expectedJulianDate) {
		Calendar inputGregorianCal = parseDate(null, DATE_FORMAT_SECONDS,
				inputGregorianDateStr);
		double calculatedJulianDate = SunriseSunset
				.getJulianDate(inputGregorianCal);
		double error = expectedJulianDate - calculatedJulianDate;
		double absError = Math.abs(error);
		Assert.assertTrue("Expected julian date " + expectedJulianDate
				+ " but got " + calculatedJulianDate + " for gregorian date "
				+ inputGregorianDateStr, absError < 0.01);
	}

	/**
	 * Raises an assert failure if the two dates are not equivalent.
	 * 
	 * @param expectedGregorianDateStr
	 *            a Gregorian date in any time zone of the format
	 *            {@link #DATE_FORMAT_SECONDS}.
	 * @param inputJulianDate
	 *            A Julian date which should be equivalent to the Gregorian
	 *            date.
	 */
	private void testJulianToGregorianConversion(double inputJulianDate,
			String expectedGregorianDateStr) {
		try {
			Date expectedGregorianDate = DATE_FORMAT_SECONDS
					.parse(expectedGregorianDateStr);
			Calendar expectedGregorianCal = Calendar.getInstance();
			expectedGregorianCal.setTime(expectedGregorianDate);
			Calendar calculatedGregorianCal = SunriseSunset
					.getGregorianDate(inputJulianDate);
			// Truncate milliseconds in the calculated result so we can compare
			// with the expected result.
			calculatedGregorianCal.set(Calendar.MILLISECOND, 0);
			String calculatedGregorianDateStr = format(DATE_FORMAT_SECONDS,
					calculatedGregorianCal);
			Assert.assertEquals("Expected gregorian date "
					+ expectedGregorianDateStr + " but got "
					+ calculatedGregorianDateStr + " for Julian date "
					+ inputJulianDate, calculatedGregorianCal,
					expectedGregorianCal);
		} catch (ParseException e) {
			e.printStackTrace();
			Assert.fail("Error parsing date " + expectedGregorianDateStr);
		}
	}

	/**
	 * Test the time of sunrise and sunset for some locations.
	 */
	@Test
	public void testSunriseSunset() {

		testSunriseSunset("PST", "20130120", 34.0522, -118.2437, "06:57",
				"17:11");
		testSunriseSunset("CET", "20130120", 48.8567, 2.351, "08:35", "17:28");
		testSunriseSunset("Australia/Sydney", "20121225", -33.86, 151.2111,
				"05:43", "20:07");
		testSunriseSunset("Japan", "20130501", 35.6938, 139.7036, "04:49",
				"18:27");
		testSunriseSunset("Europe/Dublin", "20130605", 53.3441, -6.2675,
				"05:01", "21:46");
		testSunriseSunset("CST", "20130622", 41.8781, -87.6298, "05:16",
				"20:29");
		testSunriseSunset("Pacific/Honolulu", "20150827", 21.3069, -157.8583,
				"06:13", "18:53");
		testSunriseSunset("America/Argentina/Buenos_Aires", "20130501",
				-34.6092, -58.3732, "07:29", "18:12");
		testSunriseSunset("America/Argentina/Buenos_Aires", "20131019",
				-34.6092, -58.3732, "06:07", "19:11");

		// The following test will not work on Java versions older than 2009.
		testSunriseSunset("America/Argentina/Buenos_Aires", "20130126",
				-34.6092, -58.3732, "06:07", "20:04");
		// The following test will not work on Java versions older than 2009.
		testSunriseSunset("America/Argentina/Buenos_Aires", "20131020",
				-34.6092, -58.3732, "06:05", "19:11");
		// The following test will not work on Java versions older than 2009.
		testSunriseSunset("America/Argentina/Buenos_Aires", "20131031",
				-34.6092, -58.3732, "05:53", "19:21");
	}

	/**
	 * @param timeZoneString
	 *            a valid Java timezone
	 * @param inputDayString
	 *            a day in the format {@link #DATE_FORMAT_DAY}
	 * @param inputLatitude
	 *            the latitude of a given location
	 * @param inputLongitude
	 *            the longitude of a given location (West is negative).
	 * @param expectedSunriseString
	 *            the time the sunrise is expected, in the format HH:mm. The
	 *            time should be in the timezone of the parameter
	 *            timeZoneString.
	 * @param expectedSunsetString
	 *            the time the sunset is expected, in the format HH:mm. The time
	 *            should be in the timezone of the parameter timeZoneString.
	 */
	private void testSunriseSunset(String timeZoneString,
			String inputDayString, double inputLatitude, double inputLongitude,
			String expectedSunriseString, String expectedSunsetString) {
		TimeZone tz = TimeZone.getTimeZone(timeZoneString);
		Calendar expectedSunrise = parseDate(tz, DATE_FORMAT_MINUTES,
				inputDayString + " " + expectedSunriseString);
		Calendar expectedSunset = parseDate(tz, DATE_FORMAT_MINUTES,
				inputDayString + " " + expectedSunsetString);

		// Create a Calendar for noon, in the given timezone for the given day.
		Calendar inputDay = parseDate(tz, DATE_FORMAT_DAY, inputDayString);
		inputDay.set(Calendar.HOUR_OF_DAY, 12);

		// Calculate the actual sunrise and sunset times.
		Calendar[] actualSunriseSunset = SunriseSunset.getSunriseSunset(
				inputDay, inputLatitude, inputLongitude);
		Calendar actualSunrise = actualSunriseSunset[0];
		Calendar actualSunset = actualSunriseSunset[1];

		String actualSunriseString = format(DATE_FORMAT_MINUTES, actualSunrise);
		String actualSunsetString = format(DATE_FORMAT_MINUTES, actualSunset);

		// Compare the actual and expected sunrise/sunset times. Allow a margin
		// of error of 3 minutes.
		assertEqualsOrAlmostEquals(expectedSunrise, expectedSunriseString,
				actualSunrise, actualSunriseString, 180000);
		assertEqualsOrAlmostEquals(expectedSunset, expectedSunsetString,
				actualSunset, actualSunsetString, 180000);
	}

	private Calendar parseDate(TimeZone tz, SimpleDateFormat format,
			String dateString) {
		try {
			// Use a clone since parsing may change the timezone
			SimpleDateFormat formatCopy = (SimpleDateFormat) format.clone();
			if (tz != null)
				formatCopy.setTimeZone(tz);
			Date date = formatCopy.parse(dateString);
			Calendar cal = Calendar.getInstance(formatCopy.getTimeZone());
			cal.setTime(date);
			return cal;
		} catch (ParseException e) {
			e.printStackTrace();
			Assert.fail("Could not parse date " + dateString + " with format "
					+ format.toPattern());
			return null;
		}
	}

	private String format(SimpleDateFormat format, Calendar calendar) {
		SimpleDateFormat formatCopy = (SimpleDateFormat) format.clone();
		formatCopy.setTimeZone(calendar.getTimeZone());
		return formatCopy.format(calendar.getTime());
	}

	/**
	 * Raises an assertion failure if the two Calendars have different time
	 * zones or are not close enough in time.
	 * 
	 * @param cal1
	 *            One date, compared with cal2
	 * @param calString1
	 *            readable string for cal1, used in the error message if the
	 *            Calendars are not equal
	 * @param cal2
	 *            Another date, compared zith cal1
	 * @param calString2
	 *            readable string for cal2, used in the error message if the
	 *            Calendars are not equal
	 * @param toleranceMillis
	 *            The difference between the timeInMillis of the two calendar
	 *            objects must be no greater than toleranceMillis
	 */
	private void assertEqualsOrAlmostEquals(Calendar cal1, String calString1,
			Calendar cal2, String calString2, long toleranceMillis) {
		Assert.assertEquals(
				calString1 + " is in timezone " + cal1.getTimeZone() + " and "
						+ calString2 + " is in timezone " + cal2.getTimeZone(),
				cal1.getTimeZone(), cal2.getTimeZone());
		long timeDifference = cal1.getTimeInMillis() - cal2.getTimeInMillis();
		long absTimeDifference = Math.abs(timeDifference);
		Assert.assertTrue(calString1 + " and " + calString2 + " are more than "
				+ toleranceMillis + " milliseconds apart (" + absTimeDifference
				+ ")", absTimeDifference < toleranceMillis);
	}

	/**
	 * Tests the {@link SunriseSunset#isDay(double, double)} method for a few
	 * locations. The value of isDay will depend on when the test is executed
	 * (time of day and time of year). We approximately validate the result: If
	 * the method returns true (day), we compare the current time to the
	 * sunrise/sunset of the longest day. If the method returns false (night),
	 * we compare the current time to the sunrise/sunset of the longest night.
	 */
	@Test
	public void testDayOrNight() {
		testDayOrNight("Honolulu", "Pacific/Honolulu", 21.3069, -157.8583,
				"5:48", "7:13", "17:47", "19:19");
		testDayOrNight("Los Angeles", "PST", 34.0522, -118.2437, "5:40",
				"7:00", "16:42", "20:09");
		testDayOrNight("Chicago", "CST", 41.8781, -87.6298, "5:14", "7:19",
				"16:19", "20:31");
		testDayOrNight("Buenos Aires", "America/Argentina/Buenos_Aires",
				-34.6092, -58.3732, "5:32", "8:02", "17:48", "20:11");
		testDayOrNight("Dublin", "Europe/Dublin", 53.3441, -6.2675, "4:55",
				"8:42", "16:05", "21:58");
		testDayOrNight("Paris", "CET", 48.8567, 2.351, "5:45", "8:45", "16:53",
				"21:59");
		testDayOrNight("Tokyo", "Japan", 35.6938, 139.7036, "4:24", "6:52",
				"16:27", "19:02");
		testDayOrNight("Sydney", "Australia/Sydney", -33.86, 151.2111, "5:36",
				"7:02", "16:52", "20:10");

	}

	/**
	 * Test the day/night status of a location. Since the day/night status of a
	 * location depends on when the unit test is executed, we can only do an
	 * approximate validation. For a given location, we know the earliest and
	 * latest possible times for sunrise and sunset. If the
	 * {@link SunriseSunset#isDay(double, double)} method returns true, we
	 * compare "now" with the longest day (earliest sunrise/latest sunset) at
	 * that location and make sure that "now" falls into this range. If isDay
	 * returns false, we compare "now" with the longest night (latest
	 * sunrise/earliest sunset) at that location and make sure that "now" is
	 * either before that sunrise or after that sunset.
	 * 
	 * @param name
	 *            the name of the location. Used for logging and shown in the
	 *            error if an assertion fails.
	 * @param timeZoneString
	 *            a valid Java timezone
	 * @param inputLatitude
	 *            the latitude of a given location
	 * @param inputLongitude
	 *            the longitude of a given location (West is negative).
	 * @param earliestSunriseString
	 *            a time in the format HH:mm for the earliest time sunrise
	 *            occurs throughout the year, at this location.
	 * @param latestSunriseString
	 *            a time in the format HH:mm for the latest time sunrise occurs
	 *            throughout the year, at this location.
	 * @param earliestSunsetString
	 *            a time in the format HH:mm for the latest time sunset occurs
	 *            throughout the year, at this location.
	 * @param latestSunsetString
	 *            a time in the format HH:mm for the latest time sunset occurs
	 *            throughout the year, at this location.
	 */
	private void testDayOrNight(String name, String timeZoneString,
			double inputLatitude, double inputLongitude,
			String earliestSunriseString, String latestSunriseString,
			String earliestSunsetString, String latestSunsetString) {
		boolean isDay = SunriseSunset.isDay(inputLatitude, inputLongitude);
		System.out.println(name + ": Currently " + (isDay ? "day" : "night"));

		Calendar now = Calendar.getInstance(TimeZone
				.getTimeZone(timeZoneString));
		Calendar earliestSunrise = getCalendarAtTime(now, earliestSunriseString);
		Calendar latestSunrise = getCalendarAtTime(now, latestSunriseString);
		Calendar earliestSunset = getCalendarAtTime(now, earliestSunsetString);
		Calendar latestSunset = getCalendarAtTime(now, latestSunsetString);
		String nowString = format(DATE_FORMAT_MINUTES, now);
		if (isDay) {
			Assert.assertTrue("In " + name + ", " + nowString
					+ " is before sunrise at " + earliestSunriseString
					+ ", but we think it's day.", now.after(earliestSunrise));
			Assert.assertTrue("In " + name + ", " + nowString
					+ " is after sunset at " + latestSunsetString
					+ ", but we think it's day.", now.before(latestSunset));
		} else {
			Assert.assertTrue("In " + name + ", " + nowString
					+ " is after sunrise at " + latestSunriseString
					+ " or before sunset at " + earliestSunsetString
					+ ", but we think it's night.", now.before(latestSunrise)
					|| now.after(earliestSunset));
		}
	}

	/**
	 * @param day
	 *            a date in any timezone.
	 * @param timeString
	 *            a time in format HH:mm
	 * @return a new Calendar object with the same day as the given day, and the
	 *         hours and minutes in timeString. Seconds and milliseconds are set
	 *         to zero.
	 */
	private Calendar getCalendarAtTime(Calendar day, String timeString) {

		String[] tokens = timeString.split(":");
		int hour = Integer.parseInt(tokens[0]);
		int min = Integer.parseInt(tokens[1]);
		Calendar clone = (Calendar) day.clone();
		clone.set(Calendar.HOUR_OF_DAY, hour);
		clone.set(Calendar.MINUTE, min);
		clone.set(Calendar.SECOND, 0);
		clone.set(Calendar.MILLISECOND, 0);
		return clone;
	}

}
