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

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.TimeZone;

/**
 * Unit tests for the Sunrise Sunset Calculator.
 *
 * @author Carmen Alvarez
 */
public class SunriseSunsetTest {

    /**
     * Not a unit test, but helpful for troubleshooting and adding new unit
     * tests. This method logs the list of Java timezone ids.
     */
    @Ignore
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
        SunriseSunsetTestUtils.testDateConversion("19760302 15:15:45 UTC", 2442840.1359375);
        SunriseSunsetTestUtils.testDateConversion("19760302 10:15:45 EST", 2442840.1359375);
        SunriseSunsetTestUtils.testDateConversion("19780427 12:00:42 CET", 2443625.9588194);
        SunriseSunsetTestUtils.testDateConversion("19010101 23:59:59 UTC", 2415386.4999884);
        SunriseSunsetTestUtils.testDateConversion("19010101 09:00:00 UTC", 2415385.875);
        SunriseSunsetTestUtils.testDateConversion("19190101 00:00:00 AKST", 2421959.916667);
        // After WWII, AKST was officially (not in practice) two hours slower
        // than PST (GMT-10). In practice Alaska had 4 time zones:
        // Bering Time, Alaska Time, Yukon Time, and Pacific Time.
        // http://www.alaskahistoricalsociety.org/index.cfm/discover-alaska/Glimpses-of-the-Past/98
        SunriseSunsetTestUtils.testDateConversion("19460101 00:00:00 AKST", 2431821.916667);
        SunriseSunsetTestUtils.testDateConversion("19670101 00:00:00 AKST", 2439491.916667);
        SunriseSunsetTestUtils.testDateConversion("19680101 00:00:00 AKST", 2439856.916667);
        // After April 1968, Alaska had the four official time zones
        // with AKST being UTC-10.
        SunriseSunsetTestUtils.testDateConversion("19690101 00:00:00 AKST", 2440222.916667);
        SunriseSunsetTestUtils.testDateConversion("19830101 00:00:00 AKST", 2445335.916667);

        // In October 1983, Alaska aligned with Yukon time (UTC-9)
        SunriseSunsetTestUtils.testDateConversion("19840101 00:00:00 AKST", 2445700.875);
        SunriseSunsetTestUtils.testDateConversion("19850101 00:00:00 AKST", 2446066.875);
        SunriseSunsetTestUtils.testDateConversion("19900101 00:00:00 AKST", 2447892.875);
        SunriseSunsetTestUtils.testDateConversion("20000101 00:00:00 AKST", 2451544.875);
        SunriseSunsetTestUtils.testDateConversion("20130101 00:00:00 AKST", 2456293.875);

