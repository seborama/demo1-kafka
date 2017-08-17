package seborama.demo1.kafka.ordercompletion;

import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import seborama.demo1.kafka.ConsoleArguments;
import seborama.demo1.kafka.KafkaOrderConsumer;

import java.io.IOException;
import java.util.Map;

import static seborama.demo1.kafka.ConsoleArguments.NUMBER_OF_MESSAGES;
import static seborama.demo1.kafka.ConsoleArguments.SLEEP_DURATION;

public class OrderCompletionServer {

    public static void main(String[] args) throws IOException {
        ConsoleArguments consoleArguments = new ConsoleArguments(args);

        startServer(consoleArguments.getAsInteger(SLEEP_DURATION).orElse(1000),
                consoleArguments.getAsInteger(NUMBER_OF_MESSAGES).orElse(100));
    }

    public static Map<MetricName, ? extends Metric> startServer(int sleepDuration, int numberOfMessages) throws IOException {
        try (KafkaOrderConsumer consumer = OrderDispatchConsumer.create(sleepDuration)) {
            System.out.println("Order completion server running...");
            Map<MetricName, ? extends Metric> metrics = consumer.consumerLoop(numberOfMessages);
            System.out.println("Order completion server finished");
            return metrics;
        }
    }
}
