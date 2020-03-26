#!/usr/bin/env bash
set -e
set -o pipefail

message_limit="$1"
if [ -z "$message_limit" ]; then
    echo "Please pass a number as argument to this script to define the Message limit"
    exit 1
fi

export MESSAGE_LIMIT="$message_limit"

mvn -Dmaven.test.skip=true package

java -jar target/*.jar