        SunriseSunsetTestUtils.testDateConversion("19010101 00:00:00 UTC", 2415385.5);
        SunriseSunsetTestUtils.testDateConversion("19001231 23:59:59 UTC", 2415385.4999884);
        SunriseSunsetTestUtils.testDateConversion("19000701 00:00:00 UTC", 2415201.5);
        SunriseSunsetTestUtils.testDateConversion("19000401 00:00:00 UTC", 2415110.5);
        SunriseSunsetTestUtils.testDateConversion("19000301 00:00:00 UTC", 2415079.5);
        SunriseSunsetTestUtils.testDateConversion("19000228 00:00:00 UTC", 2415078.5);
        SunriseSunsetTestUtils.testDateConversion("19000225 00:00:00 UTC", 2415075.5);
        SunriseSunsetTestUtils.testDateConversion("19000221 00:00:00 UTC", 2415071.5);
        SunriseSunsetTestUtils.testDateConversion("19000214 00:00:00 UTC", 2415064.5);
        SunriseSunsetTestUtils.testDateConversion("19000201 00:00:00 UTC", 2415051.5);
        SunriseSunsetTestUtils.testDateConversion("18800516 10:19:00 UTC", 2407851.929861111);
        SunriseSunsetTestUtils.testDateConversion("18010101 09:00:00 UTC", 2378861.875);
    }

    /**
     * Test conversion between Gregorian and Julian dates (both ways).
     */
    @Test
    public void testDateConversions2() {
        SunriseSunsetTestUtils.testDateConversion("19000131 00:03:18 UTC", 2415050.502291667);
        SunriseSunsetTestUtils.testDateConversion("18500712 00:17:18 UTC", 2396950.512013889);
    }

    /**
     * Test the time of sunrise and sunset for some locations.
     */
    @Test
    public void testSunriseSunset() {

        SunriseSunsetTestUtils.testSunriseSunset("PST", "20130120", 34.0522, -118.2437, "06:57", "17:11");
        SunriseSunsetTestUtils.testSunriseSunset("CET", "20130120", 48.8567, 2.351, "08:35", "17:28");
        SunriseSunsetTestUtils.testSunriseSunset("Australia/Sydney", "20121225", -33.86, 151.2111, "05:43", "20:07");
        SunriseSunsetTestUtils.testSunriseSunset("Japan", "20130501", 35.6938, 139.7036, "04:49", "18:27");
        SunriseSunsetTestUtils.testSunriseSunset("Europe/Dublin", "20130605", 53.3441, -6.2675, "05:01", "21:46");
        SunriseSunsetTestUtils.testSunriseSunset("CST", "20130622", 41.8781, -87.6298, "05:16", "20:29");
        SunriseSunsetTestUtils.testSunriseSunset("Pacific/Honolulu", "20150827", 21.3069, -157.8583, "06:13", "18:53");
        SunriseSunsetTestUtils.testSunriseSunset("America/Argentina/Buenos_Aires", "20130501", -34.6092, -58.3732, "07:29", "18:12");
        SunriseSunsetTestUtils.testSunriseSunset("America/Argentina/Buenos_Aires", "20131019", -34.6092, -58.3732, "06:07", "19:11");

        // The following SunriseSunsetTestUtils.test will not work on Java versions older than 2009.
        SunriseSunsetTestUtils.testSunriseSunset("America/Argentina/Buenos_Aires", "20130126", -34.6092, -58.3732, "06:07", "20:04");
        // The following SunriseSunsetTestUtils.test will not work on Java versions older than 2009.
        SunriseSunsetTestUtils.testSunriseSunset("America/Argentina/Buenos_Aires", "20131020", -34.6092, -58.3732, "06:05", "19:11");
        // The following SunriseSunsetTestUtils.test will not work on Java versions older than 2009.
        SunriseSunsetTestUtils.testSunriseSunset("America/Argentina/Buenos_Aires", "20131031", -34.6092, -58.3732, "05:53", "19:21");
    }

    /**
     * Test the time of civil twilight for some locations.
     */
    @Test
    public void testCivilTwilight() {

        SunriseSunsetTwilightTestUtils.testCivilTwilight("PST", "20130120", 34.0522, -118.2437, "06:30", "17:38");
        SunriseSunsetTwilightTestUtils.testCivilTwilight("CET", "20130120", 48.8567, 2.351, "08:00", "18:04");
        SunriseSunsetTwilightTestUtils.testCivilTwilight("Australia/Sydney", "20121225", -33.86, 151.2111, "05:14", "20:38");
        SunriseSunsetTwilightTestUtils.testCivilTwilight("Japan", "20130501", 35.6938, 139.7036, "04:22", "18:55");
        SunriseSunsetTwilightTestUtils.testCivilTwilight("Europe/Dublin", "20130605", 53.3441, -6.2675, "04:10", "22:37");
        SunriseSunsetTwilightTestUtils.testCivilTwilight("CST", "20130622", 41.8781, -87.6298, "04:41", "21:04");
        SunriseSunsetTwilightTestUtils.testCivilTwilight("Pacific/Honolulu", "20150827", 21.3069, -157.8583, "05:51", "19:16");
        SunriseSunsetTwilightTestUtils.testCivilTwilight("America/Argentina/Buenos_Aires", "20130501", -34.6092, -58.3732, "07:03", "18:38");
        SunriseSunsetTwilightTestUtils.testCivilTwilight("America/Argentina/Buenos_Aires", "20131019", -34.6092, -58.3732, "05:41", "19:36");

        // The following SunriseSunsetTwilightTestUtils.test will not work on Java versions older than 2009.
        SunriseSunsetTwilightTestUtils.testCivilTwilight("America/Argentina/Buenos_Aires", "20130126", -34.6092, -58.3732, "05:40", "20:32");
        // The following SunriseSunsetTwilightTestUtils.test will not work on Java versions older than 2009.
        SunriseSunsetTwilightTestUtils.testCivilTwilight("America/Argentina/Buenos_Aires", "20131020", -34.6092, -58.3732, "05:39", "19:37");
        // The following SunriseSunsetTwilightTestUtils.test will not work on Java versions older than 2009.
        SunriseSunsetTwilightTestUtils.testCivilTwilight("America/Argentina/Buenos_Aires", "20131031", -34.6092, -58.3732, "05:26", "19:48");
    }

    /**
     * Test if a particular datetime is in civil twilight for some locations.
     */
    @Test
    public void testIsCivilTwilight() {
        // Civil twilight at 17:11-17:38 and 6:30-06:57
        SunriseSunsetTwilightTestUtils.testIsCivilTwilight("PST", "20130120", "17:09", "17:25", "17:45", "06:25", "06:35", "06:59", 34.0522, -118.2437);

        // Civil twilight at 17:28-18:04 and 08:00-08:35
        SunriseSunsetTwilightTestUtils.testIsCivilTwilight("CET", "20130120", "17:25", "17:30", "18:15", "07:55", "08:30", "08:40", 48.8567, 2.351);

        // Civil twilight at 20:07-20:38 and 05:14-05:43
        SunriseSunsetTwilightTestUtils.testIsCivilTwilight("Australia/Sydney", "20121225", "20:06", "20:16", "20:39", "05:13", "05:30", "05:45", -33.86, 151.2111);

        // Civil twilight between 18:27-18:55 and 04:22-04:49
        SunriseSunsetTwilightTestUtils.testIsCivilTwilight("Japan", "20130501", "18:25", "18:40", "18:57", "04:20", "04:30", "04:52", 35.6938, 139.7036);

        // Civil twilight between 21:46-22:37 to 04:10-05:01
        SunriseSunsetTwilightTestUtils.testIsCivilTwilight("Europe/Dublin", "20130605", "21:44", "21:50", "22:39", "04:00", "04:30", "05:03", 53.3441, -6.2675);

        // Civil twilight between 20:29-21:04 and 04:41-05:16
        SunriseSunsetTwilightTestUtils.testIsCivilTwilight("CST", "20130622", "20:27", "21:00", "21:06", "04:39", "04:50", "05:18", 41.8781, -87.6298);

        // Civil twilight between 18:53-19:16 and 05:51-06:13
        SunriseSunsetTwilightTestUtils.testIsCivilTwilight("Pacific/Honolulu", "20150827", "18:51", "19:00", "19:18", "05:49", "06:00", "06:16", 21.3069, -157.8583);

        // The following SunriseSunsetTwilightTestUtils.test will not work on Java versions older than 2009.
        // Civil twilight between 20:04-20:32 and 05:40-06:07
        SunriseSunsetTwilightTestUtils.testIsCivilTwilight("America/Argentina/Buenos_Aires", "20130126", "20:02", "20:15", "20:34", "05:38", "06:00", "06:10", -34.6092, -58.3732);
        // Civil twilight between 19:11-19:37 and 05:39-06:05
        SunriseSunsetTwilightTestUtils.testIsCivilTwilight("America/Argentina/Buenos_Aires", "20131020", "19:09", "19:25", "19:39", "05:37", "06:00", "06:07", -34.6092, -58.3732);
        // Civil twilight between 19:21-19:48 and 05:26-05:52
        SunriseSunsetTwilightTestUtils.testIsCivilTwilight("America/Argentina/Buenos_Aires", "20131031", "19:19", "19:25", "19:50", "05:24", "05:40", "05:54", -34.6092, -58.3732);
    }

    /**
     * Test the time of nautical twilight for some locations.
     */
    @Test
    public void testNauticalTwilight() {

        SunriseSunsetTwilightTestUtils.testNauticalTwilight("PST", "20130120", 34.0522, -118.2437, "05:59", "18:08");
        SunriseSunsetTwilightTestUtils.testNauticalTwilight("CET", "20130120", 48.8567, 2.351, "07:21", "18:43");
        SunriseSunsetTwilightTestUtils.testNauticalTwilight("Australia/Sydney", "20121225", -33.86, 151.2111, "04:38", "21:12");
        SunriseSunsetTwilightTestUtils.testNauticalTwilight("Japan", "20130501", 35.6938, 139.7036, "03:49", "19:27");
        //SunriseSunsetTwilightTestUtils.testNauticalTwilight("Europe/Dublin", "20130605", 53.3441, -6.2675,
        //		"02:47", "00:00"); // Can't SunriseSunsetTwilightTestUtils.test this right now - twilight starts the next day.
        SunriseSunsetTwilightTestUtils.testNauticalTwilight("CST", "20130622", 41.8781, -87.6298, "03:57", "21:48");
        SunriseSunsetTwilightTestUtils.testNauticalTwilight("Pacific/Honolulu", "20150827", 21.3069, -157.8583, "05:24", "19:42");
        SunriseSunsetTwilightTestUtils.testNauticalTwilight("America/Argentina/Buenos_Aires", "20130501", -34.6092, -58.3732, "06:33", "19:08");
        SunriseSunsetTwilightTestUtils.testNauticalTwilight("America/Argentina/Buenos_Aires", "20131019", -34.6092, -58.3732, "05:10", "20:07");

        // The following SunriseSunsetTwilightTestUtils.test will not work on Java versions older than 2009.
        SunriseSunsetTwilightTestUtils.testNauticalTwilight("America/Argentina/Buenos_Aires", "20130126", -34.6092, -58.3732, "05:06", "21:06");
        // The following SunriseSunsetTwilightTestUtils.test will not work on Java versions older than 2009.
        SunriseSunsetTwilightTestUtils.testNauticalTwilight("America/Argentina/Buenos_Aires", "20131020", -34.6092, -58.3732, "05:08", "20:08");
        // The following SunriseSunsetTwilightTestUtils.test will not work on Java versions older than 2009.
        SunriseSunsetTwilightTestUtils.testNauticalTwilight("America/Argentina/Buenos_Aires", "20131031", -34.6092, -58.3732, "04:54", "20:20");
    }

    /**
     * Test if a particular datetime is in nautical twilight for some locations.
     */
    @Test
    public void testIsNauticalTwilight() {
        SunriseSunsetTwilightTestUtils.testIsNauticalTwilight("PST", "20130120", "17:36", "18:00", "18:11", "5:57", "06:25", "06:32", 34.0522, -118.2437);
        SunriseSunsetTwilightTestUtils.testIsNauticalTwilight("CET", "20130120", "18:02", "18:30", "18:45", "07:19", "07:30", "08:02", 48.8567, 2.351);
        SunriseSunsetTwilightTestUtils.testIsNauticalTwilight("Australia/Sydney", "20121225", "20:36", "21:00", "21:14", "04:36", "05:00", "05:16", -33.86, 151.2111);
        SunriseSunsetTwilightTestUtils.testIsNauticalTwilight("Japan", "20130501", "18:53", "19:00", "19:29", "03:47", "04:00", "04:24", 35.6938, 139.7036);
        SunriseSunsetTwilightTestUtils.testIsNauticalTwilight("CST", "20130622", "21:02", "21:30", "22:50", "03:55", "04:00", "04:43", 41.8781, -87.6298);
        SunriseSunsetTwilightTestUtils.testIsNauticalTwilight("Pacific/Honolulu", "20150827", "19:14", "19:30", "19:44", "05:22", "05:30", "05:53", 21.3069, -157.8583);
        SunriseSunsetTwilightTestUtils.testIsNauticalTwilight("America/Argentina/Buenos_Aires", "20130126", "20:30", "21:00", "21:08", "05:04", "05:30", "05:42", -34.6092, -58.3732);
        SunriseSunsetTwilightTestUtils.testIsNauticalTwilight("America/Argentina/Buenos_Aires", "20131020", "19:35", "20:00", "20:10", "05:06", "05:15", "05:41", -34.6092, -58.3732);
        SunriseSunsetTwilightTestUtils.testIsNauticalTwilight("America/Argentina/Buenos_Aires", "20130501", "18:36", "19:00", "19:10", "06:31", "06:45", "07:05", -34.6092, -58.3732);
        SunriseSunsetTwilightTestUtils.testIsNauticalTwilight("America/Argentina/Buenos_Aires", "20131019", "19:34", "20:00", "20:09", "05:08", "05:35", "05:43", -34.6092, -58.3732);
    }


    /**
     * Test the time of astronomical twilight for some locations.
     */
    @Test
    public void testAstronomicalTwilight() {
        SunriseSunsetTwilightTestUtils.testAstronomicalTwilight("PST", "20130120", 34.0522, -118.2437, "05:30", "18:38");
        SunriseSunsetTwilightTestUtils.testAstronomicalTwilight("CET", "20130120", 48.8567, 2.351, "6:43", "19:20");
        SunriseSunsetTwilightTestUtils.testAstronomicalTwilight("Australia/Sydney", "20121225", -33.86, 151.2111, "03:59", "21:52");
        SunriseSunsetTwilightTestUtils.testAstronomicalTwilight("Japan", "20130501", 35.6938, 139.7036, "03:14", "20:02");
        SunriseSunsetTwilightTestUtils.testAstronomicalTwilight("Europe/Dublin", "20130605", 53.3441, -6.2675, null, null);
        SunriseSunsetTwilightTestUtils.testAstronomicalTwilight("CST", "20130622", 41.8781, -87.6298, "03:04", "22:41");
        SunriseSunsetTwilightTestUtils.testAstronomicalTwilight("Pacific/Honolulu", "20150827", 21.3069, -157.8583, "04:57", "20:09");
        SunriseSunsetTwilightTestUtils.testAstronomicalTwilight("America/Argentina/Buenos_Aires", "20130501", -34.6092, -58.3732, "06:04", "19:38");
        SunriseSunsetTwilightTestUtils.testAstronomicalTwilight("America/Argentina/Buenos_Aires", "20131019", -34.6092, -58.3732, "04:38", "20:39");

        // The following SunriseSunsetTwilightTestUtils.test will not work on Java versions older than 2009.
        SunriseSunsetTwilightTestUtils.testAstronomicalTwilight("America/Argentina/Buenos_Aires", "20130126", -34.6092, -58.3732, "4:30", "21:42");
        // The following SunriseSunsetTwilightTestUtils.test will not work on Java versions older than 2009.
        SunriseSunsetTwilightTestUtils.testAstronomicalTwilight("America/Argentina/Buenos_Aires", "20131020", -34.6092, -58.3732, "04:36", "20:40");
        // The following SunriseSunsetTwilightTestUtils.test will not work on Java versions older than 2009.
        SunriseSunsetTwilightTestUtils.testAstronomicalTwilight("America/Argentina/Buenos_Aires", "20131031", -34.6092, -58.3732, "04:21", "20:53");
    }

    /**
     * Test if a particular datetime is in astronomical twilight for some locations.
     */
    @Test
    public void testIsAstronomicalTwilight() {
        SunriseSunsetTwilightTestUtils.testIsAstronomicalTwilight("PST", "20130120", "18:06", "18:30", "18:40", "5:28", "05:35", "06:01", 34.0522, -118.2437);
        SunriseSunsetTwilightTestUtils.testIsAstronomicalTwilight("CET", "20130120", "18:41", "19:00", "19:22", "06:41", "07:00", "07:22", 48.8567, 2.351);
        SunriseSunsetTwilightTestUtils.testIsAstronomicalTwilight("Australia/Sydney", "20121225", "21:10", "21:30", "21:54", "03:57", "04:30", "04:40", -33.86, 151.2111);
        SunriseSunsetTwilightTestUtils.testIsAstronomicalTwilight("Japan", "20130501", "19:25", "19:40", "20:04", "03:12", "03:30", "03:51", 35.6938, 139.7036);
        SunriseSunsetTwilightTestUtils.testIsAstronomicalTwilight("CST", "20130622", "21:46", "22:30", "22:43", "03:02", "03:30", "03:59", 41.8781, -87.6298);
        SunriseSunsetTwilightTestUtils.testIsAstronomicalTwilight("Pacific/Honolulu", "20150827", "19:40", "20:00", "20:11", "04:55", "05:00", "05:26", 21.3069, -157.8583);
        SunriseSunsetTwilightTestUtils.testIsAstronomicalTwilight("America/Argentina/Buenos_Aires", "20130501", "19:06", "19:30", "19:40", "06:02", "06:25", "06:35", -34.6092, -58.3732);
        SunriseSunsetTwilightTestUtils.testIsAstronomicalTwilight("America/Argentina/Buenos_Aires", "20131019", "20:05", "20:30", "20:41", "04:36", "05:05", "05:12", -34.6092, -58.3732);
        SunriseSunsetTwilightTestUtils.testIsAstronomicalTwilight("America/Argentina/Buenos_Aires", "20130126", "21:04", "21:30", "21:44", "04:28", "05:00", "05:08", -34.6092, -58.3732);
        SunriseSunsetTwilightTestUtils.testIsAstronomicalTwilight("America/Argentina/Buenos_Aires", "20131020", "20:06", "20:30", "20:42", "04:34", "05:05", "05:10", -34.6092, -58.3732);
    }

    /**
     * Test if a particular datetime is in twilight for some locations.
     */
    @Test
    public void testIsTwilight() {
        SunriseSunsetTwilightTestUtils.testIsTwilight("PST", "20130120", "17:09", "18:30", "18:40", "5:28", "05:35", "06:59", 34.0522, -118.2437);
        SunriseSunsetTwilightTestUtils.testIsTwilight("CET", "20130120", "17:25", "19:00", "19:22", "06:41", "07:00", "08:40", 48.8567, 2.351);
        SunriseSunsetTwilightTestUtils.testIsTwilight("Australia/Sydney", "20121225", "20:06", "21:30", "21:54", "03:57", "04:30", "05:45", -33.86, 151.2111);
        SunriseSunsetTwilightTestUtils.testIsTwilight("Japan", "20130501", "18:25", "19:40", "20:04", "03:12", "03:30", "04:52", 35.6938, 139.7036);
        SunriseSunsetTwilightTestUtils.testIsTwilight("CST", "20130622", "20:27", "22:30", "22:43", "03:02", "03:30", "05:18", 41.8781, -87.6298);
        SunriseSunsetTwilightTestUtils.testIsTwilight("Pacific/Honolulu", "20150827", "18:51", "20:00", "20:11", "04:55", "05:00", "06:16", 21.3069, -157.8583);
        SunriseSunsetTwilightTestUtils.testIsTwilight("America/Argentina/Buenos_Aires", "20130126", "20:02", "21:30", "21:44", "04:28", "05:00", "06:10", -34.6092, -58.3732);
        SunriseSunsetTwilightTestUtils.testIsTwilight("America/Argentina/Buenos_Aires", "20131020", "19:09", "20:30", "20:42", "04:34", "05:05", "06:07", -34.6092, -58.3732);
    }

    /**
     * Test the time of solar noon for some locations
     */
    @Test
    public void testSolarNoon() {
        SunriseSunsetTestUtils.testSolarNoon("PST", "20130120", 34.0522, -118.2437, "12:04");
        SunriseSunsetTestUtils.testSolarNoon("CET", "20130120", 48.8567, 2.351, "13:02");
        SunriseSunsetTestUtils.testSolarNoon("Australia/Sydney", "20121225", -33.86, 151.2111, "12:55");
        SunriseSunsetTestUtils.testSolarNoon("Japan", "20130501", 35.6938, 139.7036, "11:38");
        SunriseSunsetTestUtils.testSolarNoon("Europe/Dublin", "20130605", 53.3441, -6.2675, "13:24");
        SunriseSunsetTestUtils.testSolarNoon("CST", "20130622", 41.8781, -87.6298, "12:52");
        SunriseSunsetTestUtils.testSolarNoon("Pacific/Honolulu", "20150827", 21.3069, -157.8583, "12:33");
        SunriseSunsetTestUtils.testSolarNoon("America/Argentina/Buenos_Aires", "20130501", -34.6092, -58.3732, "12:51");
        SunriseSunsetTestUtils.testSolarNoon("America/Argentina/Buenos_Aires", "20131019", -34.6092, -58.3732, "12:39");

        // The following test will not work on Java versions older than 2009.
        SunriseSunsetTestUtils.testSolarNoon("America/Argentina/Buenos_Aires", "20130126", -34.6092, -58.3732, "13:06");
        // The following test will not work on Java versions older than 2009.
        SunriseSunsetTestUtils.testSolarNoon("America/Argentina/Buenos_Aires", "20131020", -34.6092, -58.3732, "12:38");
        // The following test will not work on Java versions older than 2009.
        SunriseSunsetTestUtils.testSolarNoon("America/Argentina/Buenos_Aires", "20131031", -34.6092, -58.3732, "12:37");
    }

    /**
     * Test if a particular datetime is during the day for some locations
     */
    @Test
    public void testIsDay() {
        SunriseSunsetTestUtils.testIsDayNightTwilight("PST", "20130120", "17:00", "18:30", "23:00", "05:35", 34.0522, -118.2437);
        SunriseSunsetTestUtils.testIsDayNightTwilight("CET", "20130120", "17:00", "19:00", "19:24", "07:00", 48.8567, 2.351);
        SunriseSunsetTestUtils.testIsDayNightTwilight("Australia/Sydney", "20121225", "20:0", "21:30", "21:56", "04:30", -33.86, 151.2111);
        SunriseSunsetTestUtils.testIsDayNightTwilight("Japan", "20130501", "18:00", "19:40", "20:06", "03:30", 35.6938, 139.7036);
        SunriseSunsetTestUtils.testIsDayNightTwilight("CST", "20130622", "20:20", "22:30", "23:43", "03:30", 41.8781, -87.6298);
        SunriseSunsetTestUtils.testIsDayNightTwilight("Pacific/Honolulu", "20150827", "18:49", "20:00", "20:13", "05:00", 21.3069, -157.8583);
        SunriseSunsetTestUtils.testIsDayNightTwilight("America/Argentina/Buenos_Aires", "20130126", "20:00", "21:30", "21:46","05:00", -34.6092, -58.3732);
        SunriseSunsetTestUtils.testIsDayNightTwilight("America/Argentina/Buenos_Aires", "20131020", "19:07", "20:30", "20:44", "05:05", -34.6092, -58.3732);
    }

    /**
     * Test the day period calculation
     */
    @Test
    public void testGetDayPeriod() {
        SunriseSunsetTestUtils.testGetDayPeriod("PST", "20130120", "5:35", "6:25", "6:35", "12:00", "17:25", "18:00", "18:30", "23:30", 34.0522, -118.2437);
        SunriseSunsetTestUtils.testGetDayPeriod("CET", "20130120", "7:00", "7:30", "8:30", "12:00", "17:30", "18:30", "19:00","21:00", 48.8567, 2.351);
        SunriseSunsetTestUtils.testGetDayPeriod("America/Argentina/Buenos_Aires", "20131020", "5:05", "5:15", "06:00", "12:00", "19:25", "20:00", "20:30", "20:45", -34.6092, -58.3732);
    }

}
