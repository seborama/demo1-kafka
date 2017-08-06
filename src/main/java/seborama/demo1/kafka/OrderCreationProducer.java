package seborama.demo1.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

import static org.apache.kafka.common.utils.Utils.sleep;

public class OrderCreationProducer {

    public static final String ORDER_CREATION_TOPIC = "OrderCreationTopic";

    public static void main(String[] args) {
        Properties props = configure();
        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            sendMessages(producer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendMessages(Producer<String, String> producer) {
        for (int i = 0; i < 100; i++) {
            String msg = "Message " + i;
            producer.send(new ProducerRecord<>(ORDER_CREATION_TOPIC, msg));
            System.out.println("Sent:" + msg);
            sleep(1000);
        }
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
}
