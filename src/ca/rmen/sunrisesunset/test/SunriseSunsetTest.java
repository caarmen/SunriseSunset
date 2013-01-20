package ca.rmen.sunrisesunset.test;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import junit.framework.Assert;

import org.junit.Test;

import ca.rmen.sunrisesunset.SunriseSunset;

public class SunriseSunsetTest {

	private static final SimpleDateFormat DATE_FORMAT_SECONDS = new SimpleDateFormat(
			"yyyyMMdd HH:mm:ss z");
	private static final SimpleDateFormat DATE_FORMAT_MINUTES = new SimpleDateFormat(
			"yyyyMMdd HH:mm");
	private static final SimpleDateFormat DATE_FORMAT_DAY = new SimpleDateFormat(
			"yyyyMMdd");

	@Test
	public void testDateConversions1() {
		test("19760302 15:15:45 UTC", 2442840.1359375);
		test("19760302 10:15:45 EST", 2442840.1359375);
		test("19780427 12:00:42 CET", 2443625.9588194);
		test("19010101 23:59:59 UTC", 2415386.4999884);
		test("19010101 09:00:00 UTC", 2415385.875);
		test("19010101 00:00:00 AKST", 2415385.875);
		test("19010101 00:00:00 UTC", 2415385.5);
		test("19001231 23:59:59 UTC", 2415385.4999884);
		test("19000701 00:00:00 UTC", 2415201.5);
		test("19000401 00:00:00 UTC", 2415110.5);
		test("19000301 00:00:00 UTC", 2415079.5);
		test("19000228 00:00:00 UTC", 2415078.5);
		test("19000225 00:00:00 UTC", 2415075.5);
		test("19000221 00:00:00 UTC", 2415071.5);
		test("19000214 00:00:00 UTC", 2415064.5);
		test("19000201 00:00:00 UTC", 2415051.5);
		test("18800516 10:19:00 UTC", 2407851.929861111);
		test("18010101 09:00:00 UTC", 2378861.875);
	}

	@Test
	public void testDateConversions2() {
		test("19000131 00:03:18 UTC", 2415050.502291667);
		test("18500712 00:17:18 UTC", 2396950.512013889);
	}

	@Test
	public void testSunriseSunset() {

		testSunriseSunset("PST", "20130120", -118.2437, 34.0522, "06:57",
				"17:11");
		testSunriseSunset("CET", "20130120", 2.351, 48.8567, "08:35", "17:28");
		testSunriseSunset("Australia/Sydney", "20121225", 151.2111, -33.86,
				"05:43", "20:07");
		testSunriseSunset("Japan", "20130501", 139.7036, 35.6938, "04:49",
				"18:27");
		testSunriseSunset("Europe/Dublin", "20130605", -6.2675, 53.3441,
				"05:01", "21:46");
		testSunriseSunset("CST", "20130622", -87.6298, 41.8781, "05:16",
				"20:29");
	}

	private void test(String gregorianDateStr, double julianDate) {
		compare(gregorianDateStr, julianDate);
		compare(julianDate, gregorianDateStr);
	}

	private void compare(String inputGregorianDateStr, double expectedJulianDate) {
		Calendar inputGregorianCal = parseDate(null, DATE_FORMAT_SECONDS,
				inputGregorianDateStr);
		double calculatedJulianDate = SunriseSunset
				.getJulianDate(inputGregorianCal);
		double error = expectedJulianDate - calculatedJulianDate;
		double absError = Math.abs(error);
		assertTrue("Expected julian date " + expectedJulianDate + " but got "
				+ calculatedJulianDate + " for gregorian date "
				+ inputGregorianDateStr, absError < 0.01);
	}

	private void compare(double inputJulianDate, String expectedGregorianDateStr) {
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
			assertTrue("Error parsing date " + expectedGregorianDateStr, false);
		}
	}

	private void testSunriseSunset(String timeZoneString,
			String inputDayString, double inputLongitude, double inputLatitude,
			String expectedSunriseString, String expectedSunsetString) {
		TimeZone tz = TimeZone.getTimeZone(timeZoneString);

		Calendar inputDay = parseDate(tz, DATE_FORMAT_DAY, inputDayString);
		Calendar[] actualSunriseSunset = SunriseSunset.getSunriseSunset(
				inputDay, inputLatitude, inputLongitude);
		Calendar expectedSunrise = parseDate(tz, DATE_FORMAT_MINUTES,
				inputDayString + " " + expectedSunriseString);
		Calendar expectedSunset = parseDate(tz, DATE_FORMAT_MINUTES,
				inputDayString + " " + expectedSunsetString);
		Calendar actualSunrise = actualSunriseSunset[0];
		Calendar actualSunset = actualSunriseSunset[1];

		String actualSunriseString = format(DATE_FORMAT_MINUTES, actualSunrise);
		String actualSunsetString = format(DATE_FORMAT_MINUTES, actualSunset);
		compare(expectedSunrise, expectedSunriseString, actualSunrise,
				actualSunriseString, 180000);
		compare(expectedSunset, expectedSunsetString, actualSunset,
				actualSunsetString, 180000);
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

	private void compare(Calendar cal1, String calString1, Calendar cal2,
			String calString2, long toleranceMillis) {
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
}
