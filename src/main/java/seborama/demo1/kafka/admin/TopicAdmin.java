package seborama.demo1.kafka.admin;

import kafka.admin.TopicCommand;
import kafka.admin.TopicCommand.TopicCommandOptions;
import kafka.utils.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import scala.Tuple2;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;

public class TopicAdmin implements Closeable {
    private Tuple2<ZkClient, ZkConnection> zkTuple;
    private ZkUtils zkUtils;

    TopicAdmin() {
        final int ZK_CONNECTION_TIMEOUT = 6000;
        final int ZK_SESSION_TIMEOUT = 6000;
        zkTuple = ZkUtils.createZkClientAndConnection("127.0.0.1:2181", ZK_SESSION_TIMEOUT, ZK_CONNECTION_TIMEOUT);
        zkUtils = new ZkUtils(zkTuple._1, zkTuple._2, false);
    }

    boolean createTopic(String topicName) {
//        if (topicExists(topicName)) {
//            System.out.printf("TopicAdmin %s already exists\n", topicName);
//            return true;
//        }

        String[] arguments = new String[]{
                "--replication-factor",
                "1",
                "--partitions",
                "5",
                "--topic",
                topicName
        };
        TopicCommandOptions topicCommandOptions = new TopicCommandOptions(arguments);

        kafka.admin.TopicCommand.createTopic(zkUtils, topicCommandOptions);
        if (!topicExists(topicName)) {
            System.out.printf("TopicAdmin %s creation failed\n", topicName);
            return false;
        }

        return true;
    }

    boolean topicExists(String topicName) {
        String output;
        String[] arguments = new String[]{};
        TopicCommandOptions topicCommandOptions = new TopicCommandOptions(arguments);
        PrintStream old = null;

        try {
//            SystemOutRedirector.invoke();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            // IMPORTANT: Save the old System.out!
            old = System.out;
            // Tell Java to use your special stream
            System.setOut(ps);
            TopicCommand.listTopics(zkUtils, topicCommandOptions);
        } finally {
            // Put things back
            System.out.flush();
            if (old != null) System.setOut(old);
            output = SystemOutRedirector.revoke();
        }

        System.out.println("output = " + output);
        return output.contains(topicName);
    }

    @Override
    public void close() throws IOException {
        zkUtils.close();
    }
}
