package seborama.demo1.kafka.ordercompletion;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import seborama.demo1.kafka.MessageArrivedListener;

public class OrderCompletionMessageArrivedListener implements MessageArrivedListener {

    @Override
    public void onMessageArrived(final ConsumerRecord<String, String> record) {
        System.out.printf("Order completed: %s - %s\n", record.key(), record.value());
    }
}
