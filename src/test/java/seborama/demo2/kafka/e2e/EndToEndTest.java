package seborama.demo2.kafka.e2e;

import org.junit.BeforeClass;
import org.junit.Test;
import seborama.demo1.kafka.admin.TopicAdmin;

import static seborama.demo2.kafka.topology.OrderLifeCycle.ORDER_COMPLETION_STREAMS_TOPIC;
import static seborama.demo2.kafka.topology.OrderLifeCycle.ORDER_CREATION_STREAMS_TOPIC;

public class EndToEndTest {

    @BeforeClass
    public static void setUpOnce() throws Exception {
        TopicAdmin topicAdmin = new TopicAdmin();
        topicAdmin.createTopic(ORDER_CREATION_STREAMS_TOPIC);
        topicAdmin.createTopic(ORDER_COMPLETION_STREAMS_TOPIC);
    }

    @Test
    public void noTest() throws Exception {
    }
}
