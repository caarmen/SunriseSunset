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
 *
 * @author Carmen Alvarez
 */
package ca.rmen.sunrisesunset.test;

import ca.rmen.sunrisesunset.SunriseSunset;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the Sunrise Sunset Calculator, for specific locations.
 */
public class SunriseSunsetSpecificLocationsTest {

    @Test
    public void testAntarctica() {
        // There is a higher margin of error with antarctica calculations
        double accuracyMinutes = 3.6;
        SunriseSunsetTestUtils.testSunriseSunset("Antarctica/McMurdo", "20150419", -77.8456, 166.6693, "10:37", "15:08", accuracyMinutes);
        SunriseSunsetTwilightTestUtils.testCivilTwilight("Antarctica/McMurdo", "20150419", -77.8456, 166.6693, "08:26", "17:19", accuracyMinutes);
        SunriseSunsetTwilightTestUtils.testNauticalTwilight("Antarctica/McMurdo", "20150419", -77.8456, 166.6693, "06:29", "19:17", accuracyMinutes);
        SunriseSunsetTwilightTestUtils.testAstronomicalTwilight("Antarctica/McMurdo", "20150419", -77.8456, 166.6693, "04:27", "21:18", accuracyMinutes);
        SunriseSunsetTestUtils.testSolarNoon("Antarctica/McMurdo", "20150419", -77.8456, 166.6693, "12:53");
        SunriseSunsetTwilightTestUtils.testIsCivilTwilight("Antarctica/McMurdo", "20150419", "15:06", "17:00", "17:23", "08:24", "09:00", "10:39", -77.8456, 166.6693);
        SunriseSunsetTwilightTestUtils.testIsNauticalTwilight("Antarctica/McMurdo", "20150419", "17:17", "18:00", "19:20", "06:27", "07:00", "08:28", -77.8456, 166.6693);
        SunriseSunsetTwilightTestUtils.testIsAstronomicalTwilight("Antarctica/McMurdo", "20150419", "19:15", "20:00", "21:22", "04:25", "05:00", "06:31", -77.8456, 166.6693);
        SunriseSunsetTestUtils.testIsDayNightTwilight("Antarctica/McMurdo", "20150419", "15:04", "17:00", "21:24", "06:00", -77.8456, 166.6693);
        SunriseSunsetTestUtils.testGetDayPeriod("Antarctica/McMurdo", "20150419", "5:05", "07:00", "09:00", "12:00", "16:25", "18:00", "20:30", "22:45", -77.8456, 166.6693);

        SunriseSunsetTestUtils.testSunriseSunset("Antarctica/McMurdo", "20150621", -77.8456, 166.6693, null, null);
        SunriseSunsetTwilightTestUtils.testCivilTwilight("Antarctica/McMurdo", "20150621", -77.8456, 166.6693, null, null);
        SunriseSunsetTwilightTestUtils.testNauticalTwilight("Antarctica/McMurdo", "20150621", -77.8456, 166.6693, "11:33", "14:17", accuracyMinutes);
        SunriseSunsetTwilightTestUtils.testAstronomicalTwilight("Antarctica/McMurdo", "20150621", -77.8456, 166.6693, "08:32", "17:17", accuracyMinutes);
        SunriseSunsetTestUtils.testSolarNoon("Antarctica/McMurdo", "20150621", -77.8456, 166.6693, null);
        SunriseSunsetTwilightTestUtils.testIsCivilTwilight("Antarctica/McMurdo", "20150621 12:00", -77.8456, 166.6693, false);
        SunriseSunsetTwilightTestUtils.testIsCivilTwilight("Antarctica/McMurdo", "20150621 06:00", -77.8456, 166.6693, false);
        SunriseSunsetTwilightTestUtils.testIsNauticalTwilight("Antarctica/McMurdo", "20150621 14:15", -77.8456, 166.6693, false);
        SunriseSunsetTwilightTestUtils.testIsNauticalTwilight("Antarctica/McMurdo", "20150621 14:19", -77.8456, 166.6693, false);
        SunriseSunsetTwilightTestUtils.testIsNauticalTwilight("Antarctica/McMurdo", "20150621 17:14", -77.8456, 166.6693, false);
        SunriseSunsetTwilightTestUtils.testIsNauticalTwilight("Antarctica/McMurdo", "20150621 17:20", -77.8456, 166.6693, false);
        SunriseSunsetTwilightTestUtils.testIsAstronomicalTwilight("Antarctica/McMurdo", "20150621", "14:15", "16:00", "17:19", "08:30", "10:00", "11:35", -77.8456, 166.6693);
        SunriseSunsetTestUtils.testGetDayPeriod("Antarctica/McMurdo", "20150621 09:05", -77.8456, 166.6693, SunriseSunset.DayPeriod.ASTRONOMICAL_TWILIGHT);
        SunriseSunsetTestUtils.testGetDayPeriod("Antarctica/McMurdo", "20150621 11:35", -77.8456, 166.6693, SunriseSunset.DayPeriod.NIGHT);
        SunriseSunsetTestUtils.testGetDayPeriod("Antarctica/McMurdo", "20150621 13:35", -77.8456, 166.6693, SunriseSunset.DayPeriod.NIGHT);
        SunriseSunsetTestUtils.testGetDayPeriod("Antarctica/McMurdo", "20150621 15:35", -77.8456, 166.6693, SunriseSunset.DayPeriod.ASTRONOMICAL_TWILIGHT);
        SunriseSunsetTestUtils.testDayLength("Antarctica/McMurdo", "20150621", -77.8456, 166.6693, 0);

        SunriseSunsetTestUtils.testSunriseSunset("Antarctica/McMurdo", "20150921", -77.8456, 166.6693, "06:48", "18:46", accuracyMinutes);
        SunriseSunsetTwilightTestUtils.testCivilTwilight("Antarctica/McMurdo", "20150921", -77.8456, 166.6693, "5:07", "20:27", accuracyMinutes);
        // Not sure why this one is off more... 2:23 vs 2:28
        SunriseSunsetTwilightTestUtils.testNauticalTwilight("Antarctica/McMurdo", "20150921", -77.8456, 166.6693, "02:23", "23:11", 5.1);
        SunriseSunsetTwilightTestUtils.testAstronomicalTwilight("Antarctica/McMurdo", "20150921", -77.8456, 166.6693, null, null);
        SunriseSunsetTestUtils.testSolarNoon("Antarctica/McMurdo", "20150921", -77.8456, 166.6693, "12:47");
        SunriseSunsetTwilightTestUtils.testIsCivilTwilight("Antarctica/McMurdo", "20150921", "18:44", "20:00", "20:29", "05:05", "06:00", "06:51", -77.8456, 166.6693);
        SunriseSunsetTwilightTestUtils.testIsNauticalTwilight("Antarctica/McMurdo", "20150921", "20:25", "21:00", "23:13", "02:21", "03:00", "05:10", -77.8456, 166.6693);
        SunriseSunsetTwilightTestUtils.testIsAstronomicalTwilight("Antarctica/McMurdo", "20150921 23:13", -77.8456, 166.6693, false);
        SunriseSunsetTwilightTestUtils.testIsAstronomicalTwilight("Antarctica/McMurdo", "20150921 23:09", -77.8456, 166.6693, false);
        SunriseSunsetTwilightTestUtils.testIsAstronomicalTwilight("Antarctica/McMurdo", "20150921 02:21", -77.8456, 166.6693, false);
        SunriseSunsetTwilightTestUtils.testIsAstronomicalTwilight("Antarctica/McMurdo", "20150921 02:25", -77.8456, 166.6693, false);
        SunriseSunsetTestUtils.testGetDayPeriod("Antarctica/McMurdo", "20150921 04:05", -77.8456, 166.6693, SunriseSunset.DayPeriod.NAUTICAL_TWILIGHT);
        SunriseSunsetTestUtils.testGetDayPeriod("Antarctica/McMurdo", "20150921 06:00", -77.8456, 166.6693, SunriseSunset.DayPeriod.CIVIL_TWILIGHT);
        SunriseSunsetTestUtils.testGetDayPeriod("Antarctica/McMurdo", "20150921 12:00", -77.8456, 166.6693, SunriseSunset.DayPeriod.DAY);
        SunriseSunsetTestUtils.testGetDayPeriod("Antarctica/McMurdo", "20150921 19:00", -77.8456, 166.6693, SunriseSunset.DayPeriod.CIVIL_TWILIGHT);
        SunriseSunsetTestUtils.testGetDayPeriod("Antarctica/McMurdo", "20150921 22:00", -77.8456, 166.6693, SunriseSunset.DayPeriod.NAUTICAL_TWILIGHT);
        SunriseSunsetTestUtils.testGetDayPeriod("Antarctica/McMurdo", "20150921 23:30", -77.8456, 166.6693, SunriseSunset.DayPeriod.NIGHT);

        SunriseSunsetTestUtils.testSunriseSunset("Antarctica/McMurdo", "20151221", -77.8456, 166.6693, null, null);
        SunriseSunsetTwilightTestUtils.testCivilTwilight("Antarctica/McMurdo", "20151221", -77.8456, 166.6693, null, null);
        SunriseSunsetTwilightTestUtils.testNauticalTwilight("Antarctica/McMurdo", "20151221", -77.8456, 166.6693, null, null);
        SunriseSunsetTwilightTestUtils.testAstronomicalTwilight("Antarctica/McMurdo", "20151221", -77.8456, 166.6693, null, null);
        SunriseSunsetTestUtils.testDayOrNight("Antarctica", "Antarctica/McMurdo", -77.8456, 166.6693, null, null, null, null);
        SunriseSunsetTestUtils.testSolarNoon("Antarctica/McMurdo", "20151221", -77.8456, 166.6693, null);
        SunriseSunsetTwilightTestUtils.testIsCivilTwilight("Antarctica/McMurdo", "20151221 12:00", -77.8456, 166.6693, false);
        SunriseSunsetTwilightTestUtils.testIsNauticalTwilight("Antarctica/McMurdo", "20151221 12:00", -77.8456, 166.6693, false);
        SunriseSunsetTwilightTestUtils.testIsAstronomicalTwilight("Antarctica/McMurdo", "20151221 12:00", -77.8456, 166.6693, false);
        SunriseSunsetTestUtils.testGetDayPeriod("Antarctica/McMurdo", "20151221 04:05", -77.8456, 166.6693, SunriseSunset.DayPeriod.DAY);
        SunriseSunsetTestUtils.testGetDayPeriod("Antarctica/McMurdo", "20151221 12:00", -77.8456, 166.6693, SunriseSunset.DayPeriod.DAY);
        SunriseSunsetTestUtils.testGetDayPeriod("Antarctica/McMurdo", "20151221 18:00", -77.8456, 166.6693, SunriseSunset.DayPeriod.DAY);
        SunriseSunsetTestUtils.testGetDayPeriod("Antarctica/McMurdo", "20151221 23:00", -77.8456, 166.6693, SunriseSunset.DayPeriod.DAY);
        SunriseSunsetTestUtils.testDayLength("Antarctica/McMurdo", "20151221", -77.8456, 166.6693, 86400000);

    }

