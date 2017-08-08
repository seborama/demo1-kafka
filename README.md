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
java -cp target/kafka-java-example-0.1.0-SNAPSHOT-jar-with-dependencies.jar seborama.demo1.kafka.OrderCreationServer
```

### Run the order fulfilment server

```bash
java -cp target/kafka-java-example-0.1.0-SNAPSHOT-jar-with-dependencies.jar seborama.demo1.kafka.OrderFulfilmentServer
```

### Run the order dispatch server

```bash
java -cp target/kafka-java-example-0.1.0-SNAPSHOT-jar-with-dependencies.jar seborama.demo1.kafka.OrderDispatchServer
```

### Run the order completion server

```bash
java -cp target/kafka-java-example-0.1.0-SNAPSHOT-jar-with-dependencies.jar seborama.demo1.kafka.OrderCompletionServer
```

## Producers

## Independent Consumers

## Coordinated Consumers (consumer groups)

## Streams

Micro-service architecture evolved: from imperative ("how") to declarative ("what") design.
