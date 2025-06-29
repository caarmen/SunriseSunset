#!/usr/bin/env bash
logprefix=$0
echo "${logprefix}: Rebuilding project..."
mvn clean
if [ "${GPGKEY}" != "" ]
then
  # To sign the package, use the following command to find the name of your gpg key:
  # gpg --list-signatures --keyid-format 0xshort
  # The name of the key is the first token after "sig 3".
  # See https://central.sonatype.org/publish/requirements/gpg/#listing-keys for more info.
  # Set the environment variable GPGKEY to the name of the key to use.
  mvn -Dgpg.keyname="${GPGKEY}" package gpg:sign
else
  mvn package
fi
echo "${logprefix}: Project rebuilt".
echo "${logprefix}: Extracting version from pom file..."
version=$(mvn -q exec:exec -Dexec.executable="echo" -Dexec.args="\${project.version}" --non-recursive)
echo "${logprefix}: Version is $version."
echo "${logprefix}: Installing to local maven repository..."
mvn install -f publish.pom
mvn install:install-file \
  -Dfile="library/target/lib-sunrise-sunset-$version.jar" \
  -Djavadoc="library/target/lib-sunrise-sunset-${version}-javadoc.jar" \
  -Dsources="library/target/lib-sunrise-sunset-${version}-sources.jar"
echo "${logprefix}: Done."
