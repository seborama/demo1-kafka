#!/bin/bash

java -Dlogback.configurationFile=./logback.xml -Dorg.slf4j.simpleLogger.defaultLogLevel=info -cp target/kafka-java-example-0.1.0-SNAPSHOT-jar-with-dependencies.jar seborama.demo2.kafka.topology.OrderLifeCycleStream
