package seborama.demo1.kafka.ordercompletion;

import seborama.demo1.kafka.ConsoleArguments;
import seborama.demo1.kafka.OrderServer;

import java.io.IOException;

import static seborama.demo1.kafka.ConsoleArguments.NUMBER_OF_MESSAGES;
import static seborama.demo1.kafka.ConsoleArguments.SLEEP_DURATION;

public class OrderCompletionServer extends OrderServer {

    public static final String ORDER_COMPLETION_GROUP_1 = "order-completion-group-1";

    public static void main(String[] args) throws IOException {
        ConsoleArguments consoleArguments = new ConsoleArguments(args);

        OrderServer server = new OrderCompletionServer(ORDER_COMPLETION_GROUP_1,
                consoleArguments
                        .getAsInteger(SLEEP_DURATION)
                        .orElse(1000));

        server.startServer(consoleArguments
                .getAsInteger(NUMBER_OF_MESSAGES)
                .orElse(100));
    }

    public OrderCompletionServer(String groupName, int sleepDuration) {
        super("Order Completion", OrderDispatchConsumer.create(groupName, sleepDuration));
    }
}
