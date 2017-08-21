package seborama.demo1.kafka.orderdispatch;

import seborama.demo1.kafka.KafkaOrderConsumer;
import seborama.demo1.kafka.MessageArrivedListener;
import seborama.demo1.kafka.orderfulfilment.OrderFulfilmentProducer;

class OrderFulfilmentConsumer {

    static KafkaOrderConsumer create(String groupName, int sleepDuration) {
        MessageArrivedListener listener = new PushToOrderDispatchMessageArrivedListener(sleepDuration);

        return KafkaOrderConsumer.create(
                OrderFulfilmentProducer.TOPIC_NAME,
                groupName,
                sleepDuration,
                listener);
    }

}
