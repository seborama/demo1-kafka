package seborama.demo1.kafka;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KafkaOrderProducerTest {

    private MockProducer<String, String> mockProducer;

    @Before
    public void setUp() throws Exception {
        mockProducer = new MockProducer<>(
                true,
                new StringSerializer(),
                new StringSerializer()
        );
    }

    @Test
    public void sendMessages() throws Exception {
        final String topicName = "aTopicName";
        try (KafkaOrderProducer unit = getKafkaProducer(topicName, mockProducer)) {
            unit.sendMessage("aKey", "aValue");
            assertThat(mockProducer.history().size()).isEqualTo(1);
            assertThat(mockProducer.history().get(0).topic()).isEqualTo(topicName);
        }
    }

    public static KafkaOrderProducer getKafkaProducer(final String topicName,
                                               final MockProducer<String, String> mockProducer) {
        return new KafkaOrderProducer(topicName, 0, mockProducer);
    }
}
