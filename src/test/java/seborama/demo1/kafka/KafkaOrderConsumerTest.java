package seborama.demo1.kafka;

import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class KafkaOrderConsumerTest {

    private MockConsumer<String, String> mockConsumer;

    @Before
    public void setUp() throws Exception {
        mockConsumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
    }

    @Test
    public void consumerLoop() throws Exception {
        final String topicName = "aTopicName";
        try (KafkaOrderConsumer unit = getKafkaConsumer(topicName, mockConsumer)) {
            unit.consumerLoop();
            assertThat(true).isFalse();
//            assertThat(mockConsumer.history().size()).isEqualTo(1);
//            assertThat(mockConsumer.history().get(0).topic()).isEqualTo(topicName);
        }
    }

    private static KafkaOrderConsumer getKafkaConsumer(final String topicName,
                                                       final MockConsumer<String, String> mockConsumer) {
        MessageArrivedListener listener = mock(MessageArrivedListener.class);
        return new KafkaOrderConsumer(mockConsumer, topicName, 0, listener);
    }

}
