/*
 * Sunrise Sunset Calculator.
 * Copyright (C) 2013-2017 Carmen Alvarez
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

import ca.rmen.sunrisesunset.SunriseSunset;
import org.junit.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Helper methods for unit tests for the Sunrise Sunset Calculator.
 */
class SunriseSunsetTestUtils {

    // We compare our calculations to calculations found on some websites. Some slight differences
    // are observed sometimes between our calculations and the websites.  We tolerate a slight
    // difference between our calculations and their calculations.
    static final double DEFAULT_ACCURACY_MINUTES = 2.25;

    private static final SimpleDateFormat DATE_FORMAT_SECONDS = new SimpleDateFormat(
            "yyyyMMdd HH:mm:ss z");
    static final SimpleDateFormat DATE_FORMAT_MINUTES = new SimpleDateFormat(
            "yyyyMMdd HH:mm");
    private static final SimpleDateFormat DATE_FORMAT_DAY = new SimpleDateFormat(
            "yyyyMMdd");

    /**
     * Test the conversion between the Gregorian date and the Julian date. The
     * conversion both ways is tested.
     *
     * @param gregorianDateStr a Gregorian date in any time zone of the format
     *                         {@link #DATE_FORMAT_SECONDS}.
     * @param julianDate       A Julian date which should be equivalent to the Gregorian
     *                         date.
     */
    static void testDateConversion(String gregorianDateStr, double julianDate) {
        testGregorianToJulianConversion(gregorianDateStr, julianDate);
        testJulianToGregorianConversion(julianDate, gregorianDateStr);
    }

