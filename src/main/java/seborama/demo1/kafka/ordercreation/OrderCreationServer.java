package seborama.demo1.kafka.ordercreation;

import seborama.demo1.kafka.KafkaOrderProducer;

import java.io.IOException;

public class OrderCreationServer {

    public static void main(String[] args) throws IOException {
        try (KafkaOrderProducer producer = OrderCreationProducer.create(1000)) {
            OrderCreationProducer.sendMessages(producer, 100);
        }
    }
}
