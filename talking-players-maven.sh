#!/usr/bin/env bash
set -e
set -o pipefail

function clean_up() {
  if [[ "$app" == "server" ]]; then
    unset MESSAGE_LIMIT
  fi
  kill 0
}

trap clean_up EXIT

app="$1"
if [[ -z "$app" ]]; then
  echo "Please pass the name of the app you want to run: 'server' or 'client'"
  exit 1
fi
if [[ "$app" == "server" ]]; then
  message_limit="$2"
  if [ -z "$message_limit" ]; then
    echo "Please pass a number as 2nd argument to this script to define the Message limit"
    exit 1
  fi

  export MESSAGE_LIMIT="$message_limit"
fi

mvn -Dmaven.test.skip=true package

if [[ "$app" == "server" ]]; then
  java -jar server/target/*uber.jar
else
  java -jar client/target/*uber.jar
fi
