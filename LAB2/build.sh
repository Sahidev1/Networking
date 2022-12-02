#!/bin/bash
set -e

FILENAME="temp0q209i013j091z.txt"
find -name "*.java" > $FILENAME
javac @"$FILENAME"
rm $FILENAME

echo "Build successfull!"

echo "Usage: java Main [HOST IP]  [HOST PORT]"


