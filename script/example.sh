#!/bin/sh
REPLAY_PATH="/Users/gamer/Downloads"
REPLAY_FILE="replay.dem"
REPLAY_COMM=(purchase damage death experience gold heal)
for i in "${REPLAY_COMM[@]}"
do
  echo "[EXAMPLE] " $i
  sh ./script/run.sh $REPLAY_PATH $REPLAY_FILE $i > ./example/$i.csv
done
