#!/bin/sh
REPLAY_PATH="/Users/gamer/Downloads"
REPLAY_FILE="replay.dem"
REPLAY_COMM=()
if [ "$#" -eq  "0" ]
  then
    REPLAY_COMM=(ability item purchase damage death experience gold heal debug)
  else
    REPLAY_COMM=($1)
fi
for i in "${REPLAY_COMM[@]}"
do
  echo "[EXAMPLE]" $i
  sh ./script/run.sh $REPLAY_PATH $REPLAY_FILE $i > ./example/$i.csv
done
