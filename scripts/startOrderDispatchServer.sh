#!/bin/bash
# Run the order dispatch server

java -cp target/kafka-java-example-0.1.0-SNAPSHOT-jar-with-dependencies.jar seborama.demo2.kafka.orderdispatch.OrderDispatchServer $@
