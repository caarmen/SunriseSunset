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

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Helper methods for twilight unit tests for the Sunrise Sunset Calculator.
 */
class SunriseSunsetTwilightTestUtils {


    static void testIsCivilTwilight(String timeZoneString, String inputDayString,
                                    String timeBeforeTwilightDusk, String timeDuringEveningTwilight, String timeAfterTwilightDusk,
                                    String timeBeforeTwilightDawn, String timeDuringMorningTwilight, String timeAfterTwilightDawn,
                                    double latitude, double longitude) {
        testIsCivilTwilight(timeZoneString, inputDayString + " " + timeBeforeTwilightDusk, latitude, longitude, false);
        testIsCivilTwilight(timeZoneString, inputDayString + " " + timeDuringEveningTwilight, latitude, longitude, true);
        testIsCivilTwilight(timeZoneString, inputDayString + " " + timeAfterTwilightDusk, latitude, longitude, false);
        testIsCivilTwilight(timeZoneString, inputDayString + " " + timeBeforeTwilightDawn, latitude, longitude, false);
        testIsCivilTwilight(timeZoneString, inputDayString + " " + timeDuringMorningTwilight, latitude, longitude, true);
        testIsCivilTwilight(timeZoneString, inputDayString + " " + timeAfterTwilightDawn, latitude, longitude, false);
    }

    static void testIsNauticalTwilight(String timeZoneString, String inputDayString,
                                       String timeBeforeTwilightDusk, String timeDuringEveningTwilight, String timeAfterTwilightDusk,
                                       String timeBeforeTwilightDawn, String timeDuringMorningTwilight, String timeAfterTwilightDawn,
                                       double latitude, double longitude) {
        testIsNauticalTwilight(timeZoneString, inputDayString + " " + timeBeforeTwilightDusk, latitude, longitude, false);
        testIsNauticalTwilight(timeZoneString, inputDayString + " " + timeDuringEveningTwilight, latitude, longitude, true);
        testIsNauticalTwilight(timeZoneString, inputDayString + " " + timeAfterTwilightDusk, latitude, longitude, false);
        testIsNauticalTwilight(timeZoneString, inputDayString + " " + timeBeforeTwilightDawn, latitude, longitude, false);
        testIsNauticalTwilight(timeZoneString, inputDayString + " " + timeDuringMorningTwilight, latitude, longitude, true);
        testIsNauticalTwilight(timeZoneString, inputDayString + " " + timeAfterTwilightDawn, latitude, longitude, false);
    }

    static void testIsAstronomicalTwilight(String timeZoneString, String inputDayString,
                                           String timeBeforeTwilightDusk, String timeDuringEveningTwilight, String timeAfterTwilightDusk,
                                           String timeBeforeTwilightDawn, String timeDuringMorningTwilight, String timeAfterTwilightDawn,
                                           double latitude, double longitude) {
        testIsAstronomicalTwilight(timeZoneString, inputDayString + " " + timeBeforeTwilightDusk, latitude, longitude, false);
        testIsAstronomicalTwilight(timeZoneString, inputDayString + " " + timeDuringEveningTwilight, latitude, longitude, true);
        testIsAstronomicalTwilight(timeZoneString, inputDayString + " " + timeAfterTwilightDusk, latitude, longitude, false);
        testIsAstronomicalTwilight(timeZoneString, inputDayString + " " + timeBeforeTwilightDawn, latitude, longitude, false);
        testIsAstronomicalTwilight(timeZoneString, inputDayString + " " + timeDuringMorningTwilight, latitude, longitude, true);
        testIsAstronomicalTwilight(timeZoneString, inputDayString + " " + timeAfterTwilightDawn, latitude, longitude, false);
    }