    @Test
    public void testAtlanta() {
        // Issue #16: Atlanta, Georgia:
        // Compare to sunrisesunset.com:
        SunriseSunsetTestUtils.testSunriseSunsetSeconds("US/Eastern", "20090906", 33.766667, -84.416667, "07:15:00 EDT", "19:58:00 EDT", SunriseSunsetTestUtils.DEFAULT_ACCURACY_MINUTES);
        SunriseSunsetTestUtils.testSolarNoon("US/Eastern", "20090906", 33.766667, -84.416667, "13:36");

        // Compare to Pyephem (07:14:57, 19:56:10)
        SunriseSunsetTestUtils.testSunriseSunsetSeconds("US/Eastern", "20090906", 33.766667, -84.416667, "07:14:57 EDT", "19:56:10 EDT", SunriseSunsetTestUtils.DEFAULT_ACCURACY_MINUTES);

        // Compare to USNO (07:15:00, 23:56:00)
        // This one is off by 2 seconds over our accuracy threshold: it's off by 137000 ms (2.2833 minutes) instead of 135000 ms (2.25 minutes).
        // But since it doesn't seem to have seconds, this may be due to rounding done by USNO.
        SunriseSunsetTestUtils.testSunriseSunsetSeconds("US/Eastern", "20090906", 33.766667, -84.416667, "07:15:00 EDT", "19:56:00 EDT", SunriseSunsetTestUtils.DEFAULT_ACCURACY_MINUTES + 0.034);
    }

