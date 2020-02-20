#!/bin/sh
REPLAY_PATH="/Users/gamer/Downloads"
REPLAY_FILE="replay.dem"
REPLAY_COMM=(damage death experience gold heal)
for i in "${REPLAY_COMM[@]}"
do
  echo $i
  sh ./script/run.sh $REPLAY_PATH $REPLAY_FILE $i > ./example/$i.csv
done
