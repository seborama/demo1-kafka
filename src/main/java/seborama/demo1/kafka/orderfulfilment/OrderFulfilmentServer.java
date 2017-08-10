package seborama.demo1.kafka.orderfulfilment;

import seborama.demo1.kafka.KafkaOrderConsumer;

import java.io.IOException;

public class OrderFulfilmentServer {

    public static void main(String[] args) throws IOException {
        try (KafkaOrderConsumer consumer = OrderCreationConsumer.create(1000)) {
            consumer.consumerLoop();
        }
    }
}
