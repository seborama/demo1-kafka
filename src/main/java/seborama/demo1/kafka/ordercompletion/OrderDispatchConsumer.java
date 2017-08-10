package seborama.demo1.kafka.ordercompletion;

import seborama.demo1.kafka.KafkaOrderConsumer;
import seborama.demo1.kafka.MessageArrivedListener;
import seborama.demo1.kafka.orderdispatch.OrderDispatchProducer;

class OrderDispatchConsumer {

    private static final String GROUP_NAME = "order-dispatch-group-1";

    static KafkaOrderConsumer create(int sleepDuration) {
        MessageArrivedListener listener = new OrderCompletionMessageArrivedListener();

        return KafkaOrderConsumer.create(
                OrderDispatchProducer.TOPIC_NAME,
                GROUP_NAME,
                sleepDuration,
                listener);
    }

}
