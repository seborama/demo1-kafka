package seborama.demo1.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.Closeable;
import java.io.IOException;

public class PushToOrderFulfilmentMessageArrivedListener implements MessageArrivedListener, Closeable {
    private final OrderFulfilmentProducer producer;

    PushToOrderFulfilmentMessageArrivedListener() {
        this.producer = new OrderFulfilmentProducer();
    }

    @Override
    public void onMessageArrived(final ConsumerRecord<String, String> record) {
        producer.sendMessage(record.key(), record.value());
        System.out.println("record received - ready to push to order fulfilment");
    }

    @Override
    public void close() throws IOException {
        producer.close();
    }
}
