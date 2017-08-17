package seborama.demo1.kafka.orderfulfilment;

import seborama.demo1.kafka.ConsoleArguments;
import seborama.demo1.kafka.KafkaOrderConsumer;

import java.io.IOException;

import static seborama.demo1.kafka.ConsoleArguments.NUMBER_OF_MESSAGES;
import static seborama.demo1.kafka.ConsoleArguments.SLEEP_DURATION;

public class OrderFulfilmentServer {

    private static KafkaOrderConsumer consumer;

    public static void main(String[] args) throws IOException {
        ConsoleArguments consoleArguments = new ConsoleArguments(args);

        startServer(consoleArguments.getAsInteger(SLEEP_DURATION).orElse(1000),
                consoleArguments.getAsInteger(NUMBER_OF_MESSAGES).orElse(100));
    }

    public static void startServer(int sleepDuration, int numberOfMessages) {
        consumer = OrderCreationConsumer.create(sleepDuration);
        System.out.println("Order fulfilment server running...");
        consumer.consumerLoop(numberOfMessages);
        System.out.println("Order fulfilment server finished");
    }

    public static void stopServer(long timeoutMillis) throws InterruptedException {
        boolean isStopped = false;
        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() - start <= timeoutMillis) {
            if (consumer != null) {
                consumer.stop();
                isStopped = true;
                break;
            } else {
                Thread.sleep(10);
            }
        }

        if (isStopped)
            System.err.println("Order fulfilment server has been stopped");
        else
            System.err.println("Unable to stop Order fulfilment server: not initialised");
    }
}
