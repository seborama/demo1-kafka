package seborama.demo1.kafka.orderfulfilment;

import seborama.demo1.kafka.KafkaOrderConsumer;
import seborama.demo1.kafka.MessageArrivedListener;
import seborama.demo1.kafka.ordercreation.OrderCreationProducer;

class OrderCreationConsumer {

    private static final String GROUP_NAME = "order-creation-group-1";

    static KafkaOrderConsumer create(int sleepDuration) {
        MessageArrivedListener listener = new PushToOrderFulfilmentMessageArrivedListener(sleepDuration);

        return KafkaOrderConsumer.create(
                OrderCreationProducer.TOPIC_NAME,
                GROUP_NAME,
                sleepDuration,
                listener);
    }

}
