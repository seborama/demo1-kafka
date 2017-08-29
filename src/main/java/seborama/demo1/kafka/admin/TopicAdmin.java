package seborama.demo1.kafka.admin;

import kafka.admin.TopicCommand;
import kafka.admin.TopicCommand.TopicCommandOptions;
import kafka.utils.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.apache.kafka.common.errors.TopicExistsException;
import scala.Tuple2;

import java.io.Closeable;
import java.io.IOException;

public class TopicAdmin implements Closeable {
    private Tuple2<ZkClient, ZkConnection> zkTuple;
    private ZkUtils zkUtils;

    public TopicAdmin() {
        final int ZK_CONNECTION_TIMEOUT = 6000;
        final int ZK_SESSION_TIMEOUT = 6000;
        zkTuple = ZkUtils.createZkClientAndConnection("127.0.0.1:2181", ZK_SESSION_TIMEOUT, ZK_CONNECTION_TIMEOUT);
        zkUtils = new ZkUtils(zkTuple._1, zkTuple._2, false);
    }

    public void createTopic(String topicName) {
        String[] arguments = new String[]{
                "--replication-factor",
                "1",
                "--partitions",
                "5",
                "--topic",
                topicName,
                "--config",
                "retention.ms=86400000"
        };
        TopicCommandOptions topicCommandOptions = new TopicCommandOptions(arguments);

        try {
            kafka.admin.TopicCommand.createTopic(zkUtils, topicCommandOptions);
        } catch (TopicExistsException ignored) {
        }
    }

    public void deleteTopic(String topicName) {
        // NOTE: this will only work if the Kafka server has been configured to allow topic deletion!
        String[] arguments = new String[]{
                "--topic",
                topicName
        };
        TopicCommandOptions topicCommandOptions = new TopicCommandOptions(arguments);

        kafka.admin.TopicCommand.deleteTopic(zkUtils, topicCommandOptions);
    }

    void listTopics() {
        String[] arguments = new String[]{};
        TopicCommandOptions topicCommandOptions = new TopicCommandOptions(arguments);

        TopicCommand.listTopics(zkUtils, topicCommandOptions);
    }

    @Override
    public void close() throws IOException {
        zkUtils.close();
    }
}
