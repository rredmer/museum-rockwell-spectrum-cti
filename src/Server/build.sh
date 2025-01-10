#!/bin/sh
# build -- Build Script for "Droid"

# Identify the custom class path components we need
CP=$TOMCAT_HOME/classes:$TOMCAT_HOME/lib/ant.jar:$TOMCAT_HOME/lib/xml.jar
CP=$CP:$TOMCAT_HOME/lib/jasper.jar:$TOMCAT_HOME/lib/servlet.jar
CP=$CP:$TOMCAT_HOME/lib/webserver.jar

# Execute ANT to perform the requested build target
java -Dtomcat.home=$TOMCAT_HOME -classpath $CP:$CLASSPATH \
   org.apache.tools.ant.Main "$@"
