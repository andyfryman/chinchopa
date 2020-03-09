const path = require('path');
const fs = require('fs');
const header = "name,team,level,experience,gold,lasthit,deny,kill,death,assist,time,match,won";
const text = fs.readFileSync('./data/stat-all.csv').toString('utf-8');
const result = text.replace(/(^[ \t]*\n)/gm, "");
console.log(header);
console.log(result);