    /**
     * Raises an assert failure if the two dates are not equivalent.
     *
     * @param inputGregorianDateStr a Gregorian date in any time zone of the format
     *                              {@link #DATE_FORMAT_SECONDS}.
     * @param expectedJulianDate    A Julian date which should be equivalent to the Gregorian
     *                              date.
     */
    private static void testGregorianToJulianConversion(String inputGregorianDateStr,
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
     * @param expectedGregorianDateStr a Gregorian date in any time zone of the format
     *                                 {@link #DATE_FORMAT_SECONDS}.
     * @param inputJulianDate          A Julian date which should be equivalent to the Gregorian
     *                                 date.
     */
    private static void testJulianToGregorianConversion(double inputJulianDate,
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
     * @param timeZoneString        a valid Java timezone
     * @param inputDayString        a day in the format {@link #DATE_FORMAT_DAY}
     * @param inputLatitude         the latitude of a given location
     * @param inputLongitude        the longitude of a given location (West is negative).
     * @param expectedSunriseString the time the sunrise is expected, in the format HH:mm. The
     *                              time should be in the timezone of the parameter
     *                              timeZoneString.
     * @param expectedSunsetString  the time the sunset is expected, in the format HH:mm. The time
     *                              should be in the timezone of the parameter timeZoneString.
     */
    static void testSunriseSunset(String timeZoneString,
                                  String inputDayString, double inputLatitude, double inputLongitude,
                                  String expectedSunriseString, String expectedSunsetString) {
        testSunriseSunset(timeZoneString, inputDayString, inputLatitude, inputLongitude, expectedSunriseString, expectedSunsetString, DEFAULT_ACCURACY_MINUTES);
    }

    /**
     * @param timeZoneString        a valid Java timezone
     * @param inputDayString        a day in the format {@link #DATE_FORMAT_DAY}
     * @param inputLatitude         the latitude of a given location
     * @param inputLongitude        the longitude of a given location (West is negative).
     * @param expectedSunriseString the time the sunrise is expected, in the format HH:mm. The
     *                              time should be in the timezone of the parameter
     *                              timeZoneString.
     * @param expectedSunsetString  the time the sunset is expected, in the format HH:mm. The time
     *                              should be in the timezone of the parameter timeZoneString.
     * @param accuracyMinutes       the difference between the expected and calculated sunrise and sunset times we allow for the test to pass.
     */
    static void testSunriseSunset(String timeZoneString,
                                  String inputDayString, double inputLatitude, double inputLongitude,
                                  String expectedSunriseString, String expectedSunsetString, double accuracyMinutes) {
        Calendar inputDay = parseDate(timeZoneString, inputDayString);

        // Calculate the actual sunrise and sunset times.
        Calendar[] actualSunriseSunset = SunriseSunset.getSunriseSunset(
                inputDay, inputLatitude, inputLongitude);

        Calendar[] actualSunriseSunsetWithAltitude = SunriseSunset.getSunriseSunset(
                inputDay, inputLatitude, inputLongitude, SunriseSunset.SUN_ALTITUDE_SUNRISE_SUNSET);

        Assert.assertArrayEquals(actualSunriseSunset, actualSunriseSunsetWithAltitude);

        // Compare the calculated times with the expected ones.
        validateSunriseSunset(actualSunriseSunset, timeZoneString, inputDayString, expectedSunriseString, expectedSunsetString, accuracyMinutes);
    }

    /**
     * Like {@link #testSunriseSunset(String, String, double, double, String, String, double)}, but the expected sunrise and sunset strings contain seconds.
     *
     * @param expectedSunriseString the time the sunrise is expected, in the format HH:mm:ss z.
     * @param expectedSunsetString  the time the sunset is expected, in the format HH:mm:ss z.
     * @see {@link #testSunriseSunset(String, String, double, double, String, String, double)}
     */
    static void testSunriseSunsetSeconds(String timeZoneString,
                                         String inputDayString, double inputLatitude, double inputLongitude,
                                         String expectedSunriseString, String expectedSunsetString, double accuracyMinutes) {
        Calendar inputDay = parseDate(timeZoneString, inputDayString);

        // Calculate the actual sunrise and sunset times.
        Calendar[] actualSunriseSunset = SunriseSunset.getSunriseSunset(
                inputDay, inputLatitude, inputLongitude);

        Calendar[] actualSunriseSunsetWithAltitude = SunriseSunset.getSunriseSunset(
                inputDay, inputLatitude, inputLongitude, SunriseSunset.SUN_ALTITUDE_SUNRISE_SUNSET);

        Assert.assertArrayEquals(actualSunriseSunset, actualSunriseSunsetWithAltitude);

        // Compare the calculated times with the expected ones.
        validateSunriseSunsetSeconds(actualSunriseSunset, timeZoneString, inputDayString, expectedSunriseString, expectedSunsetString, accuracyMinutes);
    }

    /**
     * @param timeZoneString     a valid Java timezone
     * @param inputDayString     a day in the format {@link #DATE_FORMAT_DAY}
     * @param inputLatitude      the latitude of a given location
     * @param inputLongitude     the longitude of a given location (West is negative).
     * @param expectedNoonString the time the noon is expected, in the format HH:mm. The
     *                           time should be in the timezone of the parameter
     *                           timeZoneString.
     */
    static void testSolarNoon(String timeZoneString,
                              String inputDayString, double inputLatitude, double inputLongitude,
                              String expectedNoonString) {

        Calendar inputDay = parseDate(timeZoneString, inputDayString);

        // Calculate the actual solar noon
        Calendar actualSolarNoon = SunriseSunset.getSolarNoon(inputDay, inputLatitude, inputLongitude);

        // Compare the calculated times with the expected ones.
        validateSolarNoon(actualSolarNoon, timeZoneString, inputDayString, expectedNoonString);
    }

    static void testDayLength(String timeZoneString,
                              String inputDayString, double inputLatitude, double inputLongitude,
                              long expectedDayLength) {

        Calendar inputDay = parseDate(timeZoneString, inputDayString);
        Assert.assertEquals(expectedDayLength, SunriseSunset.getDayLength(inputDay, inputLatitude, inputLongitude));
    }

    static Calendar parseDate(String timeZoneString, String inputDayString) {
        TimeZone tz = TimeZone.getTimeZone(timeZoneString);

        // Create a Calendar for noon, in the given timezone for the given day.
        Calendar inputDay = parseDate(tz, DATE_FORMAT_DAY, inputDayString);
        if (inputDay != null) {
            inputDay.set(Calendar.HOUR_OF_DAY, 12);
            inputDay.set(Calendar.MINUTE, 0);
            inputDay.set(Calendar.SECOND, 0);
            inputDay.set(Calendar.MILLISECOND, 0);
        }

        return inputDay;
    }

    static void validateSunriseSunset(Calendar[] actualSunriseSunset, String timeZoneString, String inputDayString,
                                      String expectedSunriseString, String expectedSunsetString, double accuracyMinutes) {

        if (expectedSunriseString == null || expectedSunsetString == null) {
            Assert.assertNull(actualSunriseSunset);
            return;
        }

        Calendar actualSunrise = actualSunriseSunset[0];
        Calendar actualSunset = actualSunriseSunset[1];

        String actualSunriseString = format(DATE_FORMAT_MINUTES, actualSunrise);
        String actualSunsetString = format(DATE_FORMAT_MINUTES, actualSunset);

        TimeZone tz = TimeZone.getTimeZone(timeZoneString);
        Calendar expectedSunrise = parseDate(tz, DATE_FORMAT_MINUTES,
                inputDayString + " " + expectedSunriseString);
        Calendar expectedSunset = parseDate(tz, DATE_FORMAT_MINUTES,
                inputDayString + " " + expectedSunsetString);

        // Compare the actual and expected sunrise/sunset times. Allow a margin
        // of error.
        assertEqualsOrAlmostEquals(expectedSunrise, expectedSunriseString,
                actualSunrise, actualSunriseString, (int) (accuracyMinutes * 60000));
        assertEqualsOrAlmostEquals(expectedSunset, expectedSunsetString,
                actualSunset, actualSunsetString, (int) (accuracyMinutes * 60000));

    }

    /**
     * Like {@link #validateSunriseSunset(Calendar[], String, String, String, String, double)} but the expected time strings are in seconds.
     *
     * @param expectedSunriseString the time the sunrise is expected, in the format HH:mm:ss z.
     * @param expectedSunsetString  the time the sunset is expected, in the format HH:mm:ss z.
     */
    private static void validateSunriseSunsetSeconds(Calendar[] actualSunriseSunset, String timeZoneString, String inputDayString,
                                                     String expectedSunriseString, String expectedSunsetString, double accuracyMinutes) {

        if (expectedSunriseString == null || expectedSunsetString == null) {
            Assert.assertNull(actualSunriseSunset);
            return;
        }

        Calendar actualSunrise = actualSunriseSunset[0];
        Calendar actualSunset = actualSunriseSunset[1];

        String actualSunriseString = format(DATE_FORMAT_SECONDS, actualSunrise);
        String actualSunsetString = format(DATE_FORMAT_SECONDS, actualSunset);

        TimeZone tz = TimeZone.getTimeZone(timeZoneString);
        Calendar expectedSunrise = parseDate(tz, DATE_FORMAT_SECONDS,
                inputDayString + " " + expectedSunriseString);
        Calendar expectedSunset = parseDate(tz, DATE_FORMAT_SECONDS,
                inputDayString + " " + expectedSunsetString);

        // Compare the actual and expected sunrise/sunset times. Allow a margin
        // of error.
        assertEqualsOrAlmostEquals(expectedSunrise, expectedSunriseString,
                actualSunrise, actualSunriseString, (int) (accuracyMinutes * 60000));
        assertEqualsOrAlmostEquals(expectedSunset, expectedSunsetString,
                actualSunset, actualSunsetString, (int) (accuracyMinutes * 60000));

    }

    private static void validateSolarNoon(Calendar actualSolarNoon, String timeZoneString, String inputDayString,
                                          String expectedSolarNoonString) {

        if (expectedSolarNoonString == null) {
            if (actualSolarNoon != null) {
                Assert.fail("Expected no value for solar noon for " + timeZoneString + " " + inputDayString + ", but got " + actualSolarNoon.getTime());
            }
            return;
        }

        Assert.assertNotNull("Expected a value for solar noon for " + timeZoneString + " " + inputDayString, actualSolarNoon);

        String actualSolarNoonString = format(DATE_FORMAT_MINUTES, actualSolarNoon);

        TimeZone tz = TimeZone.getTimeZone(timeZoneString);
        Calendar expectedSolarNoon = parseDate(tz, DATE_FORMAT_MINUTES,
                inputDayString + " " + expectedSolarNoonString);

        // Compare the actual and expected solar noon times. Allow a margin
        // of error.
        assertEqualsOrAlmostEquals(expectedSolarNoon, expectedSolarNoonString,
                actualSolarNoon, actualSolarNoonString, (int) (DEFAULT_ACCURACY_MINUTES * 60000));
    }

    static Calendar parseDate(TimeZone tz, SimpleDateFormat format,
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

    private static String format(SimpleDateFormat format, Calendar calendar) {
        SimpleDateFormat formatCopy = (SimpleDateFormat) format.clone();
        formatCopy.setTimeZone(calendar.getTimeZone());
        return formatCopy.format(calendar.getTime());
    }

    /**
     * Raises an assertion failure if the two Calendars have different time
     * zones or are not close enough in time.
     *
     * @param cal1            One date, compared with cal2
     * @param calString1      readable string for cal1, used in the error message if the
     *                        Calendars are not equal
     * @param cal2            Another date, compared with cal1
     * @param calString2      readable string for cal2, used in the error message if the
     *                        Calendars are not equal
     * @param toleranceMillis The difference between the timeInMillis of the two calendar
     *                        objects must be no greater than toleranceMillis
     */
    private static void assertEqualsOrAlmostEquals(Calendar cal1, String calString1,
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

    static void testIsDayNightTwilight(String timeZoneString, String inputDayString,
                               String dayTime, String eveningTwilightTime, String nightTime, String morningTwilightTime,
                               double latitude, double longitude) {
        testIsDay(timeZoneString, inputDayString + " " + dayTime, latitude, longitude, true);
        testIsNight(timeZoneString, inputDayString + " " + dayTime, latitude, longitude, false);
        SunriseSunsetTwilightTestUtils.testIsTwilight(timeZoneString, inputDayString + " " + dayTime, latitude, longitude, false);

        testIsDay(timeZoneString, inputDayString + " " + eveningTwilightTime, latitude, longitude, false);
        testIsNight(timeZoneString, inputDayString + " " + eveningTwilightTime, latitude, longitude, false);
        SunriseSunsetTwilightTestUtils.testIsTwilight(timeZoneString, inputDayString + " " + eveningTwilightTime, latitude, longitude, true);

        testIsDay(timeZoneString, inputDayString + " " + nightTime, latitude, longitude, false);
        testIsNight(timeZoneString, inputDayString + " " + nightTime, latitude, longitude, true);
        SunriseSunsetTwilightTestUtils.testIsTwilight(timeZoneString, inputDayString + " " + nightTime, latitude, longitude, false);

        testIsDay(timeZoneString, inputDayString + " " + morningTwilightTime, latitude, longitude, false);
        testIsNight(timeZoneString, inputDayString + " " + morningTwilightTime, latitude, longitude, false);
        SunriseSunsetTwilightTestUtils.testIsTwilight(timeZoneString, inputDayString + " " + morningTwilightTime, latitude, longitude, true);
    }

    static void testGetDayPeriod(String timeZoneString, String inputDayString,
                                 String astronomicalTwilightMorningTime, String nauticalTwilightMorningTime, String civilTwilightMorningTime,
                                 String dayTime,
                                 String civilTwilightEveningTime, String nauticalTwilightEveningTime, String astronomicalTwilightEveningTime,
                                 String nightTime,
                                 double latitude, double longitude) {
        testGetDayPeriod(timeZoneString, inputDayString + " " + astronomicalTwilightMorningTime, latitude, longitude, SunriseSunset.DayPeriod.ASTRONOMICAL_TWILIGHT);
        testGetDayPeriod(timeZoneString, inputDayString + " " + nauticalTwilightMorningTime, latitude, longitude, SunriseSunset.DayPeriod.NAUTICAL_TWILIGHT);
        testGetDayPeriod(timeZoneString, inputDayString + " " + civilTwilightMorningTime, latitude, longitude, SunriseSunset.DayPeriod.CIVIL_TWILIGHT);
        testGetDayPeriod(timeZoneString, inputDayString + " " + dayTime, latitude, longitude, SunriseSunset.DayPeriod.DAY);
        testGetDayPeriod(timeZoneString, inputDayString + " " + civilTwilightEveningTime, latitude, longitude, SunriseSunset.DayPeriod.CIVIL_TWILIGHT);
        testGetDayPeriod(timeZoneString, inputDayString + " " + nauticalTwilightEveningTime, latitude, longitude, SunriseSunset.DayPeriod.NAUTICAL_TWILIGHT);
        testGetDayPeriod(timeZoneString, inputDayString + " " + astronomicalTwilightEveningTime, latitude, longitude, SunriseSunset.DayPeriod.ASTRONOMICAL_TWILIGHT);
        testGetDayPeriod(timeZoneString, inputDayString + " " + nightTime, latitude, longitude, SunriseSunset.DayPeriod.NIGHT);
    }

    private static void testIsDay(String timeZoneString, String inputDayString,
                                    double latitude, double longitude, boolean expectedIsDay) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
        Calendar inputDay = SunriseSunsetTestUtils.parseDate(timeZone, SunriseSunsetTestUtils.DATE_FORMAT_MINUTES, inputDayString);
        boolean actualIsDay = SunriseSunset.isDay(inputDay, latitude, longitude);
        Assert.assertEquals(expectedIsDay, actualIsDay);
    }

    private static void testIsNight(String timeZoneString, String inputNightString,
                          double latitude, double longitude, boolean expectedIsNight) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
        Calendar inputNight = SunriseSunsetTestUtils.parseDate(timeZone, SunriseSunsetTestUtils.DATE_FORMAT_MINUTES, inputNightString);
        boolean actualIsNight = SunriseSunset.isNight(inputNight, latitude, longitude);
        Assert.assertEquals(expectedIsNight, actualIsNight);
    }

    static void testGetDayPeriod(String timeZoneString, String timeString, double latitude, double longitude, SunriseSunset.DayPeriod expectedDayPeriod) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
        Calendar calendar = SunriseSunsetTestUtils.parseDate(timeZone, SunriseSunsetTestUtils.DATE_FORMAT_MINUTES, timeString);
        SunriseSunset.DayPeriod actualDayPeriod = SunriseSunset.getDayPeriod(calendar, latitude, longitude);
        Assert.assertEquals(expectedDayPeriod, actualDayPeriod);
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
     * @param name                  the name of the location. Used for logging and shown in the
     *                              error if an assertion fails.
     * @param timeZoneString        a valid Java timezone
     * @param inputLatitude         the latitude of a given location
     * @param inputLongitude        the longitude of a given location (West is negative).
     * @param earliestSunriseString a time in the format HH:mm for the earliest time sunrise
     *                              occurs throughout the year, at this location.
     * @param latestSunriseString   a time in the format HH:mm for the latest time sunrise occurs
     *                              throughout the year, at this location.
     * @param earliestSunsetString  a time in the format HH:mm for the latest time sunset occurs
     *                              throughout the year, at this location.
     * @param latestSunsetString    a time in the format HH:mm for the latest time sunset occurs
     *                              throughout the year, at this location.
     */
    static void testDayOrNight(String name, String timeZoneString,
                               double inputLatitude, double inputLongitude,
                               String earliestSunriseString, String latestSunriseString,
                               String earliestSunsetString, String latestSunsetString) {
        boolean isDay = SunriseSunset.isDay(inputLatitude, inputLongitude);
        boolean isNight = SunriseSunset.isNight(inputLatitude, inputLongitude);
        Assert.assertTrue("isDay and isNight must return opposite values", isDay != isNight);

        Calendar now = Calendar.getInstance(TimeZone
                .getTimeZone(timeZoneString));
        String nowString = format(DATE_FORMAT_MINUTES, now);

        if (earliestSunriseString == null || latestSunriseString == null
                || earliestSunsetString == null || latestSunsetString == null) {
            // For extreme latitudes around June/December, we'll just return here.  The previous lines at least make
            // sure the library doesn't crash.
            return;
        }
        Calendar earliestSunrise = getCalendarAtTime(now, earliestSunriseString);
        Calendar latestSunrise = getCalendarAtTime(now, latestSunriseString);
        Calendar earliestSunset = getCalendarAtTime(now, earliestSunsetString);
        Calendar latestSunset = getCalendarAtTime(now, latestSunsetString);
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
     * @param day        a date in any timezone.
     * @param timeString a time in format HH:mm
     * @return a new Calendar object with the same day as the given day, and the
     * hours and minutes in timeString. Seconds and milliseconds are set
     * to zero.
     */
    private static Calendar getCalendarAtTime(Calendar day, String timeString) {

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
