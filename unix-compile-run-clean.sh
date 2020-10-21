#!/bin/bash

echo Compiling...
javac *.java cx2002grp2/stars/*.java
echo Finished compiling

echo ==========RUN==========
java App
echo ==========END==========

echo Cleaning .class files
rm -f *.class cx2002grp2/stars/*.class
echo Program end
