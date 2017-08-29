#!/bin/bash

for i in {1..10000}
do
    echo '{ "id": "id'$i'", "created": true, "fulfilled": false, "dispatched": false, "completed": false }'
done > order-creation-streams-topic.records.in

