package seborama.demo1.kafka;

import java.io.IOException;

public class OrderFulfilmentServer {

    public static void main(String[] args) throws IOException {
        try (OrderCreationConsumer consumer= new OrderCreationConsumer()) {
            consumer.consumerLoop();
        }
    }
}
