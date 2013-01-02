#!/bin/bash
WORKER_JAR=../../../../weka-amqp-worker/target/weka-amqp-worker-0.0.1-SNAPSHOT.jar
TERMINATOR_JAR=../../../../weka-amqp-terminator/target/weka-amqp-terminator-0.0.1-SNAPSHOT.jar
WORKER_COUNT=4
FILTERED_VALUE=3
RESULT_FILE=results

echo "Starting terminator"
gnome-terminal -t "Terminator" -e "java -jar $TERMINATOR_JAR $RESULT_FILE"


for i in $(seq 1 $WORKER_COUNT)
do
	echo "Starting worker $i"
	gnome-terminal -t "Worker $i" -e "java -jar $WORKER_JAR $FILTERED_VALUE"
done

