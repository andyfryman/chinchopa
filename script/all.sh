sh ./script/build.sh
sh ./script/stat-all.sh
node ./script/stat.js > ./data/stat-all.csv
node ./script/stat-concat.js > ./data/stat.csv
