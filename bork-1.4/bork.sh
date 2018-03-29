#!/bin/sh

if [ -z "$BORK_HOME" ] ; then
  BORK_HOME=$PWD
fi

if [ -z "$BORK_HOME" ] ; then
  echo Please set BORK_HOME
  exit 1
fi

if [ ! -d "$BORK_HOME" ] ; then
  echo BORK_HOME is not a directory
  exit 1
fi

if [ -z "$BORK_PASSWORD" ] ; then 
  echo Please set BORK_PASSWORD
  exit 2
fi

java "-Dbork.password=$BORK_PASSWORD" -cp "$BORK_HOME/bork.jar" \
  $BORK_OPTIONS org.matthew.bork.Bork $*
