package seborama.demo1.kafka.ordercreation;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;

import static org.apache.kafka.common.utils.Utils.sleep;

public class OrderCreationProducer implements Closeable {

    public static final String ORDER_CREATION_TOPIC = "OrderCreationTopic";
    private final Producer<String, String> producer;
    private final long sleepDuration;

    public static OrderCreationProducer create() {
        Properties props = configure();
        Producer<String, String> producer = new KafkaProducer<>(props);
        return new OrderCreationProducer(producer, 1000L);
    }

    OrderCreationProducer(Producer<String, String> producer, long sleepDuration) {
        this.producer = producer;
        this.sleepDuration = sleepDuration;
    }

    public void sendMessages() {
        for (int i = 0; i < 100; i++) {
            String msg = "Message " + i;
            producer.send(new ProducerRecord<>(ORDER_CREATION_TOPIC, msg));
            System.out.println("Sent:" + msg);
            sleep(sleepDuration);
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

    @Override
    public void close() throws IOException {
        producer.close();
    }
}
