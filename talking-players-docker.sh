#!/usr/bin/env bash
set -e
set -o pipefail

message_limit="$1"
if [ -z "$message_limit" ]; then
    echo "Please pass a number as first argument to this script to define the Message limit"
    exit 1
fi

docker build -t talking-player .

docker run --rm -e MESSAGE_LIMIT="$message_limit" talking-player
