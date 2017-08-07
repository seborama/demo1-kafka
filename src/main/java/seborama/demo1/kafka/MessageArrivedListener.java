package seborama.demo1.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface MessageArrivedListener {
    void onMessageArrived(ConsumerRecord<String, String> record);
}
