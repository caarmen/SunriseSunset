package ca.rmen.sunrisesunset;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class SunriseSunset {

	private static final int JULIAN_DATE_2000_01_01 = 2451545;
	private static final double CONST_0009 = 0.0009;
	private static final double CONST_360 = 360;

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm z");

	/**
	 * http://en.wikipedia.org/wiki/Julian_day#
	 * Converting_Julian_or_Gregorian_calendar_date_to_Julian_Day_Number
	 * 
	 * @param gregorianDate
	 * @return the Julian date for the given Gregorian date.
	 */
	public static double getJulianDate(final Calendar gregorianDate) {
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
	 * the local timezone.
	 * 
	 * http://en.wikipedia.org/wiki/Julian_day#
	 * Gregorian_calendar_from_Julian_day_number
	 * 
	 * @param julianDate
	 * @return
	 */
	public static Calendar getGregorianDate(final double julianDate) {

		int J = (int) (julianDate + 0.5);// note: this shifts the epoch back by
											// one half
		int j = J + 32044;// note: this shifts the epoch back to astronomical
							// year -4800 instead of the start of the Christian
							// era in year AD 1 of the proleptic Gregorian
							// calendar.
		int g = j / 146097;
		int dg = j % 146097;
		int c = ((dg / 36524 + 1) * 3) / 4;
		int dc = dg - c * 36524;
		int b = dc / 1461;
		int db = dc % 1461;
		int a = ((db / 365 + 1) * 3) / 4;
		int da = db - a * 365;
		int y = g * 400 + c * 100 + b * 4 + a; // note: this is the integer
												// number of full years elapsed
												// since March 1, 4801 BC at
												// 00:00 UTC
		int m = (da * 5 + 308) / 153 - 2; // note: this is the integer number of
											// full months elapsed since the
											// last March 1 at 00:00 UTC
		int d = da - (m + 4) * 153 / 5 + 122;// note: this is the number of days
												// elapsed since day 1 of the
												// month at 00:00 UTC, including
												// fractions of one day)
		int year = y - 4800 + (m + 2) / 12;
		int month = (m + 2) % 12 + 1;
		int day = d + 1;

		// Create a gregorian date at midnight.
		final Calendar gregorianDateUTC = Calendar.getInstance(TimeZone
				.getTimeZone("UTC"));
		gregorianDateUTC.set(Calendar.YEAR, year);
		gregorianDateUTC.set(Calendar.MONTH, month);
		gregorianDateUTC.set(Calendar.DAY_OF_MONTH, day);
		gregorianDateUTC.set(Calendar.HOUR_OF_DAY, 0);
		gregorianDateUTC.set(Calendar.MINUTE, 0);
		gregorianDateUTC.set(Calendar.SECOND, 0);

		// Get the julian date for this Gregorian date at midnight.
		final double julianDate2 = getJulianDate(gregorianDateUTC);

		// Compare the julian date at noon and the real julian date. Add the
		// difference (fraction of a day) to the gregorian date.
		// Example: dayFraction = 0.717
		final double dayFraction = julianDate - julianDate2;
		// Ex: 0.717*24 = 17.208 hours. We truncate to 17 hours.
		final int hours = (int) (dayFraction * 24);
		// Ex: 17.208 - 17 = 0.208 days. 0.208*60 = 12.48 minutes. We truncate
		// to 12 minutes.
		final int minutes = (int) ((dayFraction * 24 - hours) * 60d);
		// Ex: 17.208*60 - (17*60 + 12) = 1032.48 - 1032 = 0.48 minutes. 0.48*60
		// = 28.8 seconds.
		// We truncate to 28 seconds.
		final int seconds = (int) ((dayFraction * 24 * 60 - (hours * 60 + minutes)) * 60d);
		gregorianDateUTC.set(Calendar.HOUR_OF_DAY, hours);
		gregorianDateUTC.set(Calendar.MINUTE, minutes);
		gregorianDateUTC.set(Calendar.SECOND, seconds);
		Calendar gregorianDate = Calendar.getInstance();
		gregorianDate.setTimeInMillis(gregorianDateUTC.getTimeInMillis());
		return gregorianDate;
	}

	/**
	 * Calculate the sunrise and sunset times for the given date and given
	 * location. This is based on the Wikipedia article on the Sunrise equation:
	 * http://en.wikipedia.org/wiki/Sunrise_equation
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
		System.out.println("getSunriseSunset day="
				+ DATE_FORMAT.format(day.getTime()) + " latitude=" + latitude
				+ " longitude=" + longitude);
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
		System.out.println("getSunriseSunset res=["
				+ DATE_FORMAT.format(gregRise.getTime()) + " ,  "
				+ DATE_FORMAT.format(gregSet.getTime()) + "]");
		return new Calendar[] { gregRise, gregSet };
	}

	/**
	 * Test the main SunriseSunset method.
	 * 
	 * @param args
	 *            : date in the format yyyyMMdd, latitude in degrees, longitude
	 *            in degrees, the timeZone to display the result
	 * @throws ParseException
	 */
	public static void main(final String args[]) throws ParseException {
		if (args.length != 4) {
			System.err
					.println("Usage: "
							+ SunriseSunset.class.getName()
							+ " <date in yyyyMMdd> <latitude in degrees> <longitude in degrees (Est=positive)> <Java timezone>");
			System.err.println("Ex: " + SunriseSunset.class.getName()
					+ " 20090602 34 118 America/Los_Angeles");
			System.exit(-1);
		}
		final String dateformat = "yyyyMMdd";
		final SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		int idx = 0;
		final Date date = sdf.parse(args[idx++]);
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		final double latitude = Double.parseDouble(args[idx++]);
		final double longitude = Double.parseDouble(args[idx++]);
		final TimeZone timeZone = TimeZone.getTimeZone(args[idx++]);
		final SimpleDateFormat sdfResult = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm z");
		sdfResult.setTimeZone(timeZone);
		final Calendar[] sunriseSunset = SunriseSunset.getSunriseSunset(cal,
				latitude, longitude);
		System.out.println("On " + sdf.format(date) + " at (" + latitude + ","
				+ longitude + ", " + timeZone.getDisplayName()
				+ "), the sun will be up from "
				+ sdfResult.format(sunriseSunset[0].getTime()) + " to "
				+ sdfResult.format(sunriseSunset[1].getTime()));

	}

	public static boolean isNight(String coordinates) {
		if (coordinates == null)
			return false;
		String[] coordStr = coordinates.split(",");
		double lat = Double.valueOf(coordStr[0]);
		double lon = Double.valueOf(coordStr[1]);
		Calendar today = Calendar.getInstance();
		// today.set(Calendar.HOUR_OF_DAY, 0);
		// today.set(Calendar.MINUTE, 0);
		// today.set(Calendar.SECOND, 0);
		// today.set(Calendar.MILLISECOND, 0);
		Calendar[] sunriseSunset = getSunriseSunset(today, lat, lon);
		Calendar sunrise = sunriseSunset[0];
		Calendar sunset = sunriseSunset[1];
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		System.out.println("isNight now=" + DATE_FORMAT.format(now.getTime()));
		return now.before(sunrise) || now.after(sunset);
	}
}