    @Test
    public void testAlertNunavutCanada() {
        // Wildly different results for Alert, Nunavut, Canada, between multiple sources, for
        // sunrise/sunset, civil twilight and nautical twilight.
        // Astronomical twilight and solar noon is consistent where available.

        // The different sources, ranked by how close the results match this library are:
        // suncalc.net (closest)
        // suncalc.org
        // timeanddate.com
        // esrl.noaa.gov
        // nrc-cnrc.gc.ca
        // sunrisesunset.com (biggest difference)

        // http://www.suncalc.org/#/82.5018,-62.3481,11/2016.02.28/12:35/1
        double accuracyMinutesSunCalcDotOrg = 1.1; // this is better than our default accuracy acceptance!
        SunriseSunsetTestUtils.testSunriseSunset("EST", "20160228", 82.50178, -62.34809, "10:27", "12:19", accuracyMinutesSunCalcDotOrg);
        SunriseSunsetTwilightTestUtils.testCivilTwilight("EST", "20160228", 82.50178, -62.34809, "06:27", "16:19", accuracyMinutesSunCalcDotOrg);
        SunriseSunsetTestUtils.testSolarNoon("EST", "20160228", 82.501778, -62.34809, "11:23");

        // http://suncalc.net/#/82.5018,-62.3481,0/2016.02.28/12:22
        double accuracyMinutesSunCalcDotNet = 1.1; // this is better than our default accuracy acceptance!
        SunriseSunsetTestUtils.testSunriseSunset("EST", "20160228", 82.5018, -62.3481, "10:27", "12:19", accuracyMinutesSunCalcDotNet);
        SunriseSunsetTwilightTestUtils.testCivilTwilight("EST", "20160228", 82.5018, -62.3481, "06:27", "16:19", accuracyMinutesSunCalcDotNet);
        SunriseSunsetTwilightTestUtils.testNauticalTwilight("EST", "20160228", 82.5018, -62.3481, "03:16", "19:30", accuracyMinutesSunCalcDotNet);

        // https://www.timeanddate.com/sun/canada/alert?month=2&year=2016:
        double accuracyMinutesTimeAndDateDotCom = 11.1;
        SunriseSunsetTestUtils.testSunriseSunset("EST", "20160228", 82.50177764892578, -62.34809112548828, "10:17", "12:31", accuracyMinutesTimeAndDateDotCom);
        SunriseSunsetTwilightTestUtils.testCivilTwilight("EST", "20160228", 82.50177764892578, -62.34809112548828, "06:25", "16:24", accuracyMinutesTimeAndDateDotCom);
        SunriseSunsetTwilightTestUtils.testNauticalTwilight("EST", "20160228", 82.50177764892578, -62.34809112548828, "03:15", "19:38", accuracyMinutesTimeAndDateDotCom);
        SunriseSunsetTwilightTestUtils.testAstronomicalTwilight("EST", "20160228", 82.50177764892578, -62.34809112548828, null, null);
        SunriseSunsetTestUtils.testSolarNoon("EST", "20160228", 82.50177764892578, -62.34809112548828, "11:22");

        // https://www.esrl.noaa.gov/gmd/grad/solcalc/sunrise.html
        double accuracyMinutesNoaaDotGov = 12.1;
        SunriseSunsetTestUtils.testSunriseSunset("EST", "20160228", 82.501667, -62.348056, "10:15", "12:32", accuracyMinutesNoaaDotGov);
        SunriseSunsetTestUtils.testSolarNoon("EST", "20160228", 82.501667, -62.348056, "11:22");

        // http://app.hia-iha.nrc-cnrc.gc.ca/cgi-bin/sun-soleil.pl
        double accuracyMinutesNrc = 12.9;
        SunriseSunsetTestUtils.testSunriseSunset("EST", "20160228", 82.5, -62.35, "10:14", "12:33", accuracyMinutesNrc);
        SunriseSunsetTwilightTestUtils.testCivilTwilight("EST", "20160228", 82.5, -62.35, "06:24", "16:24", accuracyMinutesNrc);
        SunriseSunsetTwilightTestUtils.testNauticalTwilight("EST", "20160228", 82.5, -62.35, "03:15", "19:38", accuracyMinutesNrc);
        SunriseSunsetTestUtils.testSolarNoon("EST", "20160228", 82.501778, -62.35, "11:22");

        // http://sunrisesunset.com/calendar.asp?comb_city_info=SunriseSunsetTestUtils.test;62.34809;82.50178;-5;1&month=2&year=2016&time_type=0&back=&want_twi_civ=1&want_twi_naut=1&want_twi_astro=1&want_info=1&want_solar_noon=1&wadj=1
        double accuracyMinutesSunriseSunsetDotCom = 26.0;
        SunriseSunsetTestUtils.testSunriseSunset("EST", "20160228", 82.50177764892578, -62.34809112548828, "10:50", "11:54", accuracyMinutesSunriseSunsetDotCom);
        SunriseSunsetTwilightTestUtils.testCivilTwilight("EST", "20160228", 82.50177764892578, -62.34809112548828, "06:30", "16:14", accuracyMinutesSunriseSunsetDotCom);
        SunriseSunsetTwilightTestUtils.testNauticalTwilight("EST", "20160228", 82.50177764892578, -62.34809112548828, "03:20", "19:24", accuracyMinutesSunriseSunsetDotCom);
        SunriseSunsetTwilightTestUtils.testAstronomicalTwilight("EST", "20160228", 82.50177764892578, -62.34809112548828, null, null);
        SunriseSunsetTestUtils.testSolarNoon("EST", "20160228", 82.50177764892578, -62.34809112548828, "11:22");
    }


}
