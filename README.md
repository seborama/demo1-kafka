# Apache Kafka Java Examples

Ordering System:

  - Order creation
  - Order Fulfilment
  - Order dispatch
  - Order completion
  - TBC: Order returns?


## Running the project

Under construction...

### Build the project

```bash
mvn package
```

### Run the order creation server

```bash
./scripts/startOrderCreationServer.sh
```

### Run the order fulfilment server

```bash
./scripts/startOrderFulfilmentServer.sh
```

### Run the order dispatch server

```bash
./scripts/startOrderDispatchServer.sh
```

### Run the order completion server

```bash
./scripts/startOrderCompletionServer.sh
```

## Producers

## Independent Consumers

## Coordinated Consumers (consumer groups)

## Streams

Micro-service architecture evolved: from imperative ("how") to declarative ("what") design.

## Userful CLI

### Number of messages in the topic

```bash
kafka-run-class kafka.tools.GetOffsetShell --broker-list :9092 --topic OrderCreationTopic --time -1 --offsets 1 | awk -F  ":" '{sum += $3} END {print sum}'
```

