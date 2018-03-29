@echo off

rem This is the version for the Java Web Server

set CLASSPATH=C:\JavaWebServer2.0\lib\servlet.jar;C:\JavaWebServer2.0\lib\jsp.jar;C:\MYDOCU~1\Marty\CSAJSP\Java-Code;C:\MYDOCU~1\Marty\CSAJSP\Java-Code\JDBC
C:\JDK1.1.8\bin\javac -d C:\JavaWebServer2.0\servlets %1%
