package seborama.demo1.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.TopicPartition;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.apache.kafka.common.utils.Utils.sleep;

public class KafkaOrderConsumer implements Closeable {

    private final Consumer<String, String> consumer;
    private final MessageArrivedListener listener;
    private final int sleepDuration;
    private boolean terminationFlag;

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
        terminationFlag = false;

        subscribeToTopic(topicName);
    }

    public Map<MetricName, ? extends Metric> consumerLoop(int numberOfMessages) {
        for (int i = 1; i <= numberOfMessages && !terminationFlag; ) {
            System.out.println("initiating poll");
            ConsumerRecords<String, String> records = pollConsumerForRecords();
            System.out.println("out of poll");
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Partition: " + record.partition() + " Offset: " + record.offset()
                        + " Value: " + record.value() + " ThreadID: " + Thread.currentThread().getId());
                listener.onMessageArrived(record);
                commitSyncConsumer(record);


                if (++i > numberOfMessages) break;
            }
            sleep(sleepDuration);
        }

        return consumer.metrics();
    }

    private synchronized void commitSyncConsumer(ConsumerRecord<String, String> record) {
        // NOTE: committing after every message is NOT a good strategy for performance but useful for this demo.
        // NOTE: you should set auto-commit to false
        consumer.commitSync(Collections.singletonMap(
                new TopicPartition(record.topic(), record.partition()),
                new OffsetAndMetadata(record.offset() + 1)));
    }

    private synchronized ConsumerRecords<String, String> pollConsumerForRecords() {
        return consumer.poll(100);
    }

    private synchronized void subscribeToTopic(final String topicName) {
        consumer.subscribe(Collections.singletonList(topicName));
    }

    private static Properties configure(final String groupName) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("group.id", groupName);
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "5000"); // NOTE: large for purpose of demo to show auto-commit feature behaviour (when set to true)
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "10000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    @Override
    public synchronized void close() throws IOException {
        System.out.printf("Closing consumer - Topic name: %s\n", consumer.assignment().stream()
                .map(TopicPartition::topic)
                .collect(Collectors.joining(", ")));
        consumer.close();
        System.out.printf("Closed consumer - Topic name: %s\n", consumer.assignment().stream()
                .map(TopicPartition::topic)
                .collect(Collectors.joining(", ")));
    }

    public synchronized void stop() {
        System.out.printf("Setting termination flag for consumer - Topic name: %s\n", consumer.assignment().stream()
                .map(TopicPartition::topic)
                .collect(Collectors.joining(", ")));
        terminationFlag = true;
    }
}
