@echo off

rem This is the version for the JSWDK. It assumes
rem that if you use JDK 1.2 or 1.3, you have
rem "set JAVA_HOME=C:\jdk1.2.2" or the equivalent at
rem the top of the install_dir/startup.bat script, or
rem that you've set the JAVA_HOME variable permanently.

rem You'll need to replace path prefix before jswdk-1.0.1
rem to match your system installation.

set CLASSPATH=C:\MYDOCU~1\Marty\CSAJSP\Misc\jswdk-1.0.1\lib\servlet.jar;C:\MYDOCU~1\Marty\CSAJSP\Misc\jswdk-1.0.1\lib\jspengine.jar;C:\MYDOCU~1\Marty\CSAJSP\Java-Code
javac -d C:\MYDOCU~1\Marty\CSAJSP\Misc\jswdk-1.0.1\webpages\WEB-INF\servlets %1%
