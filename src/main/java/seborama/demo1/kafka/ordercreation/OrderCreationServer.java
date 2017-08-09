package seborama.demo1.kafka.ordercreation;

import java.io.IOException;

public class OrderCreationServer {

    public static void main(String[] args) throws IOException {
        try (OrderCreationProducer producer = OrderCreationProducer.create()) {
            producer.sendMessages();
        }
    }
}
