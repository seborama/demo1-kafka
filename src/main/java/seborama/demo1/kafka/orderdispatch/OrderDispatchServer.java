package seborama.demo1.kafka.orderdispatch;

import java.io.IOException;

public class OrderDispatchServer {

    public static void main(String[] args) throws IOException {
        try (OrderFulfilmentConsumer consumer = new OrderFulfilmentConsumer(1000)) {
            consumer.consumerLoop();
        }
    }
}
