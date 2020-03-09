REPLAY_FILE=$1
echo "[DECOMPRESS]" $REPLAY_FILE
bunzip2 ./data/download/$REPLAY_FILE.bz2 --keep --stdout > ./data/replay/$REPLAY_FILE
