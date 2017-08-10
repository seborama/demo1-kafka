package seborama.demo1.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

import static org.apache.kafka.common.utils.Utils.sleep;

public class KafkaOrderConsumer implements Closeable {

    private final Consumer<String, String> consumer;
    private final MessageArrivedListener listener;
    private final int sleepDuration;

    public static KafkaOrderConsumer create(final String topicName,
                                            final String groupName,
                                            final int sleepDuration,
                                            final MessageArrivedListener listener) {
        final Properties props = configure(groupName);
        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        return new KafkaOrderConsumer(consumer, topicName, sleepDuration, listener);
    }

    KafkaOrderConsumer(final Consumer<String, String> consumer,
                       final String topicName,
                       final int sleepDuration,
                       final MessageArrivedListener listener) {
        this.consumer = consumer;
        this.sleepDuration = sleepDuration;
        this.listener = listener;

        subscribeToTopic(topicName);
    }

    public void consumerLoop() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Partition: " + record.partition() + " Offset: " + record.offset()
                        + " Value: " + record.value() + " ThreadID: " + Thread.currentThread().getId());
                listener.onMessageArrived(record);
            }
            sleep(sleepDuration);
        }
    }

    private void subscribeToTopic(final String topicName) {
        consumer.subscribe(Collections.singletonList(topicName));
    }

    private static Properties configure(final String groupName) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("group.id", groupName);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "5000");
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "10000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    @Override
    public void close() throws IOException {
        consumer.close();
    }
}
