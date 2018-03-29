#!/bin/sh

export ANT_HOME=$PWD/ant/bin

#./ant/bin/ant

java $JAVA_OPTIONS -cp classes:ant/ant.jar:$JAVA_HOME/lib/tools.jar -Dant.home=ant org.apache.tools.ant.Main $ANT_ARGS $*
