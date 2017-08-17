package seborama.demo1.kafka.e2e;

import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.junit.Test;
import seborama.demo1.kafka.OrderServer;
import seborama.demo1.kafka.ordercompletion.OrderCompletionServer;
import seborama.demo1.kafka.ordercreation.OrderCreationServer;
import seborama.demo1.kafka.orderdispatch.OrderDispatchServer;
import seborama.demo1.kafka.orderfulfilment.OrderFulfilmentServer;

import java.util.Map;

public class EndToEndTest {
    @Test
    public void itSendsBulkMessages() throws Exception {
        purgeTopics();

        System.out.println("Starting E2E");
        OrderCreationServer.startServer(1, 10);

        OrderServer orderFulfilmentServer = new OrderFulfilmentServer(1);
        orderFulfilmentServer.startServer(10);

        OrderServer orderDispatchServer = new OrderDispatchServer(1);
        orderDispatchServer.startServer(10);

        OrderServer orderCompletionServer = new OrderCompletionServer(1);
        Map<MetricName, ? extends Metric> orderCompletionMetrics = orderCompletionServer.startServer(10);

        System.out.println("orderCompletionMetrics = " + orderCompletionMetrics);

        System.out.println("Completed E2E");
    }

    private void purgeTopics() throws Exception {
        System.out.println("Purging topics");

        purgeOrderFulfilment();
        purgeOrderDispatch();
        purgeOrderCompletion();

        Thread.sleep(50);
        System.out.println("Topics purged");
    }

    private void purgeOrderFulfilment() throws InterruptedException {
        OrderServer orderServer = new OrderFulfilmentServer(1);
        purgeTopic(orderServer);
    }

    private void purgeOrderDispatch() throws InterruptedException {
        OrderServer orderServer = new OrderDispatchServer(1);
        purgeTopic(orderServer);
    }

    private void purgeOrderCompletion() throws InterruptedException {
        OrderServer orderServer = new OrderCompletionServer(1);
        purgeTopic(orderServer);
    }

    private void purgeTopic(OrderServer orderServer) throws InterruptedException {
        System.out.printf("Purging topic %s\n", orderServer.name());

        Thread orderFulfilmentPurger = new Thread(() -> {
            orderServer.startServer(10000);
        });

        orderFulfilmentPurger.start();
        while (!orderFulfilmentPurger.isAlive()) Thread.sleep(1);

        Thread.sleep(100);

        orderServer.stopServer(5000);
        orderFulfilmentPurger.join();
    }
}
