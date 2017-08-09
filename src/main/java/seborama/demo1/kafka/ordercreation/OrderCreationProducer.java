package seborama.demo1.kafka.ordercreation;

import seborama.demo1.kafka.KafkaProducer;

public class OrderCreationProducer {

    public static final String TOPIC_NAME = "OrderCreationTopic";

    static KafkaProducer create(int sleepDuration) {
        return KafkaProducer.create(TOPIC_NAME, sleepDuration);
    }

    static void sendMessages(KafkaProducer producer, int numberOfMessages) {
        for (int i = 0; i < numberOfMessages; i++) {
            String msg = "Message " + i;
            producer.sendMessage(null, msg);
        }
    }

}
