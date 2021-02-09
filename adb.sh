#!/usr/bin/env sh

# https://developer.android.com/studio/command-line/adb
ip_port=$1
if [[ -z $ip_port ]]
then
  echo "No port specified"
  exit 1
fi

adb_cmd="$HOME/Library/Android/sdk/platform-tools/adb"

$adb_cmd pair $ip_port