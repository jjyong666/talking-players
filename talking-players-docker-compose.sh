#!/usr/bin/env bash
set -e
set -o pipefail

function clean_up() {
  unset MESSAGE_LIMIT
	kill 0
}

trap clean_up EXIT

message_limit="$1"
if [ -z "$message_limit" ]; then
  echo "Please pass a number as 1st argument to this script to define the Message limit"
  exit 1
fi
export MESSAGE_LIMIT="$message_limit"

docker-compose run --rm talking-players-client
