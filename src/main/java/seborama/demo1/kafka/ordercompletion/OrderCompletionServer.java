package seborama.demo1.kafka.ordercompletion;

import seborama.demo1.kafka.ConsoleArguments;
import seborama.demo1.kafka.KafkaOrderConsumer;

import java.io.IOException;

public class OrderCompletionServer {

    public static void main(String[] args) throws IOException {
        ConsoleArguments consoleArguments = new ConsoleArguments(args);

        startServer(1000, 100);
    }

    private static void startServer(int sleepDuration, int numberOfMessages) throws IOException {
        try (KafkaOrderConsumer consumer = OrderDispatchConsumer.create(sleepDuration)) {
            System.out.println("Order completion server running...");
            consumer.consumerLoop(numberOfMessages);
        }
    }
}
