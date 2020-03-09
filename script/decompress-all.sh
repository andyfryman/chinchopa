for filename in data/download/*.dem.bz2; do
    [ -e "$filename" ] || continue
    file=$(basename $filename)
    name="${file%.*}"
    sh ./script/decompress.sh $name
done