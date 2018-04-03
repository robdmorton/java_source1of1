@echo off
setlocal

if "%BORK_HOME%"=="" set BORK_HOME=c:\development\bork

if not exist "%BORK_HOME%" set BORK_HOME=%CD%
if not exist "%BORK_HOME%\bork.jar" set BORK_HOME=c:\bork
if not exist "%BORK_HOME%\bork.jar" set BORK_HOME=c:\program files\bork

if not exist "%BORK_HOME%" echo Please set BORK_HOME && pause && exit 1

set /p PASSWORD="Enter password: "

java "-Dbork.password=%PASSWORD%" -cp "%BORK_HOME%\bork.jar" org.matthew.bork.Bork %*

set PASSWORD=

pause
