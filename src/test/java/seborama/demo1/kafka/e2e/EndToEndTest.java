package seborama.demo1.kafka.e2e;

import org.junit.Test;
import seborama.demo1.kafka.OrderServer;
import seborama.demo1.kafka.ordercompletion.OrderCompletionServer;
import seborama.demo1.kafka.ordercreation.OrderCreationServer;
import seborama.demo1.kafka.orderdispatch.OrderDispatchServer;
import seborama.demo1.kafka.orderfulfilment.OrderFulfilmentServer;

import static seborama.demo1.kafka.ordercompletion.OrderCompletionServer.ORDER_COMPLETION_GROUP_1;
import static seborama.demo1.kafka.orderdispatch.OrderDispatchServer.ORDER_DISPATCH_GROUP_1;
import static seborama.demo1.kafka.orderfulfilment.OrderFulfilmentServer.ORDER_FULFILMENT_GROUP_1;

public class EndToEndTest {

    @Test
    public void itSendsBulkMessages() throws Exception {
        System.out.println("Starting E2E");
        OrderCreationServer.startServer(1, 10);

        OrderServer orderFulfilmentServer = new OrderFulfilmentServer(ORDER_FULFILMENT_GROUP_1 + "_e2e_test", 1);
        orderFulfilmentServer.startServer(10);
        orderFulfilmentServer.stopServer(500);

        OrderServer orderDispatchServer = new OrderDispatchServer(ORDER_DISPATCH_GROUP_1 + "_e2e_test", 1);
        orderDispatchServer.startServer(10);
        orderDispatchServer.stopServer(500);

        OrderServer orderCompletionServer = new OrderCompletionServer(ORDER_COMPLETION_GROUP_1 + "_e2e_test", 1);
        orderCompletionServer.stopServer(500);
        orderCompletionServer.startServer(10);

        // TODO: other than the test completing, find a better measure of success!
        System.out.println("Completed E2E");
    }
}
