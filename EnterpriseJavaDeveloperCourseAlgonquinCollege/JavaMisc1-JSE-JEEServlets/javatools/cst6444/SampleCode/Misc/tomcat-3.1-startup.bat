@echo off
rem $Id: startup.bat,v 1.6 2000/02/26 19:41:46 rubys Exp $
rem Startup batch file for tomcat servner.

rem This batch file written and tested under Windows NT
rem Improvements to this file are welcome

rem Marty Hall: Added JAVA_HOME setting. Needed for JSP.
rem Change to match your system setting!
set JAVA_HOME=C:\jdk1.2.2

if not "%TOMCAT_HOME%" == "" goto start

SET TOMCAT_HOME=.
if exist %TOMCAT_HOME%\bin\tomcat.bat goto start

SET TOMCAT_HOME=..
if exist %TOMCAT_HOME%\bin\tomcat.bat goto start

SET TOMCAT_HOME=
echo Unable to determine the value of TOMCAT_HOME.
goto eof

:start
call %TOMCAT_HOME%\bin\tomcat start %*

:eof
