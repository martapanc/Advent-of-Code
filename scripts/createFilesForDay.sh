#!/bin/bash

srcDir="src/main/kotlin/aoc2021"
day=$1

echo "Creating files for 2021: Day $1"

if [ -f .env ]
then
  export "$(cat .env | grep -v '#' | awk '/=/ {print $1}')"
fi

cd $srcDir || exit

if [ "$day" -lt 10 ]; then
  dir_name="day0$day"
else
  dir_name="day$day"
fi

mkdir "$dir_name"
cd "$dir_name" || exit

touch "Day$day.kt"

mkdir "assets"
cd "assets" || exit
touch "input0"

date
curl --header "Cookie: session=$SESSION" "https://adventofcode.com/2021/day/$day/input" > "input"

