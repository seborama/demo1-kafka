package seborama.demo1.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

import static seborama.demo1.kafka.OrderCreationProducer.ORDER_CREATION_TOPIC;
import static org.apache.kafka.common.utils.Utils.sleep;

public class OrderCreationConsumer {

    public static final String ORDER_CREATION_GROUP_1 = "order-creation-group-1";

    public static void main(String[] args) {
        final Properties props = configure();
        final MessageArrivedListener listener = new PushToOrderFulfilmentMessageArrivedListener();

        KafkaConsumer<String, String> kafkaConsumer = joinConsumerGroup(props);
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
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
        kafkaConsumer.subscribe(Collections.singletonList(ORDER_CREATION_TOPIC));
        return kafkaConsumer;
    }

    private static Properties configure() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("group.id", ORDER_CREATION_GROUP_1);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "5000");
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "10000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }
}
