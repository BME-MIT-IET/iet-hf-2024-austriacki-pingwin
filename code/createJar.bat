@echo off

echo Program forditasa...
javac -d bin -classpath lib/log4j-api-2.20.0.jar;lib/log4j-core-2.20.0.jar;res src/proto/*.java src/test/*.java src/model/*.java src/model/characters/*.java src/model/fields/*.java

echo Jar fajl keszitese...
jar cfm ad4b_proto.jar META-INF/MANIFEST.MF -C bin . -C res log4j2.xml


pause