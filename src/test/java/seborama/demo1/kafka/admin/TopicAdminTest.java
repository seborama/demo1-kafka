package seborama.demo1.kafka.admin;

import org.junit.After;
import org.junit.Test;
import seborama.SystemOutRedirector;

import static org.assertj.core.api.Assertions.assertThat;

public class TopicAdminTest {

    @After
    public void tearDown() throws Exception {
        SystemOutRedirector.revoke();
    }

    @Test
    public void itCreatesATopic() throws Exception {
        final String topicName = "test-topic-" + System.currentTimeMillis();
        TopicAdmin unit = new TopicAdmin();

        SystemOutRedirector.invoke();
        unit.createTopic(topicName);
        String output = SystemOutRedirector.revoke();

        assertThat(output).contains("Created topic \"" + topicName + "\"");
        unit.deleteTopic(topicName);
    }
}
