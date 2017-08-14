package seborama.demo1.kafka.orderdispatch;

import seborama.demo1.kafka.KafkaOrderConsumer;

import java.io.IOException;

public class OrderDispatchServer {

    public static void main(String[] args) throws IOException {
        startServer(1000, 100);
    }

    private static void startServer(int sleepDuration, int numberOfMessages) throws IOException {
        try (KafkaOrderConsumer consumer = OrderFulfilmentConsumer.create(sleepDuration)) {
            System.out.println("Order dispatch server running...");
            consumer.consumerLoop(numberOfMessages);
        }
    }
}
