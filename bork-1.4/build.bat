@echo off
if "%OS%"=="Windows_NT" setlocal

if "%JAVA_HOME%"=="" echo Please set JAVA_HOME && exit 1

java %JAVA_OPTIONS% -cp classes;ant\ant.jar;%JAVA_HOME%\lib\tools.jar -Dant.home=ant org.apache.tools.ant.Main %ANT_ARGS% %1 %2 %3 %4 %5

:end
