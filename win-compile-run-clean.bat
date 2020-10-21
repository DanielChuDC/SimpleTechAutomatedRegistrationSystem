@echo off

echo Compiling...
javac *.java cx2002grp2/stars/*.java
echo Finished compiling

echo ==========RUN==========
java App
echo ==========END==========

echo Cleaning .class files
del *.class cx2002grp2\stars\*.class
echo Program end

pause
