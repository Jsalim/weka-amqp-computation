#!/bin/bash
NODE_MVN_DIR=../../../../weka-node
for i in $(seq 1 $1)
do
	PORT=$((8080 + i))
	echo "Starting node $i on port $PORT"
	gnome-terminal -x bash -c "cd $NODE_MVN_DIR; mvn tomcat7:run -Dmaven.tomcat.port=$PORT"
done

