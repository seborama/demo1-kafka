package seborama.demo1.kafka.ordercreation;

import seborama.demo1.kafka.KafkaOrderProducer;

import java.util.stream.IntStream;

public class OrderCreationProducer {

    public static final String TOPIC_NAME = "OrderCreationTopic";

    static KafkaOrderProducer create(int sleepDuration) {
        return KafkaOrderProducer.create(TOPIC_NAME, sleepDuration);
    }

    static void sendMessages(KafkaOrderProducer producer, int numberOfMessages) {
        IntStream.range(1, numberOfMessages+1).parallel().forEachOrdered(i -> {
            String msg = String.format("Message %d - Creation:1", i);
            producer.sendMessage(String.format("%d", i), msg);
        });
    }

}
