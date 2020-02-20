# chinchopa

> Provides example code on how to parse Dota2 replays

## Build

```shell script
docker build . -t chinchopa
```

## Run

```shell script
docker run -v /Users/gamer/Downloads:/replays --rm -it chinchopa:latest /replays/replay.dem
```

## Output
```
time,victim,slayer
00:05:39.338,creep_goodguys_ranged,hero_monkey_king
00:05:47.336,creep_goodguys_melee,creep_badguys_melee
00:05:48.836,creep_badguys_melee,hero_morphling
00:05:49.469,creep_goodguys_melee,creep_badguys_ranged
00:05:51.069,creep_goodguys_ranged,creep_badguys_melee
00:05:55.334,creep_goodguys_melee,hero_huskar
00:05:56.434,creep_goodguys_melee,creep_badguys_melee
...
```