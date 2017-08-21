package seborama.demo1.kafka.orderfulfilment;

import seborama.demo1.kafka.KafkaOrderConsumer;
import seborama.demo1.kafka.MessageArrivedListener;
import seborama.demo1.kafka.ordercreation.OrderCreationProducer;

public class OrderCreationConsumer {

    // TODO: Try setting `group_id=None` in your consumer, or call consumer.close() before ending script, or use assign() not subscribe (). Otherwise you are rejoining an existing group that has known but unresponsive members. The group coordinator will wait until those members checkin/leave/timeout. Since the consumers no longer exist (it's your prior script runs) they have to timeout. And consumer.poll() blocks during group rebalance.
    public final static String NO_CONSUMER_GROUP = null;

    static KafkaOrderConsumer create(String groupName, int sleepDuration) {
        MessageArrivedListener listener = new PushToOrderFulfilmentMessageArrivedListener(sleepDuration);

        return KafkaOrderConsumer.create(
                OrderCreationProducer.TOPIC_NAME,
                groupName,
                sleepDuration,
                listener);
    }

}
