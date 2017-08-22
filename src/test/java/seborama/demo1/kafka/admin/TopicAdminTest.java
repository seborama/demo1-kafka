package seborama.demo1.kafka.admin;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TopicAdminTest {

    @Test
    public void itCreatesATopic() throws Exception {
        TopicAdmin unit = new TopicAdmin();
        final String topicName = "test-topic-" + System.currentTimeMillis();
//        assertThat(unit.topicExists(topicName)).isFalse();
        assertThat(unit.createTopic(topicName)).isTrue();
        //unit.deleteTopic(topicName); // tear down
    }
}
