/*
 * Sunrise Sunset Calculator.
 * Copyright (C) 2016 Carmen Alvarez
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

/**
 *
 * Java utility which provides methods to determine the sunrise, sunset, civil twilight,
 * nautical twilight, and astronomical twilight times of a given
 * location.
 *
 * Also provides methods:
 * <ul>
 * <li> to convert between Gregorian and Julian dates.
 * <li> to know if it is currently day or night at a given location.
 * </ul>
 *
 * The formulas used by this class are from the Wikipedia articles on <a href="http://en.wikipedia.org/wiki/Julian_day">Julian Day</a>
 * and <a href="http://en.wikipedia.org/wiki/Sunrise_equation">Sunrise equation</a>.
 */
package ca.rmen.sunrisesunset;
