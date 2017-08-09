package seborama.demo1.kafka.orderfulfilment;

import java.io.IOException;

public class OrderFulfilmentServer {

    public static void main(String[] args) throws IOException {
        try (OrderCreationConsumer consumer= new OrderCreationConsumer(1000)) {
            consumer.consumerLoop();
        }
    }
}
