#!/usr/bin/env bash
set -e
set -o pipefail

name="talking-players"

app="$1"
if [[ -z "$app" ]]; then
  echo "Please pass the name of the app you want to run: 'server' or 'client'"
  exit 1
fi

docker build -t "$name" .

if [[ "$app" == "server" ]]; then
  message_limit="$2"
  if [ -z "$message_limit" ]; then
    echo "Please pass a number as 2nd argument to this script to define the Message limit"
    exit 1
  fi

  docker run --rm \
    -e JAR="$app" \
    -e MESSAGE_LIMIT="$message_limit" \
    -p 50000:50000 \
    "$name"
else
  docker run -it --rm --network host -e JAR=client "$name"
fi
