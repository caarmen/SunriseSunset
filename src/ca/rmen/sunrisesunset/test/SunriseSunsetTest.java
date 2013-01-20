package ca.rmen.sunrisesunset.test;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import ca.rmen.sunrisesunset.SunriseSunset;

public class SunriseSunsetTest {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyyMMdd HH:mm:ss z");

	@Test
	public void test1() {
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
	public void test2() {
		test("19000131 00:03:18 UTC", 2415050.502291667);
		test("18500712 00:17:18 UTC", 2396950.512013889);

	}

	private void test(String gregorianDateStr, double julianDate) {
		compare(gregorianDateStr, julianDate);
		compare(julianDate, gregorianDateStr);
	}

	private void compare(String inputGregorianDateStr, double expectedJulianDate) {
		try {
			Date inputGregorianDate = DATE_FORMAT.parse(inputGregorianDateStr);
			Calendar inputGregorianCal = Calendar.getInstance();
			inputGregorianCal.setTime(inputGregorianDate);
			double calculatedJulianDate = SunriseSunset
					.getJulianDate(inputGregorianCal);
			double error = expectedJulianDate - calculatedJulianDate;
			double absError = Math.abs(error);
			assertTrue("Expected julian date " + expectedJulianDate
					+ " but got " + calculatedJulianDate
					+ " for gregorian date " + inputGregorianDateStr,
					absError < 0.01);
		} catch (ParseException e) {
			assertTrue("Error parsing date " + inputGregorianDateStr, false);
		}
	}

	private void compare(double inputJulianDate, String expectedGregorianDateStr) {
		try {
			Date expectedGregorianDate = DATE_FORMAT
					.parse(expectedGregorianDateStr);
			Calendar expectedGregorianCal = Calendar.getInstance();
			expectedGregorianCal.setTime(expectedGregorianDate);
			Calendar calculatedGregorianCal = SunriseSunset
					.getGregorianDate(inputJulianDate);
			// Truncate milliseconds in the calculated result so we can compare
			// with the expected result.
			calculatedGregorianCal.set(Calendar.MILLISECOND, 0);
			String calculatedGregorianDateStr = DATE_FORMAT
					.format(calculatedGregorianCal.getTime());
			Assert.assertEquals("Expected gregorian date "
					+ expectedGregorianDateStr + " but got "
					+ calculatedGregorianDateStr + " for Julian date "
					+ inputJulianDate, calculatedGregorianCal,
					expectedGregorianCal);
		} catch (ParseException e) {
			assertTrue("Error parsing date " + expectedGregorianDateStr, false);
		}

	}
}
