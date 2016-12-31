#!/bin/sh
logprefix=$0
echo "$logprefix: Rebuilding project..."
mvn clean package
echo "$logprefix: Project rebuilt".
echo "$logprefix: Extracting version from pom file..."
version=`mvn -q exec:exec -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive`
echo "$logprefix: Version is $version."
echo "$logprefix: Installing to local maven repository..."
mvn install -f publish.pom
mvn install:install-file -Dfile=library/target/lib-sunrise-sunset-$version.jar -Djavadoc=library/target/lib-sunrise-sunset-$version-javadoc.jar -Dsources=library/target/lib-sunrise-sunset-$version-sources.jar
echo "$logprefix: Done."
