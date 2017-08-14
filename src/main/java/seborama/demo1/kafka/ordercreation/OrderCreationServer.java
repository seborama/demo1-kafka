package seborama.demo1.kafka.ordercreation;

import seborama.demo1.kafka.KafkaOrderProducer;

import java.io.IOException;

public class OrderCreationServer {

    public static void main(String[] args) throws IOException {
        startServer(1000, 100);
    }

    private static void startServer(int sleepDuration, int numberOfMessages) throws IOException {
        try (KafkaOrderProducer producer = OrderCreationProducer.create(sleepDuration)) {
            System.out.println("Order creation server running...");
            OrderCreationProducer.sendMessages(producer, numberOfMessages);
        }
    }
}
