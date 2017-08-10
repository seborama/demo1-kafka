package seborama.demo1.kafka.ordercreation;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Before;
import org.junit.Test;
import seborama.demo1.kafka.KafkaOrderProducer;
import seborama.demo1.kafka.KafkaOrderProducerTest;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderCreationProducerTest {
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
    public void itSendsBulkMessages() throws Exception {
        final String topicName = "aTopicName";
        final int numberOfMessages = 7;
        try (KafkaOrderProducer unit = KafkaOrderProducerTest.getKafkaProducer(topicName, mockProducer)) {
            OrderCreationProducer.sendMessages(unit, numberOfMessages);
            assertThat(mockProducer.history().size()).isEqualTo(numberOfMessages);
            assertThat(mockProducer.history().get(0).topic()).isEqualTo(topicName);
        }
    }

}
