#!/bin/sh
. ./classpath.rc
rm -rf example
sh ./createDB.sh
java wwapp.dbbench.App 3
