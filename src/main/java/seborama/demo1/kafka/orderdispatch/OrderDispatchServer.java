package seborama.demo1.kafka.orderdispatch;

import seborama.demo1.kafka.ConsoleArguments;
import seborama.demo1.kafka.OrderServer;

import java.io.IOException;

import static seborama.demo1.kafka.ConsoleArguments.NUMBER_OF_MESSAGES;
import static seborama.demo1.kafka.ConsoleArguments.SLEEP_DURATION;

public class OrderDispatchServer extends OrderServer {

    public static void main(String[] args) throws IOException {
        ConsoleArguments consoleArguments = new ConsoleArguments(args);

        OrderServer server = new OrderDispatchServer("order-fulfilment-group-1", consoleArguments
                .getAsInteger(SLEEP_DURATION)
                .orElse(1000));

        server.startServer(consoleArguments
                .getAsInteger(NUMBER_OF_MESSAGES)
                .orElse(100));
    }

    public OrderDispatchServer(String groupName, int sleepDuration) {
        super("Order Dispatch", OrderFulfilmentConsumer.create(groupName, sleepDuration));
    }
}
