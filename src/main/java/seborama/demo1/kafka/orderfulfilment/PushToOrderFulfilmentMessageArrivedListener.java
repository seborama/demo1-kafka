package seborama.demo1.kafka.orderfulfilment;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import seborama.demo1.kafka.KafkaOrderProducer;
import seborama.demo1.kafka.MessageArrivedListener;

import java.io.Closeable;
import java.io.IOException;

public class PushToOrderFulfilmentMessageArrivedListener implements MessageArrivedListener, Closeable {
    private final KafkaOrderProducer producer;

    PushToOrderFulfilmentMessageArrivedListener(int sleepDuration) {
        this.producer = OrderFulfilmentProducer.create(sleepDuration);
    }

    @Override
    public void onMessageArrived(final ConsumerRecord<String, String> record) {
        System.out.println("record received - ready to push to order fulfilment");
        producer.sendMessage(record.key(), record.value());
    }

    @Override
    public void close() throws IOException {
        producer.close();
    }
}
