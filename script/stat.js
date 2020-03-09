//requiring path and fs modules
const path = require('path');
const fs = require('fs');
//joining path of directory
const directoryPath = '/Users/gamer/Documents/GitHub/chinchopa/data/stat';

//passsing directoryPath and callback function
var res = [];
fs.readdir(directoryPath, function (err, files) {
    //handling error
    if (err) {
        return console.log('Unable to scan directory: ' + err);
    }
    //listing all files using forEach
    files.forEach(function (file) {

        var content = fs.readFileSync('./data/stat/' + file).toString('utf-8');
        var split = content.split('\n').slice(14);
        res.push(split);
        console.log(split.join('\n'));
        // Do whatever you want to do with the file
    });
//console.log(res.join('\n'));
});