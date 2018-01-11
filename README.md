# Apache Kafka Java Examples

Ordering System phases:

  - Order creation
  - Order fulfilment
  - Order dispatch
  - Order completion

## Pub/Sub design

![Image of Ordering System](https://raw.githubusercontent.com/seborama/demo1-kafka/master/docs/Kafka%20Demo1%20-%20Producer%20Consumer%20Architecture.png)

## Topology design

(image to be added)

## Preparing to run the project

### Get Kafka

The simplest approach for OSX is to use `brew`:

```bash
brew install kafka
```

For the purpose of testing, I recommend editing `/usr/local/etc/kafka/server.properties` and make these changes:

```
delete.topic.enable=true
listeners=PLAINTEXT://127.0.0.1:9092
```

### Initialise Kafka topics

The End-2-End test will create the necessary Kafka topics with the desired features (partitions, etc) for "`seborama.demo1`".

```bash
mvn -Dtest=seborama.demo1.kafka.e2e.EndToEndTest test
```

For "`seborama.demo2`", you can use this command:

```bash
mvn -Dtest=seborama.demo2.kafka.e2e.EndToEndTest test
```

**BEWARE**

Do not change topic partitioning once the stream has been initialised.

When testing only, you can delete / reset the streams. In production, you simply just should **not** do that.

### Package the project

```bash
mvn clean package
```

## Running the project - producers / consumers

### Producer

#### Run the order creation server

```bash
./scripts/startOrderCreationServer.sh [-sleepduration <time in milliseconds>] [-numbermessages <number of messages>]
```

### Independent Consumers

Consumers detached from a consumer group or separate consumer groups.

More explanation TBA.

### Coordinated Consumers (consumer groups)

Note: it is possible and recommended to start several of each of the consumers.

#### Run the order fulfilment server

```bash
./scripts/startOrderFulfilmentServer.sh [-sleepduration <time in milliseconds>] [-numbermessages <number of messages>]
```

#### Run the order dispatch server

```bash
./scripts/startOrderDispatchServer.sh [-sleepduration <time in milliseconds>] [-numbermessages <number of messages>]
```

#### Run the order completion server

```bash
./scripts/startOrderCompletionServer.sh [-sleepduration <time in milliseconds>] [-numbermessages <number of messages>]
```

## Streams

Micro-service architecture evolved: from imperative ("how") to declarative ("what") design.

## Running the stream

### Generate the stream's input data

```bash
./scripts/generateDemo2Messages.sh
```

### Run the stream

```bash
./scripts/startDemo2Stream.sh
```

### Produce messages for the stream

```bash
./scripts/startDemo2Producer.sh
```

## Useful CLI

### Number of messages in the topic

```bash
kafka-run-class kafka.tools.GetOffsetShell --broker-list :9092 --topic OrderCreationTopic --time -1 --offsets 1 | awk -F  ":" '{sum += $3} END {print sum}'
```

### List consumer groups (new consumer groups i.e. kafka managed rather than zookeeper)

```bash
kafka-consumer-groups --bootstrap-server localhost:9092 --list
```

### Describe consumer group

```bash
kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group order-fulfilment-group-1
```

## See also

I recommend this article on DZone for advanced offset management and only-once message processing:

https://dzone.com/articles/kafka-clients-at-most-once-at-least-once-exactly-o
