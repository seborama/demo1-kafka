package seborama.demo1.kafka.ordercreation;

import seborama.demo1.kafka.KafkaOrderProducer;

public class OrderCreationProducer {

    public static final String TOPIC_NAME = "OrderCreationTopic";

    static KafkaOrderProducer create(int sleepDuration) {
        return KafkaOrderProducer.create(TOPIC_NAME, sleepDuration);
    }

    static void sendMessages(KafkaOrderProducer producer, int numberOfMessages) {
        for (int i = 0; i < numberOfMessages; i++) {
            String msg = "Message " + i;
            producer.sendMessage(null, msg);
        }
    }

}
