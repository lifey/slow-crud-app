#!/bin/sh

set -eu

auth_host=$1

java -Djclarity.auth.host=$auth_host -jar had_one_dismissal.war 8080

