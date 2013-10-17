#!/bin/sh

set -eu

echo "Make sure you've got your JREBEL_JAR variable set to point to jrebel.jar"

export MAVEN_OPTS="-noverify -javaagent:$JREBEL_JAR -XX:PermSize=512m -XX:MaxPermSize=1G"

mvn -Drebel.spring_data_plugin=true -Drebel.remoting_plugin=true -Djetty.reload=manual jetty:run

