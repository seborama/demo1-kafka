package seborama.demo1.kafka.ordercreation;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

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
        try (OrderCreationProducer orderCreationProducer = new OrderCreationProducer(mockProducer, 0L)) {
            orderCreationProducer.sendMessages();
            assertThat(mockProducer.history().size()).isEqualTo(100);
        }
    }

}
