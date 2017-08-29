package seborama.demo1.kafka.orderdispatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import seborama.demo1.kafka.KafkaOrderProducer;
import seborama.demo1.kafka.MessageArrivedListener;

import java.io.Closeable;
import java.io.IOException;

public class PushToOrderDispatchMessageArrivedListener implements MessageArrivedListener, Closeable {
    private final KafkaOrderProducer producer;

    PushToOrderDispatchMessageArrivedListener(int sleepDuration) {
        this.producer = OrderDispatchProducer.create(sleepDuration);
    }

    @Override
    public void onMessageArrived(final ConsumerRecord<String, String> record) {
        String msg = String.format("%s - Dispatch:1", record.value());
        producer.sendMessage(record.key(), msg);
    }

    @Override
    public void close() throws IOException {
        producer.close();
    }
}
