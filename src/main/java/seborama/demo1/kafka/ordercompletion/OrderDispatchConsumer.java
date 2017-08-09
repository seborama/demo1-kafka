package seborama.demo1.kafka.ordercompletion;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import seborama.demo1.kafka.MessageArrivedListener;
import seborama.demo1.kafka.orderfulfilment.OrderFulfilmentProducer;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

import static org.apache.kafka.common.utils.Utils.sleep;

public class OrderDispatchConsumer implements Closeable {

    private static final String ORDER_DISPATCH_GROUP_1 = "order-dispatch-group-1";
    private KafkaConsumer<String, String> consumer;
    private MessageArrivedListener listener;

    OrderDispatchConsumer() {
        final Properties props = configure();
        listener = new OrderCompletionMessageArrivedListener();

        consumer = joinConsumerGroup(props);
    }

    void consumerLoop() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Partition: " + record.partition() + " Offset: " + record.offset()
                        + " Value: " + record.value() + " ThreadID: " + Thread.currentThread().getId());
                listener.onMessageArrived(record);
            }
            sleep(1000);
        }
    }

    private static KafkaConsumer<String, String> joinConsumerGroup(Properties props) {
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Collections.singletonList(OrderFulfilmentProducer.TOPIC_NAME));
        return kafkaConsumer;
    }

    private static Properties configure() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("group.id", ORDER_DISPATCH_GROUP_1);
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
