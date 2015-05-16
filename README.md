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
compile 'ca.rmen:lib-sunrise-sunset:1.0.2'
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
 <version>1.0.2</version>
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

Documentation
=============
Javadoc is here: http://caarmen.github.io/SunriseSunset/ca/rmen/sunrisesunset/SunriseSunset.html
