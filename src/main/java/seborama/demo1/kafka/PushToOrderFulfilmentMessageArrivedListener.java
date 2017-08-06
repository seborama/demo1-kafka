package seborama.demo1.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class PushToOrderFulfilmentMessageArrivedListener implements MessageArrivedListener {
    @Override
    public void onMessageArrived(ConsumerRecord<String, String> record) {
        System.out.println("record received - ready to push to order fulfilment");
    }
}
