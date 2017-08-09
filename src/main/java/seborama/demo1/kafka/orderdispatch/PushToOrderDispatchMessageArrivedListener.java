package seborama.demo1.kafka.orderdispatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import seborama.demo1.kafka.KafkaProducer;
import seborama.demo1.kafka.MessageArrivedListener;

import java.io.Closeable;
import java.io.IOException;

public class PushToOrderDispatchMessageArrivedListener implements MessageArrivedListener, Closeable {
    private final KafkaProducer producer;

    PushToOrderDispatchMessageArrivedListener(int sleepDuration) {
        this.producer = OrderDispatchProducer.create(sleepDuration);
    }

    @Override
    public void onMessageArrived(final ConsumerRecord<String, String> record) {
        System.out.println("record received - ready to push to order dispatch");
        producer.sendMessage(record.key(), record.value());
    }

    @Override
    public void close() throws IOException {
        producer.close();
    }
}
