#!/bin/bash
# Run the order completion server

java -cp target/kafka-java-example-0.1.0-SNAPSHOT-jar-with-dependencies.jar seborama.demo2.kafka.ordercompletion.OrderCompletionServer $@
