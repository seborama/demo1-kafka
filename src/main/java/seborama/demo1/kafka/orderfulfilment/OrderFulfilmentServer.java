package seborama.demo1.kafka.orderfulfilment;

import seborama.demo1.kafka.KafkaOrderConsumer;

import java.io.IOException;

public class OrderFulfilmentServer {

    public static void main(String[] args) throws IOException {
        startServer(1000, 100);
    }

    private static void startServer(int sleepDuration, int numberOfMessages) throws IOException {
        try (KafkaOrderConsumer consumer = OrderCreationConsumer.create(sleepDuration)) {
            System.out.println("Order fulfilment server running...");
            consumer.consumerLoop(numberOfMessages);
        }
    }
}
