#!/bin/bash
NODE_JAR=../../../../weka-amqp-node/target/weka-amqp-node-0.0.1-SNAPSHOT.jar
TERMINATOR_JAR=../../../../weka-amqp-terminator/target/weka-amqp-terminator-0.0.1-SNAPSHOT.jar
NODE_COUNT=4
FILTERED_VALUE=3
RESULT_FILE=results

echo "Starting terminator"
gnome-terminal -t "Terminator" -e "java -jar $TERMINATOR_JAR $RESULT_FILE"


for i in $(seq 1 $NODE_COUNT)
do
	echo "Starting node $i"
	gnome-terminal -t "Node $i" -e "java -jar $NODE_JAR $FILTERED_VALUE"
done

