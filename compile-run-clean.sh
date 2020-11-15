#!/bin/bash

export CLASSPATH="$CLASSPATH.:javax/mail.jar:javax/activation.jar:"

echo Compiling...
javac App.java cx2002grp2/stars/functions/*.java
echo Finished compiling

echo ==========RUN==========
java App
echo ==========END==========

echo Cleaning .class files
find . -name *.class  -type f -print -delete
find . -name *.class  -type f -print -delete
echo Program end
