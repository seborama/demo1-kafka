package seborama.demo1.kafka.ordercreation;

import seborama.demo1.kafka.KafkaProducer;

import java.io.IOException;

public class OrderCreationServer {

    public static void main(String[] args) throws IOException {
        try (KafkaProducer producer = OrderCreationProducer.create(1000)) {
            OrderCreationProducer.sendMessages(producer, 100);
        }
    }
}
