#!/bin/bash
FILENAME="temp0q209i013j091z.txt"
find -name "*.java" > $FILENAME
javac @"$FILENAME"
rm $FILENAME

