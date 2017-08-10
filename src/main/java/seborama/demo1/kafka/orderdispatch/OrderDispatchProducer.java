package seborama.demo1.kafka.orderdispatch;

import seborama.demo1.kafka.KafkaOrderProducer;

public class OrderDispatchProducer {

    public static final String TOPIC_NAME = "OrderDispatchTopic";

    static KafkaOrderProducer create(int sleepDuration) {
        return KafkaOrderProducer.create(TOPIC_NAME, sleepDuration);
    }
}
