package seborama.demo1.kafka.e2e;

import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.junit.Test;
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
        OrderFulfilmentServer.startServer(1, 10);
        OrderDispatchServer.startServer(1, 10);
        Map<MetricName, ? extends Metric> orderCompletionMetrics = OrderCompletionServer.startServer(1, 10);
        System.out.println("orderCompletionMetrics = " + orderCompletionMetrics);
        System.out.println("Completed E2E");
    }

    private void purgeTopics() throws Exception {
        System.out.println("Purging topics");

        Thread orderFulfilmentPurger = new Thread(() -> {
            try {
                OrderFulfilmentServer.startServer(1, 10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        orderFulfilmentPurger.start();
        while (!orderFulfilmentPurger.isAlive()) Thread.sleep(1);
        Thread.sleep(100);
        OrderFulfilmentServer.stopServer(5000);
        orderFulfilmentPurger.join();

        Thread orderDispatchPurger = new Thread(() -> {
            try {
                OrderDispatchServer.startServer(1, 10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        orderDispatchPurger.start();
        while (!orderDispatchPurger.isAlive()) Thread.sleep(1);
        orderDispatchPurger.join();

        Thread orderCompletionPurger = new Thread(() -> {
            try {
                OrderCompletionServer.startServer(1, 10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        orderCompletionPurger.start();
        while (!orderCompletionPurger.isAlive()) Thread.sleep(1);
        orderCompletionPurger.join();

        System.out.println("Topics purged");
        Thread.sleep(50);
    }
}
