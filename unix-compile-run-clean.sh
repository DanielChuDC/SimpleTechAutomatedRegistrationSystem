#!/bin/bash

export CLASSPATH=".;javax/mail.jar;javax/activation.jar"

echo Compiling...
javac *.java
echo Finished compiling

echo ==========RUN==========
java App
echo ==========END==========

echo Cleaning .class files
find . -name *.class -type f -printf "Deleting: %s\n" -delete
echo Program end
