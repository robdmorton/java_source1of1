#!/bin/sh

if [ -z $BORK_HOME ] ; then
  BORK_HOME=$HOME/Development/bork-1.0
fi

if [ ! -d $BORK_HOME ] ; then
  BORK_HOME=$PWD
fi

if [ ! -d $BORK_HOME ] ; then
  echo Please set BORK_HOME
  exit 1
fi

if [ -z $BORK_PASSWORD ] ; then 
  echo Please set BORK_PASSWORD
  exit 2
fi

java "-Dbork.password=$BORK_PASSWORD" -Dbork.nuke=true -cp "$BORK_HOME/bork.jar" org.matthew.bork.Bork $*
