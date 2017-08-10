package seborama.demo1.kafka.orderdispatch;

import seborama.demo1.kafka.KafkaOrderConsumer;
import seborama.demo1.kafka.MessageArrivedListener;
import seborama.demo1.kafka.orderfulfilment.OrderFulfilmentProducer;

class OrderFulfilmentConsumer {

    private static final String GROUP_NAME = "order-fulfilment-group-1";

    static KafkaOrderConsumer create(int sleepDuration) {
        MessageArrivedListener listener = new PushToOrderDispatchMessageArrivedListener(sleepDuration);

        return KafkaOrderConsumer.create(
                OrderFulfilmentProducer.TOPIC_NAME,
                GROUP_NAME,
                sleepDuration,
                listener);
    }

}
