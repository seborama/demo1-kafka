package seborama.demo1.kafka.ordercreation;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Before;
import org.junit.Test;

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
    public void sendMessages() throws Exception {
        int numberOfMessages = 7;
        try (OrderCreationProducer unit = new OrderCreationProducer(mockProducer, numberOfMessages, 0)) {
            unit.sendMessages();
            assertThat(mockProducer.history().size()).isEqualTo(numberOfMessages);
        }
    }

}
