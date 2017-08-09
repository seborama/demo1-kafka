package seborama.demo1.kafka.orderfulfilment;

import seborama.demo1.kafka.KafkaProducer;

public class OrderFulfilmentProducer {

    public static final String TOPIC_NAME = "OrderFulfilmentTopic";

    static KafkaProducer create(int sleepDuration) {
        return KafkaProducer.create(TOPIC_NAME, sleepDuration);
    }
}
