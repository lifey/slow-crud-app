#!/bin/sh

set -eu

java -Dcom.sun.management.jmxremote.port=1100 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -jar auth.war 9876 &
pid=$!
echo $pid > auth_pid

