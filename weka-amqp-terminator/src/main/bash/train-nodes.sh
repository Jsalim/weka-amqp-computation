#!/bin/bash
MANAGER_JAR_FILE="../../../target/weka-manager-0.0.1-SNAPSHOT.jar"
TRAIN_DATA_FILE="../../../../data/shuttle.trn"
CLASSIFIER="NaiveBayes"
PROBLEM="SHUTTLE"
NODES=""

for i in $(seq 1 $1)
do
	PORT=$((8080 + i))
	NODES="$NODES http://localhost:$PORT/weka-node"
done

java -jar $MANAGER_JAR_FILE -n $NODES -t $TRAIN_DATA_FILE -p $PROBLEM -c $CLASSIFIER
