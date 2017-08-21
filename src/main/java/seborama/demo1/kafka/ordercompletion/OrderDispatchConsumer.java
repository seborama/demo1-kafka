package seborama.demo1.kafka.ordercompletion;

import seborama.demo1.kafka.KafkaOrderConsumer;
import seborama.demo1.kafka.MessageArrivedListener;
import seborama.demo1.kafka.orderdispatch.OrderDispatchProducer;

class OrderDispatchConsumer {

    static KafkaOrderConsumer create(String groupName, int sleepDuration) {
        MessageArrivedListener listener = new OrderCompletionMessageArrivedListener();

        return KafkaOrderConsumer.create(
                OrderDispatchProducer.TOPIC_NAME,
                groupName,
                sleepDuration,
                listener);
    }

}
