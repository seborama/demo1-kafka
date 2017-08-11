package seborama.demo1.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class KafkaOrderConsumerTest {

    private MockConsumer<String, String> mockConsumer;

    @Before
    public void setUp() throws Exception {
        mockConsumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
    }

    @Test
    public void consumerLoop() throws Exception {
        final String topicName = "aTopicName";
        final int partitionNumber = 0;
        final long offset = 0L;

        MessageArrivedListener listener = new TestMessageArrivedListener(0);
        try (KafkaOrderConsumer unit = getKafkaOrderConsumer(topicName, mockConsumer, listener)) {
            configureKafkaOrderConsumer(topicName, partitionNumber);

            Thread one = new Thread(unit::consumerLoop);
            try {
                one.start();
            } catch (Exception ignored) {
            }
            while (!one.isAlive()) ;

            ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>(topicName, partitionNumber, offset, "mykey", "myvalue0");
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, "mykey", "myvalue0");
            mockConsumer.addRecord(consumerRecord);
            one.join(500);

            assertThat(((TestMessageArrivedListener) listener).getNumRecords()).isEqualTo(1);
            assertThat(((TestMessageArrivedListener) listener).getRecords().contains(producerRecord)).isTrue();
        }
    }

    private void configureKafkaOrderConsumer(String topicName, int partitionNumber) {
        mockConsumer.unsubscribe();
        mockConsumer.assign(Arrays.asList(new TopicPartition(topicName, partitionNumber)));
        HashMap<TopicPartition, Long> beginningOffsets = new HashMap<>();
        beginningOffsets.put(new TopicPartition(topicName, partitionNumber), 0L);
        mockConsumer.updateBeginningOffsets(beginningOffsets);
    }

    private static KafkaOrderConsumer getKafkaOrderConsumer(final String topicName,
                                                            final MockConsumer<String, String> mockConsumer,
                                                            MessageArrivedListener listener) {
        return new KafkaOrderConsumer(mockConsumer, topicName, 0, listener);
    }

}
