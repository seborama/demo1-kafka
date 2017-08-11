package seborama.demo1.kafka.orderdispatch;

import seborama.demo1.kafka.KafkaOrderConsumer;

import java.io.IOException;

public class OrderDispatchServer {

    public static void main(String[] args) throws IOException {
        try (KafkaOrderConsumer consumer = OrderFulfilmentConsumer.create(1000)) {
            System.out.println("Order dispatch server running...");
            consumer.consumerLoop();
        }
    }
}
