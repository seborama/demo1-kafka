package seborama.demo1.kafka.orderdispatch;

import seborama.demo1.kafka.ordercompletion.OrderDispatchConsumer;

import java.io.IOException;

public class OrderDispatchServer {

    public static void main(String[] args) throws IOException {
        try (OrderFulfilmentConsumer consumer= new OrderFulfilmentConsumer()) {
            consumer.consumerLoop();
        }
    }
}
