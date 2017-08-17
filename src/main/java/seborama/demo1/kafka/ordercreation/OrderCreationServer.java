package seborama.demo1.kafka.ordercreation;

import seborama.demo1.kafka.ConsoleArguments;
import seborama.demo1.kafka.KafkaOrderProducer;

import java.io.IOException;

import static seborama.demo1.kafka.ConsoleArguments.NUMBER_OF_MESSAGES;
import static seborama.demo1.kafka.ConsoleArguments.SLEEP_DURATION;

public class OrderCreationServer {

    public static void main(String[] args) throws IOException {
        ConsoleArguments consoleArguments = new ConsoleArguments(args);

        startServer(consoleArguments.getAsInteger(SLEEP_DURATION).orElse(1000),
                consoleArguments.getAsInteger(NUMBER_OF_MESSAGES).orElse(100));
    }

    public static void startServer(int sleepDuration, int numberOfMessages) throws IOException {
        try (KafkaOrderProducer producer = OrderCreationProducer.create(sleepDuration)) {
            System.out.println("Order creation server running...");
            OrderCreationProducer.sendMessages(producer, numberOfMessages);
            System.out.println("Order creation server finished");
        }
    }
}
