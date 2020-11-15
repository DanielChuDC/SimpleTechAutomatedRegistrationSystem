@echo off

set CLASSPATH=%CLASSPATH%.;javax\mail.jar;javax\activation.jar;
echo %CLASSPATH%

echo Compiling...
javac App.java cx2002grp2/stars/functions/*.java
echo Finished compiling

echo ==========RUN==========
java App
echo ==========END==========

echo Cleaning .class files
del /s /q /f *.class
echo Program end

pause
