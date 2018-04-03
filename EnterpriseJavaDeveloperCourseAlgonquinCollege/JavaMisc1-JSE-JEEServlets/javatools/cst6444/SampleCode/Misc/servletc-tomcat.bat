@echo off

rem This is the version for Tomcat. It assumes
rem that if you use JDK 1.2 or 1.3, you have
rem "set JAVA_HOME=C:\jdk1.2.2" or the equivalent at
rem the top of the install_dir\startup.bat script, or
rem that you've set the JAVA_HOME variable permanently.

rem You'll need to replace path prefix before tomcat
rem to match your system installation.

set CLASSPATH=C:\MYDOCU~1\Marty\CSAJSP\Misc\tomcat\lib\servlet.jar;C:\MYDOCU~1\Marty\CSAJSP\Misc\tomcat\lib\jasper.jar;C:\MYDOCU~1\Marty\CSAJSP\Java-Code
javac -d C:\MYDOCU~1\Marty\CSAJSP\Misc\tomcat\webpages\WEB-INF\classes %1%
