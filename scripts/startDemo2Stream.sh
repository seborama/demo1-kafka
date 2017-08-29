#!/bin/bash

java -Dlogback.configurationFile=/Users/seb/Dropbox/Documents/SEB-MBP-OSX/Non-Sensitive/Projects/Java/demo1-kafka/logback.xml -Dorg.slf4j.simpleLogger.defaultLogLevel=info -cp target/kafka-java-example-0.1.0-SNAPSHOT-jar-with-dependencies.jar seborama.demo2.kafka.topology.OrderLifeCycleStream
