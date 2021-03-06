package seborama.demo1.kafka.e2e;

import org.junit.BeforeClass;
import org.junit.Test;
import seborama.demo1.kafka.OrderServer;
import seborama.demo1.kafka.admin.TopicAdmin;
import seborama.demo1.kafka.ordercompletion.OrderCompletionServer;
import seborama.demo1.kafka.ordercreation.OrderCreationProducer;
import seborama.demo1.kafka.ordercreation.OrderCreationServer;
import seborama.demo1.kafka.orderdispatch.OrderDispatchProducer;
import seborama.demo1.kafka.orderdispatch.OrderDispatchServer;
import seborama.demo1.kafka.orderfulfilment.OrderFulfilmentProducer;
import seborama.demo1.kafka.orderfulfilment.OrderFulfilmentServer;

import static seborama.demo1.kafka.ordercompletion.OrderCompletionServer.ORDER_COMPLETION_GROUP_1;
import static seborama.demo1.kafka.orderdispatch.OrderDispatchServer.ORDER_DISPATCH_GROUP_1;
import static seborama.demo1.kafka.orderfulfilment.OrderFulfilmentServer.ORDER_FULFILMENT_GROUP_1;

public class EndToEndTest {

    public static final String E2E_TEST_GROUP_NAME_SUFFIX = "-e2e-test";

    @BeforeClass
    public static void setUpOnce() throws Exception {
        TopicAdmin topicAdmin = new TopicAdmin();
        topicAdmin.createTopic(OrderCreationProducer.TOPIC_NAME);
        topicAdmin.createTopic(OrderFulfilmentProducer.TOPIC_NAME);
        topicAdmin.createTopic(OrderDispatchProducer.TOPIC_NAME);
    }

    @Test
    public void itSendsBulkMessages() throws Exception {
        OrderCreationServer.startServer(1, 10);

        OrderServer orderFulfilmentServer = new OrderFulfilmentServer(ORDER_FULFILMENT_GROUP_1 + E2E_TEST_GROUP_NAME_SUFFIX, 1);
        orderFulfilmentServer.startServer(10);
        orderFulfilmentServer.stopServer();

        OrderServer orderDispatchServer = new OrderDispatchServer(ORDER_DISPATCH_GROUP_1 + E2E_TEST_GROUP_NAME_SUFFIX, 1);
        orderDispatchServer.startServer(10);
        orderDispatchServer.stopServer();

        OrderServer orderCompletionServer = new OrderCompletionServer(ORDER_COMPLETION_GROUP_1 + E2E_TEST_GROUP_NAME_SUFFIX, 1);
        orderCompletionServer.startServer(10);
        orderCompletionServer.stopServer();

        // TODO: other than the test completing, find a better measure of success!
    }

}
