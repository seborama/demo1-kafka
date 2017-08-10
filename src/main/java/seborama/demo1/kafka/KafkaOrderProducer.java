package seborama.demo1.kafka;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;

import static org.apache.kafka.common.utils.Utils.sleep;

public class KafkaOrderProducer implements Closeable {

    private final String topicName;
    private final Producer<String, String> producer;
    private final int sleepDuration;

    public static KafkaOrderProducer create(final String topicName, int sleepDuration) {
        Properties props = configure();
        Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);
        return new KafkaOrderProducer(topicName, producer, sleepDuration);
    }

    KafkaOrderProducer(final String topicName,
                       final Producer<String, String> producer,
                       final int sleepDuration) {
        this.topicName = topicName;
        this.producer = producer;
        this.sleepDuration = sleepDuration;
    }

    public void sendMessage(final String key, final String value) {
        producer.send(new ProducerRecord<>(topicName, key, value));
        System.out.println("Sent:" + value);
        sleep(sleepDuration);
    }

    private static Properties configure() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    @Override
    public void close() throws IOException {
        producer.close();
    }
}