    static void testIsTwilight(String timeZoneString, String inputDayString,
                                           String timeBeforeTwilightDusk, String timeDuringEveningTwilight, String timeAfterTwilightDusk,
                                           String timeBeforeTwilightDawn, String timeDuringMorningTwilight, String timeAfterTwilightDawn,
                                           double latitude, double longitude) {
        testIsTwilight(timeZoneString, inputDayString + " " + timeBeforeTwilightDusk, latitude, longitude, false);
        testIsTwilight(timeZoneString, inputDayString + " " + timeDuringEveningTwilight, latitude, longitude, true);
        testIsTwilight(timeZoneString, inputDayString + " " + timeAfterTwilightDusk, latitude, longitude, false);
        testIsTwilight(timeZoneString, inputDayString + " " + timeBeforeTwilightDawn, latitude, longitude, false);
        testIsTwilight(timeZoneString, inputDayString + " " + timeDuringMorningTwilight, latitude, longitude, true);
        testIsTwilight(timeZoneString, inputDayString + " " + timeAfterTwilightDawn, latitude, longitude, false);
    }

    static void testIsCivilTwilight(String timeZoneString, String inputDayString,
                                    double latitude, double longitude, boolean expectedIsCivilTwilight) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
        Calendar inputDay = SunriseSunsetTestUtils.parseDate(timeZone, SunriseSunsetTestUtils.DATE_FORMAT_MINUTES, inputDayString);
        boolean actualIsCivilTwilight = SunriseSunset.isCivilTwilight(inputDay, latitude, longitude);
        Assert.assertEquals(expectedIsCivilTwilight, actualIsCivilTwilight);
    }

    static void testIsNauticalTwilight(String timeZoneString, String inputDayString,
                                       double latitude, double longitude, boolean expectedIsNauticalTwilight) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
        Calendar inputDay = SunriseSunsetTestUtils.parseDate(timeZone, SunriseSunsetTestUtils.DATE_FORMAT_MINUTES, inputDayString);
        boolean actualIsNauticalTwilight = SunriseSunset.isNauticalTwilight(inputDay, latitude, longitude);
        Assert.assertEquals(expectedIsNauticalTwilight, actualIsNauticalTwilight);
    }

    static void testIsAstronomicalTwilight(String timeZoneString, String inputDayString,
                                           double latitude, double longitude, boolean expectedIsAstronomicalTwilight) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
        Calendar inputDay = SunriseSunsetTestUtils.parseDate(timeZone, SunriseSunsetTestUtils.DATE_FORMAT_MINUTES, inputDayString);
        boolean actualIsAstronomicalTwilight = SunriseSunset.isAstronomicalTwilight(inputDay, latitude, longitude);
        Assert.assertEquals(expectedIsAstronomicalTwilight, actualIsAstronomicalTwilight);
    }

    static void testIsTwilight(String timeZoneString, String inputDayString,
                                           double latitude, double longitude, boolean expectedIsTwilight) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
        Calendar inputDay = SunriseSunsetTestUtils.parseDate(timeZone, SunriseSunsetTestUtils.DATE_FORMAT_MINUTES, inputDayString);
        boolean actualIsTwilight = SunriseSunset.isTwilight(inputDay, latitude, longitude);
        Assert.assertEquals(expectedIsTwilight, actualIsTwilight);
    }

    /**
     * @param timeZoneString              a valid Java timezone
     * @param inputDayString              a day in the format {@link SunriseSunsetTestUtils#DATE_FORMAT_DAY}
     * @param inputLatitude               the latitude of a given location
     * @param inputLongitude              the longitude of a given location (West is negative).
     * @param expectedTwilightEndString   the time the twilight is expected to end, in the format HH:mm. The
     *                                    time should be in the timezone of the parameter
     *                                    timeZoneString.
     * @param expectedTwilightBeginString the time the twilight is expected to begin, in the format HH:mm. The time
     *                                    should be in the timezone of the parameter timeZoneString.
     */
    static void testCivilTwilight(String timeZoneString,
                                  String inputDayString, double inputLatitude, double inputLongitude,
                                  String expectedTwilightEndString, String expectedTwilightBeginString) {
        testCivilTwilight(timeZoneString, inputDayString, inputLatitude, inputLongitude, expectedTwilightEndString, expectedTwilightBeginString, SunriseSunsetTestUtils.DEFAULT_ACCURACY_MINUTES);
    }

    /**
     * @param timeZoneString              a valid Java timezone
     * @param inputDayString              a day in the format {@link SunriseSunsetTestUtils#DATE_FORMAT_DAY}
     * @param inputLatitude               the latitude of a given location
     * @param inputLongitude              the longitude of a given location (West is negative).
     * @param expectedTwilightEndString   the time the twilight is expected to end, in the format HH:mm. The
     *                                    time should be in the timezone of the parameter
     *                                    timeZoneString.
     * @param expectedTwilightBeginString the time the twilight is expected to begin, in the format HH:mm. The time
     *                                    should be in the timezone of the parameter timeZoneString.
     * @param accuracyMinutes             the difference between the expected and calculated twilight times we allow for the test to pass.
     */
    static void testCivilTwilight(String timeZoneString,
                                  String inputDayString, double inputLatitude, double inputLongitude,
                                  String expectedTwilightEndString, String expectedTwilightBeginString, double accuracyMinutes) {
        Calendar inputDay = SunriseSunsetTestUtils.parseDate(timeZoneString, inputDayString);

        // Calculate the actual sunrise and sunset times.
        Calendar[] actualTwilight = SunriseSunset.getCivilTwilight(
                inputDay, inputLatitude, inputLongitude);
        Calendar[] actualSunriseSunset = SunriseSunset.getSunriseSunset(inputDay, inputLatitude, inputLongitude, SunriseSunset.SUN_ALTITUDE_CIVIL_TWILIGHT);

        // Make sure the results with 12 degrees correspond to the results of astronomical twilight
        Assert.assertArrayEquals(actualTwilight, actualSunriseSunset);

        // Compare the calculated times with the expected ones.
        SunriseSunsetTestUtils.validateSunriseSunset(actualTwilight, timeZoneString, inputDayString, expectedTwilightEndString, expectedTwilightBeginString, accuracyMinutes);
    }

    /**
     * @param timeZoneString              a valid Java timezone
     * @param inputDayString              a day in the format {@link SunriseSunsetTestUtils#DATE_FORMAT_DAY}
     * @param inputLatitude               the latitude of a given location
     * @param inputLongitude              the longitude of a given location (West is negative).
     * @param expectedTwilightEndString   the time the twilight is expected to end, in the format HH:mm. The
     *                                    time should be in the timezone of the parameter
     *                                    timeZoneString.
     * @param expectedTwilightBeginString the time the twilight is expected to begin, in the format HH:mm. The time
     *                                    should be in the timezone of the parameter timeZoneString.
     */
    static void testNauticalTwilight(String timeZoneString,
                                     String inputDayString, double inputLatitude, double inputLongitude,
                                     String expectedTwilightEndString, String expectedTwilightBeginString) {
        testNauticalTwilight(timeZoneString, inputDayString, inputLatitude, inputLongitude, expectedTwilightEndString, expectedTwilightBeginString, SunriseSunsetTestUtils.DEFAULT_ACCURACY_MINUTES);
    }

    /**
     * @param timeZoneString              a valid Java timezone
     * @param inputDayString              a day in the format {@link SunriseSunsetTestUtils#DATE_FORMAT_DAY}
     * @param inputLatitude               the latitude of a given location
     * @param inputLongitude              the longitude of a given location (West is negative).
     * @param expectedTwilightEndString   the time the twilight is expected to end, in the format HH:mm. The
     *                                    time should be in the timezone of the parameter
     *                                    timeZoneString.
     * @param expectedTwilightBeginString the time the twilight is expected to begin, in the format HH:mm. The time
     *                                    should be in the timezone of the parameter timeZoneString.
     * @param accuracyMinutes             the difference between the expected and calculated twilight times we allow for the test to pass.
     */
    static void testNauticalTwilight(String timeZoneString,
                                     String inputDayString, double inputLatitude, double inputLongitude,
                                     String expectedTwilightEndString, String expectedTwilightBeginString, double accuracyMinutes) {
        Calendar inputDay = SunriseSunsetTestUtils.parseDate(timeZoneString, inputDayString);

        // Calculate the actual sunrise and sunset times.
        Calendar[] actualTwilight = SunriseSunset.getNauticalTwilight(
                inputDay, inputLatitude, inputLongitude);
        Calendar[] actualSunriseSunset = SunriseSunset.getSunriseSunset(inputDay, inputLatitude, inputLongitude, SunriseSunset.SUN_ALTITUDE_NAUTICAL_TWILIGHT);

        // Make sure the results with 12 degrees correspond to the results of astronomical twilight
        Assert.assertArrayEquals(actualTwilight, actualSunriseSunset);

        // Compare the calculated times with the expected ones.
        SunriseSunsetTestUtils.validateSunriseSunset(actualTwilight, timeZoneString, inputDayString, expectedTwilightEndString, expectedTwilightBeginString, accuracyMinutes);
    }

    /**
     * @param timeZoneString              a valid Java timezone
     * @param inputDayString              a day in the format {@link SunriseSunsetTestUtils#DATE_FORMAT_DAY}
     * @param inputLatitude               the latitude of a given location
     * @param inputLongitude              the longitude of a given location (West is negative).
     * @param expectedTwilightEndString   the time the twilight is expected to end, in the format HH:mm. The
     *                                    time should be in the timezone of the parameter
     *                                    timeZoneString.
     * @param expectedTwilightBeginString the time the twilight is expected to begin, in the format HH:mm. The time
     *                                    should be in the timezone of the parameter timeZoneString.
     */
    static void testAstronomicalTwilight(String timeZoneString,
                                         String inputDayString, double inputLatitude, double inputLongitude,
                                         String expectedTwilightEndString, String expectedTwilightBeginString) {
        testAstronomicalTwilight(timeZoneString, inputDayString, inputLatitude, inputLongitude, expectedTwilightEndString, expectedTwilightBeginString, SunriseSunsetTestUtils.DEFAULT_ACCURACY_MINUTES);
    }

    /**
     * @param timeZoneString              a valid Java timezone
     * @param inputDayString              a day in the format {@link SunriseSunsetTestUtils#DATE_FORMAT_DAY
     * @param inputLatitude               the latitude of a given location
     * @param inputLongitude              the longitude of a given location (West is negative).
     * @param expectedTwilightEndString   the time the twilight is expected to end, in the format HH:mm. The
     *                                    time should be in the timezone of the parameter
     *                                    timeZoneString.
     * @param expectedTwilightBeginString the time the twilight is expected to begin, in the format HH:mm. The time
     *                                    should be in the timezone of the parameter timeZoneString.
     * @param accuracyMinutes             the difference between the expected and calculated twilight times we allow for the test to pass.
     */
    static void testAstronomicalTwilight(String timeZoneString,
                                         String inputDayString, double inputLatitude, double inputLongitude,
                                         String expectedTwilightEndString, String expectedTwilightBeginString, double accuracyMinutes) {
        Calendar inputDay = SunriseSunsetTestUtils.parseDate(timeZoneString, inputDayString);

        // Calculate the actual sunrise and sunset times.
        Calendar[] actualTwilight = SunriseSunset.getAstronomicalTwilight(inputDay, inputLatitude, inputLongitude);
        Calendar[] actualSunriseSunset = SunriseSunset.getSunriseSunset(inputDay, inputLatitude, inputLongitude, SunriseSunset.SUN_ALTITUDE_ASTRONOMICAL_TWILIGHT);

        // Make sure the results with 18 degrees correspond to the results of astronomical twilight
        Assert.assertArrayEquals(actualTwilight, actualSunriseSunset);

        // Compare the calculated times with the expected ones.
        SunriseSunsetTestUtils.validateSunriseSunset(actualTwilight, timeZoneString, inputDayString, expectedTwilightEndString, expectedTwilightBeginString, accuracyMinutes);
    }

}
