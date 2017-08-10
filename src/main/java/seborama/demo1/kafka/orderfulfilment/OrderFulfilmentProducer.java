package seborama.demo1.kafka.orderfulfilment;

import seborama.demo1.kafka.KafkaOrderProducer;

public class OrderFulfilmentProducer {

    public static final String TOPIC_NAME = "OrderFulfilmentTopic";

    static KafkaOrderProducer create(int sleepDuration) {
        return KafkaOrderProducer.create(TOPIC_NAME, sleepDuration);
    }
}
