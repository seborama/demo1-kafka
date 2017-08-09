package seborama.demo1.kafka.orderdispatch;

import seborama.demo1.kafka.KafkaProducer;

class OrderDispatchProducer {

    private static final String TOPIC_NAME = "OrderDispatchTopic";

    static KafkaProducer create(int sleepDuration) {
        return KafkaProducer.create(TOPIC_NAME, sleepDuration);
    }
}
