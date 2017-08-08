package seborama.demo1.kafka.ordercompletion;

import java.io.IOException;

public class OrderCompletionServer {

    public static void main(String[] args) throws IOException {
        try (OrderDispatchConsumer consumer= new OrderDispatchConsumer()) {
            consumer.consumerLoop();
        }
    }
}
