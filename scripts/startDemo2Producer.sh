#!/bin/bash

kafka-console-producer --topic order-creation-streams-topic --broker-list 127.0.0.1:9092 < order-creation-streams-topic.records.in
