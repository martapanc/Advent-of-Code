#!/bin/bash

srcDir="src/main/kotlin/aoc2023"
day=$1
current_year=$(date +'%Y')

echo "Creating files for $current_year: Day $1"

# Read values from .env files
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

# Append line to README.md
echo "| [Day $day](https://adventofcode.com/$current_year/day/$day)   |  | [code]($dir_name/Day$day.kt) | [tests](../../../test/kotlin/aoc${current_year}/$dir_name/Day${day}KtTest.kt) |  |  |" >> "README.md"

# Create src dir for the day
mkdir "$dir_name"
cd "$dir_name" || exit

# Create Main class
echo "package aoc${current_year}.${dir_name}" > "Day${day}.kt"

# Create test input files
mkdir "assets"
cd "assets" || exit
touch "input0"

date
curl --header "Cookie: session=$AOC_SESSION" "https://adventofcode.com/$current_year/day/$day/input" > "input"
