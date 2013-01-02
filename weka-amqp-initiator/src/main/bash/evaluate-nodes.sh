#!/bin/bash
INITIATOR_JAR_FILE="../../../target/weka-amqp-initiator-0.0.1-SNAPSHOT.jar"
TEST_DATA_FILE="../../../../data/shuttle.tst"

java -jar $INITIATOR_JAR_FILE -e $TEST_DATA_FILE
