SunriseSunset
=============

Java utility which provides methods to determine the sunrise, sunset, civil twilight,
nautical twilight, and astronomical twilight times of a given
location.

Also provides methods:
* to convert between Gregorian and Julian dates.
* to know if it is currently day or night at a given location.

The formulas used by this class are from the Wikipedia articles on [Julian Day] (http://en.wikipedia.org/wiki/Julian_day)
and [Sunrise Equation] (http://en.wikipedia.org/wiki/Sunrise_equation).
  
Adding the dependency
=====================

Gradle:
-------
Add this repository:
```
maven { url "https://dl.bintray.com/caarmen/maven/" }
```

Declare the dependency:
```
compile 'ca.rmen:lib-sunrise-sunset:1.1.1'
```
Maven:
------
Add this repository:
```
<repository>
 <id>caarmen-repo</id>
 <url>https://dl.bintray.com/caarmen/maven/</url>
</repository>
```
Declare the dependency:
```
<dependency>
 <groupId>ca.rmen</groupId>
 <artifactId>lib-sunrise-sunset</artifactId>
 <version>1.1.1</version>
 <scope>compile</scope>
</dependency>
```

Any other build system:
-------------------

Alternatively, you can just copy the single java file into your project:
[library/src/main/java/ca/rmen/sunrisesunset/SunriseSunset.java](library/src/main/java/ca/rmen/sunrisesunset/SunriseSunset.java)

Using the library
=================
To get the sunrise and sunset times for today in Paris, France:
```
Calendar[] sunriseSunset = ca.rmen.sunrisesunset.SunriseSunset.getSunriseSunset(Calendar.getInstance(), 48.85837, 2.294481);
System.out.println("Sunrise at: " + sunriseSunset[0].getTime());
System.out.println("Sunset at: " + sunriseSunset[1].getTime());
```

The civil, nautical, and astronomical twilight times are retrieved in the same way:
```
Calendar[] civilTwilight = ca.rmen.sunrisesunset.SunriseSunset.getCivilTwilight(Calendar.getInstance(), 48.85837, 2.294481);
System.out.println("Civil twilight stops at: " + civilTwilight[0].getTime());
System.out.println("Civil twilight starts at: " + civilTwilight[1].getTime());

Calendar[] nauticalTwilight = ca.rmen.sunrisesunset.SunriseSunset.getNauticalTwilight(Calendar.getInstance(), 48.85837, 2.294481);
System.out.println("Nautical twilight stops at: " + nauticalTwilight[0].getTime());
System.out.println("Nautical twilight starts at: " + nauticalTwilight[1].getTime());

Calendar[] astronomicalTwilight = ca.rmen.sunrisesunset.SunriseSunset.getAstronomicalTwilight(Calendar.getInstance(), 48.85837, 2.294481);
System.out.println("Astronomical twilight stops at: " + astronomicalTwilight[0].getTime());
System.out.println("Astronomical twilight starts at: " + astronomicalTwilight[1].getTime());
```

Command-line interface
======================

A command-line tool is provided. You can download it from the [releases page](https://github.com/caarmen/SunriseSunset/releases)
or build it with `mvn clean package`, which will place it in `cli/target`.

Usage:

```
java -jar /path/to/sunrise-sunset-cli-1.1.1.jar <timezone> <yyyyMMdd> <latitude> <longitude>
```

Example usage:
```
java -jar /path/to/sunrise-sunset-cli-1.1.1.jar Europe/Paris 20171125 48.8 2.35
Current time at: 48.8,2.35:
  2017-11-25 18:01:42 Central European Time
Current day period is NAUTICAL_TWILIGHT
Day is 31588000 milliseconds long
Lookup for date:
  2017-11-25 12:00:00 Central European Time
Sunrise, Sunset:
  2017-11-25 08:15:53 Central European Time
  2017-11-25 17:02:21 Central European Time
Civil twilight:
  2017-11-25 07:40:16 Central European Time
  2017-11-25 17:37:58 Central European Time
Nautical twilight:
  2017-11-25 07:01:06 Central European Time
  2017-11-25 18:17:08 Central European Time
Astronomical twilight:
  2017-11-25 06:23:26 Central European Time
  2017-11-25 18:54:48 Central European Time
Solar noon:
  2017-11-25 12:39:07 Central European Time
```


Documentation
=============
Javadoc is here: http://caarmen.github.io/SunriseSunset/ca/rmen/sunrisesunset/SunriseSunset.html
