package seborama.demo1.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;

public class OrderFulfilmentProducer implements Closeable {

    private static final String ORDER_CREATION_TOPIC = "OrderFulfilmentTopic";
    private final Producer<String, String> producer;

    OrderFulfilmentProducer() {
        Properties props = configure();
        producer = new KafkaProducer<>(props);
    }

    void sendMessage(String key, String value) {
        producer.send(new ProducerRecord<>(ORDER_CREATION_TOPIC, key, value));
        System.out.println("Sent:" + value);
//        sleep(1000);
    }

    private Properties configure() {
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
