@echo off
rem $Id: startup.bat,v 1.2 1999/12/05 17:02:42 harishp Exp $
rem Startup batch file for tomcat servner.

rem Marty Hall: added JAVA_HOME setting below. 
rem Change to match your system setting!
set JAVA_HOME=C:\jdk1.2.2

rem This batch file written and tested under Windows NT
rem Improvements to this file are welcome
call tomcat start  
rem pause
