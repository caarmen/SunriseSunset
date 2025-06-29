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

# Release
echo "${logprefix}: Creating bundle for distribution"
# Copy the already signed library artifacts to a temporary directory.
tmpdir=$(mktemp --directory -t bundle -p /tmp/)
destdir="${tmpdir}/ca/rmen/lib-sunrise-sunset/${version}"
srcdir=library/target
bundle_file="target/bundle-${version}.jar"
mkdir -p "${destdir}"
cp -pr "${srcdir}"/*.jar* "${destdir}"

# Copy the publish.pom file and to the target diretory and sign it.
cp publish.pom "${destdir}/lib-sunrise-sunset-${version}.pom"
if [ "${GPGKEY}" != "" ]
then
  gpg --local-user "${GPGKEY}" -ab "${destdir}/lib-sunrise-sunset-${version}.pom"
fi

# Add md5 and sha files for all the artifacts.
for file in "${destdir}"/*.jar "${destdir}"/*.asc "${destdir}"/*.pom
do
  md5 -q "${file}" > "${file}".md5
  for algo in 1 256 512
  do
    shasum -a "${algo}" "${file}" | awk '{ print $1 }' > "${file}.sha${algo}"
  done
done

# Create a bundle containing everything (jar, asc, pom files, and all their md5 and sha corresponding files).
jar cfM "${bundle_file}" -C "${tmpdir}" ca
echo "${logprefix} created ${bundle_file}"
