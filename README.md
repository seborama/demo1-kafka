# Apache Kafka Java Examples

Ordering System:

  - Order creation
  - Order fulfilment
  - Order dispatch
  - Order completion

![Image of Ordering System](https://raw.githubusercontent.com/seborama/demo1-kafka/master/docs/Kafka%20Demo1%20-%20Producer%20Consumer%20Architecture.png)

## Running the project

### Build the project

```bash
mvn clean package
```

### Run the order creation server

```bash
./scripts/startOrderCreationServer.sh [-sleepduration <time in milliseconds>] [-numbermessages <number of messages>]
```

### Run the order fulfilment server

```bash
./scripts/startOrderFulfilmentServer.sh [-sleepduration <time in milliseconds>] [-numbermessages <number of messages>]
```

### Run the order dispatch server

```bash
./scripts/startOrderDispatchServer.sh [-sleepduration <time in milliseconds>] [-numbermessages <number of messages>]
```

### Run the order completion server

```bash
./scripts/startOrderCompletionServer.sh [-sleepduration <time in milliseconds>] [-numbermessages <number of messages>]
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

