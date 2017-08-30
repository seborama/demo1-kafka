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
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import static org.apache.kafka.common.utils.Utils.sleep;

public class KafkaOrderConsumer implements Closeable {

    private static Long AUTO_COMMIT_INTERVAL_MS = 1000L;
    private static String groupName;
    private final ReadWriteLock lock;
    private final Consumer<String, String> consumer;
    private final MessageArrivedListener listener;
    private final int sleepDuration;
    private boolean terminationFlag;

    public static KafkaOrderConsumer create(final String topicName,
                                            final String groupName,
                                            final int sleepDuration,
                                            final MessageArrivedListener listener) {
        KafkaOrderConsumer.groupName = groupName;
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
        lock = new ReentrantReadWriteLock();

        subscribeToTopic(topicName);
    }

    public Map<MetricName, ? extends Metric> consumerLoop(int numberOfMessages) {
        int i = 0;
        for (; i < numberOfMessages && !terminationFlag; ) {
            long pollStartTime = System.currentTimeMillis();
            ConsumerRecords<String, String> records = pollConsumerForRecords();
            if (System.currentTimeMillis() - pollStartTime > 300_000L) {
                System.out.println("Polling timeout: topic has been idled for longer than set-tolerance");
                break;
            }

            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Partition: " + record.partition() + " Offset: " + record.offset()
                        + " Value: " + record.value() + " ThreadID: " + Thread.currentThread().getId());
                listener.onMessageArrived(record);
                // commitSyncConsumer(record);

                i++;
            }

            sleep(sleepDuration);
        }

        commitSyncConsumer();
        System.out.printf("Processed: %d message(s)\n", i);
        return getMetrics();
    }

    private Map<MetricName, ? extends Metric> getMetrics() {
        lock.readLock().lock();
        try {
            return consumer.metrics();
        } finally {
            lock.readLock().unlock();
        }
    }

    private void commitSyncConsumer() {
        lock.writeLock().lock();
        try {
            consumer.commitSync();
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void commitSyncConsumer(ConsumerRecord<String, String> record) {
        // NOTE: committing after every message is NOT a good strategy for performance but useful for this demo.
        // NOTE: you should set auto-commit to false
        lock.writeLock().lock();
        try {
            consumer.commitSync(Collections.singletonMap(
                    new TopicPartition(record.topic(), record.partition()),
                    new OffsetAndMetadata(record.offset() + 1)));
        } finally {
            lock.writeLock().unlock();
        }
    }

    private ConsumerRecords<String, String> pollConsumerForRecords() {
        lock.writeLock().lock();
        try {
            return consumer.poll(100L);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void subscribeToTopic(final String topicName) {
        System.out.printf("Subscribing to topic name: %s\n", topicName);
        lock.writeLock().lock();
        try {
            consumer.subscribe(Collections.singletonList(topicName));
        } finally {
            lock.writeLock().unlock();
            System.out.printf("Subscribed to topic name: %s\n", topicName);
        }
    }

    private static Properties configure(final String groupName) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("group.id", groupName);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000"); // NOTE: use large value for purpose of demo to show auto-commit feature behaviour (when set to true)
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "10000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    @Override
    public void close() throws IOException {
        String topicName = getTopicName();
        System.out.printf("Closing consumer - TopicAdmin name: %s\n", topicName);

        lock.writeLock().lock();
        try {
            terminationFlag = true;
            consumer.close();
        } finally {
            lock.writeLock().unlock();
            System.out.printf("Closed consumer - TopicAdmin name: %s\n", topicName);
        }
    }

    private String getTopicName() {
        lock.readLock().lock();
        try {
            return consumer.assignment().stream()
                    .map(TopicPartition::topic)
                    .collect(Collectors.joining(", "));
        } finally {
            lock.readLock().unlock();
        }
    }

    public void stop() {
        if (terminationFlag) {
            System.out.println("Request to stop already terminated consumer ignored");
        } else {
            String topicName = getTopicName();
            System.out.printf("Setting termination flag for consumer - TopicAdmin name: %s\n", topicName);
            terminationFlag = true;
            System.out.printf("Termination flag for consumer has been set - TopicAdmin name: %s\n", topicName);
        }
    }
}
