#!/bin/sh

set -eu

hod_host=$1
auth_host=$2

java -Djclarity.hod.host=$hod_host -DJMX_AUTH_SERVER_HOST=$auth_host -DJMX_AUTH_SERVER_PORT=1100 -jar sca-test-harness.jar &
pid=$!
echo $pid > test_harness_pid

