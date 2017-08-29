package seborama.demo2.kafka.topology;

import org.apache.commons.cli.ParseException;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seborama.demo2.kafka.interfaces.cli.CommandLineParser;

import java.io.IOException;
import java.util.Properties;

import static seborama.demo2.kafka.topology.OrderLifeCycle.buildTopology;

public class OrderLifeCycleStream {

    private final static Logger log = LoggerFactory.getLogger(OrderLifeCycleStream.class);

    public static void main(String[] args) throws IOException, ParseException {
        CommandLineParser cli = new CommandLineParser(args);
        if (cli.isHelpRequest()) {
            cli.showUsage();
            System.exit(0);
        }

        StreamsConfig streamsConfig = new StreamsConfig(getProperties());
        KafkaStreams invitationStream = new KafkaStreams(
                buildTopology(OrderLifeCycle.ORDER_CREATION_STREAMS_TOPIC), streamsConfig);
        invitationStream.start();
        log.info("Order stream is running");
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.CLIENT_ID_CONFIG, "order-lifecycle-demo-stream");
        props.put("group.id", "order-lifecycle-demo-stream-group");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "order-lifecycle-demo-stream-v1");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "localhost:2181");
        props.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);
        props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
        return props;
    }
}
