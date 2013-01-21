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
	 * Test conversion between Gregorian and Julian dates (both ways).
	 */
	@Test
	public void testDateConversions1() {
		testDateConversion("19760302 15:15:45 UTC", 2442840.1359375);
		testDateConversion("19760302 10:15:45 EST", 2442840.1359375);
		testDateConversion("19780427 12:00:42 CET", 2443625.9588194);
		testDateConversion("19010101 23:59:59 UTC", 2415386.4999884);
		testDateConversion("19010101 09:00:00 UTC", 2415385.875);
		testDateConversion("19010101 00:00:00 AKST", 2415385.875);
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
	 * Log the day/night status of some locations. This method only logs the
	 * day/night status, it does not do any validation.
	 */
	@Test
	public void testIsNight() {
		logDayOrNight("Honolulu", 21.3069, -157.8583);
		logDayOrNight("Los Angeles", 34.0522, -118.2437);
		logDayOrNight("Chicago", 41.8781, -87.6298);
		logDayOrNight("Dublin", 53.3441, -6.2675);
		logDayOrNight("Paris", 48.8567, 2.351);
		logDayOrNight("Tokyo", 35.6938, 139.7036);
		logDayOrNight("Sydney", -33.86, 151.2111);

	}

	private void logDayOrNight(String name, double latitude, double longitude) {
		boolean isDay = SunriseSunset.isDay(latitude, longitude);
		System.out.println(name + ": Currently " + (isDay ? "day" : "night"));
	}

}
