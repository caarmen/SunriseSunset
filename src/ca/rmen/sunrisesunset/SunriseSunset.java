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
package ca.rmen.sunrisesunset;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Provides methods to determine the sunrise and sunset time of a given
 * location, or if it is currently day or night at a given location. <br/>
 * Also provides methods to convert between Greogrian and Julian dates.<br/>
 * The formulas used by this class are from the Wikipedia articles on Julian Day
 * and Sunrise Equation. <br/>
 * {@link http://en.wikipedia.org/wiki/Julian_day} <br/>
 * {@link http://en.wikipedia.org/wiki/Sunrise_equation}
 * 
 * @author Carmen Alvarez
 * 
 */
public class SunriseSunset {

	private static final int JULIAN_DATE_2000_01_01 = 2451545;
	private static final double CONST_0009 = 0.0009;
	private static final double CONST_360 = 360;

	/**
	 * Convert a Gregorian calendar date to a Julian date. Accuracy is to the
	 * second.
	 * 
	 * This is based on the Wikipedia article for Julian day.
	 * 
	 * {@link http://en.wikipedia.org/wiki/Julian_day#
	 * Converting_Julian_or_Gregorian_calendar_date_to_Julian_Day_Number}
	 * 
	 * @param gregorianDate
	 *            Gregorian date in any time zone.
	 * @return the Julian date for the given Gregorian date.
	 */
	public static double getJulianDate(final Calendar gregorianDate) {
		// Convert the date to the UTC timezone.
		TimeZone tzUTC = TimeZone.getTimeZone("UTC");
		Calendar gregorianDateUTC = Calendar.getInstance(tzUTC);
		gregorianDateUTC.setTimeInMillis(gregorianDate.getTimeInMillis());
		// For the year (Y) astronomical year numbering is used, thus 1 BC is 0,
		// 2 BC is -1, and 4713 BC is -4712.
		int year = gregorianDateUTC.get(Calendar.YEAR);
		// The months (M) January to December are 1 to 12
		int month = gregorianDateUTC.get(Calendar.MONTH) + 1;
		// D is the day of the month.
		int day = gregorianDateUTC.get(Calendar.DAY_OF_MONTH);
		int a = (14 - month) / 12;
		int y = year + 4800 - a;
		int m = month + 12 * a - 3;

		int julianDay = day + (153 * m + 2) / 5 + 365 * y + (y / 4) - (y / 100)
				+ (y / 400) - 32045;
		int hour = gregorianDateUTC.get(Calendar.HOUR_OF_DAY);
		int minute = gregorianDateUTC.get(Calendar.MINUTE);
		int second = gregorianDateUTC.get(Calendar.SECOND);

		double julianDate = julianDay + ((double) hour - 12) / 24
				+ ((double) minute) / 1440 + ((double) second) / 86400;
		return julianDate;
	}

	/**
	 * Convert a Julian date to a Gregorian date. The Gregorian date will be in
	 * the local timezone. Accuracy is to the second.
	 * 
	 * This is based on the Wikipedia article for Julian day.
	 * 
	 * {@link http://en.wikipedia.org/wiki/Julian_day#
	 * Gregorian_calendar_from_Julian_day_number}
	 * 
	 * @return a Gregorian date in the local timezone.
	 */
	public static Calendar getGregorianDate(final double julianDate) {

		final int DAYS_PER_4000_YEARS = 146097;
		final int DAYS_PER_CENTURY = 36524;
		final int DAYS_PER_4_YEARS = 1461;
		final int DAYS_PER_5_MONTHS = 153;

		// Let J = JD + 0.5: (note: this shifts the epoch back by one half day,
		// to start it at 00:00UTC, instead of 12:00 UTC);
		int J = (int) (julianDate + 0.5);

		// let j = J + 32044; (note: this shifts the epoch back to astronomical
		// year -4800 instead of the start of the Christian era in year AD 1 of
		// the proleptic Gregorian calendar).
		int j = J + 32044;

		// let g = j div 146097; let dg = j mod 146097;
		int g = j / DAYS_PER_4000_YEARS;
		int dg = j % DAYS_PER_4000_YEARS;

		// let c = (dg div 36524 + 1) * 3 div 4; let dc = dg - c * 36524;
		int c = ((dg / DAYS_PER_CENTURY + 1) * 3) / 4;
		int dc = dg - c * DAYS_PER_CENTURY;

		// let b = dc div 1461; let db = dc mod 1461;
		int b = dc / DAYS_PER_4_YEARS;
		int db = dc % DAYS_PER_4_YEARS;

		// let a = (db div 365 + 1) * 3 div 4; let da = db - a * 365;
		int a = ((db / 365 + 1) * 3) / 4;
		int da = db - a * 365;

		// let y = g * 400 + c * 100 + b * 4 + a; (note: this is the integer
		// number of full years elapsed since March 1, 4801 BC at 00:00 UTC);
		int y = g * 400 + c * 100 + b * 4 + a;

		// let m = (da * 5 + 308) div 153 - 2; (note: this is the integer number
		// of full months elapsed since the last March 1 at 00:00 UTC);
		int m = (da * 5 + 308) / DAYS_PER_5_MONTHS - 2;

		// let d = da -(m + 4) * 153 div 5 + 122; (note: this is the number of
		// days elapsed since day 1 of the month at 00:00 UTC, including
		// fractions of one day);
		int d = da - ((m + 4) * DAYS_PER_5_MONTHS) / 5 + 122;

		// let Y = y - 4800 + (m + 2) div 12;
		int year = y - 4800 + (m + 2) / 12;

		// let M = (m + 2) mod 12 + 1;
		int month = (m + 2) % 12;

		// let D = d + 1;
		int day = d + 1;

		// Apply the fraction of the day in the Julian date to the Gregorian
		// date.
		// Example: dayFraction = 0.717
		final double dayFraction = (julianDate + 0.5) - J;

		// Ex: 0.717*24 = 17.208 hours. We truncate to 17 hours.
		final int hours = (int) (dayFraction * 24);
		// Ex: 17.208 - 17 = 0.208 days. 0.208*60 = 12.48 minutes. We truncate
		// to 12 minutes.
		final int minutes = (int) ((dayFraction * 24 - hours) * 60d);
		// Ex: 17.208*60 - (17*60 + 12) = 1032.48 - 1032 = 0.48 minutes. 0.48*60
		// = 28.8 seconds.
		// We round to 29 seconds.
		final int seconds = (int) ((dayFraction * 24 * 3600 - (hours * 3600 + minutes * 60)) + .5);

		// Create the gregorian date in UTC.
		final Calendar gregorianDateUTC = Calendar.getInstance(TimeZone
				.getTimeZone("UTC"));
		gregorianDateUTC.set(Calendar.YEAR, year);
		gregorianDateUTC.set(Calendar.MONTH, month);
		gregorianDateUTC.set(Calendar.DAY_OF_MONTH, day);
		gregorianDateUTC.set(Calendar.HOUR_OF_DAY, hours);
		gregorianDateUTC.set(Calendar.MINUTE, minutes);
		gregorianDateUTC.set(Calendar.SECOND, seconds);

		// Convert to a Gregorian date in the local timezone.
		Calendar gregorianDate = Calendar.getInstance();
		gregorianDate.setTimeInMillis(gregorianDateUTC.getTimeInMillis());
		return gregorianDate;
	}

	/**
	 * Calculate the sunrise and sunset times for the given date and given
	 * location. This is based on the Wikipedia article on the Sunrise equation:
	 * {@link http://en.wikipedia.org/wiki/Sunrise_equation}
	 * 
	 * @param day
	 *            The day for which to calculate sunrise and sunset
	 * @param latitude
	 *            the latitude of the location in degrees.
	 * @param longitude
	 *            the longitude of the location in degrees (West is negative)
	 * @return a two-element Gregorian Calendar array. The first element is the
	 *         sunrise, the second element is the sunset.
	 */
	public static Calendar[] getSunriseSunset(final Calendar day,
			final double latitude, double longitude) {
		final double latitudeRad = Math.toRadians(latitude);

		longitude = -longitude;

		// Get the given date as a Julian date.
		final double julianDate = getJulianDate(day);

		// Calculate current Julian cycle (number of days since 2000-01-01).
		final double nstar = julianDate - JULIAN_DATE_2000_01_01 - CONST_0009
				- longitude / CONST_360;
		final double n = Math.round(nstar);

		// Approximate solar noon
		final double jstar = JULIAN_DATE_2000_01_01 + CONST_0009 + longitude
				/ CONST_360 + n;
		// Solar mean anomaly
		final double m = Math
				.toRadians((357.5291 + 0.98560028 * (jstar - JULIAN_DATE_2000_01_01))
						% CONST_360);

		// Equation of center
		final double c = 1.9148 * Math.sin(m) + 0.0200 * Math.sin(2 * m)
				+ 0.0003 * Math.sin(3 * m);

		// Ecliptic longitude
		final double lambda = Math
				.toRadians((Math.toDegrees(m) + 102.9372 + c + 180) % CONST_360);

		// Solar transit (hour angle for solar noon)
		final double jtransit = jstar + 0.0053 * Math.sin(m) - 0.0069
				* Math.sin(2 * lambda);

		// Declination of the sun.
		final double delta = Math.asin(Math.sin(lambda)
				* Math.sin(Math.toRadians(23.45)));

		// Hour angle
		final double omega = Math.acos((Math.sin(Math.toRadians(-0.83)) - Math
				.sin(latitudeRad) * Math.sin(delta))
				/ (Math.cos(latitudeRad) * Math.cos(delta)));

		// Sunset
		final double jset = JULIAN_DATE_2000_01_01
				+ CONST_0009
				+ ((Math.toDegrees(omega) + longitude) / CONST_360 + n + 0.0053
						* Math.sin(m) - 0.0069 * Math.sin(2 * lambda));

		// Sunrise
		final double jrise = jtransit - (jset - jtransit);
		// Convert sunset and sunrise to Gregorian dates, in UTC
		final Calendar gregRiseUTC = getGregorianDate(jrise);
		final Calendar gregSetUTC = getGregorianDate(jset);

		// Convert the sunset and sunrise to the timezone of the day parameter
		final Calendar gregRise = Calendar.getInstance(day.getTimeZone());
		gregRise.setTimeInMillis(gregRiseUTC.getTimeInMillis());
		final Calendar gregSet = Calendar.getInstance(day.getTimeZone());
		gregSet.setTimeInMillis(gregSetUTC.getTimeInMillis());
		return new Calendar[] { gregRise, gregSet };
	}

	/**
	 * @param latitude
	 *            the latitude of the location in degrees.
	 * @param longitude
	 *            the longitude of the location in degrees (West is negative)
	 * @return true if it is currently day at the given location. This returns
	 *         true if the current time at the location is after the sunrise and
	 *         before the sunset for that location.
	 */
	public static boolean isDay(double latitude, double longitude) {
		return !isNight(latitude, longitude);
	}

	/**
	 * @param latitude
	 *            the latitude of the location in degrees.
	 * @param longitude
	 *            the longitude of the location in degrees (West is negative)
	 * @return true if it is currently night at the given location. This returns
	 *         true if the current time at the location is before the sunrise or
	 *         after the sunset for that location.
	 */
	public static boolean isNight(double latitude, double longitude) {
		Calendar today = Calendar.getInstance();
		Calendar[] sunriseSunset = getSunriseSunset(today, latitude, longitude);
		Calendar sunrise = sunriseSunset[0];
		Calendar sunset = sunriseSunset[1];
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		return now.before(sunrise) || now.after(sunset);
	}

}
