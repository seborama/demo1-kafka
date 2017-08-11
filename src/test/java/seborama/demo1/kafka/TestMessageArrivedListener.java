package seborama.demo1.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public class TestMessageArrivedListener implements MessageArrivedListener, Closeable {
    private final KafkaOrderProducer producer;
    private MockProducer<String, String> mockProducer;

    int getNumRecords() {
        return mockProducer.history().size();
    }

    List<ProducerRecord<String, String>> getRecords() {
        return mockProducer.history();
    }

    TestMessageArrivedListener(int sleepDuration) {
        mockProducer = new MockProducer<>(
                true,
                new StringSerializer(),
                new StringSerializer()
        );

        this.producer = new KafkaOrderProducer("aTopicName", sleepDuration, mockProducer);
    }

    @Override
    public void onMessageArrived(final ConsumerRecord<String, String> record) {
        producer.sendMessage(record.key(), record.value());
    }

    @Override
    public void close() throws IOException {
        producer.close();
    }
}
