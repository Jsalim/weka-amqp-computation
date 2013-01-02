#!/bin/bash
INITIATOR_JAR_FILE="../../../target/weka-amqp-initiator-0.0.1-SNAPSHOT.jar"
TRAIN_DATA_FILE="../../../../data/shuttle.trn"

java -jar $INITIATOR_JAR_FILE -t $TRAIN_DATA_FILE
