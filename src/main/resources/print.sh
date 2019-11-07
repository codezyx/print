#!/bin/bash
export APP_HOME=/users/ems/print
java -jar -Dfile.encoding=GBK -XX:+UseG1GC -Xbootclasspath/a:$APP_HOME/conf $APP_HOME/lib/dvr-1.0.jar