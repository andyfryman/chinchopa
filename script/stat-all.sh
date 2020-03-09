for filename in data/replay/*.dem; do
    [ -e "$filename" ] || continue
    file=$(basename $filename)
    name="${file%.*}"
    echo $name
    sh ./script/run.sh /Users/gamer/Documents/GitHub/chinchopa/data/replay $file stats > ./data/stat/$name.csv
done