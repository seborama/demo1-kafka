package seborama.demo1.kafka;

import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;

import java.util.Map;

public abstract class OrderServer {
    private final String serverName;
    private final KafkaOrderConsumer consumer;
    private boolean isActive;

    protected OrderServer(String serverName, KafkaOrderConsumer consumer) {
        isActive = true;
        this.serverName = serverName;
        this.consumer = consumer;
    }

    public Map<MetricName, ? extends Metric> startServer(int numberOfMessages) {
        System.out.printf("%s server running...\n", this.serverName);
        Map<MetricName, ? extends Metric> metrics = this.consumer.consumerLoop(numberOfMessages);
        System.out.printf("%s server finished\n", this.serverName);
        return metrics;
    }

    public void stopServer() throws InterruptedException {
        if (!isServerRunning()) {
            System.err.printf("Ignoring request to close %s server when it is not running\n", serverName);
            return;
        }

        this.consumer.stop();
        isActive = false;
        System.err.printf("%s server has been stopped\n", serverName);
    }

    private boolean isServerRunning() {
        return isActive && this.consumer != null;
    }

    public String name() {
        return this.serverName;
    }
}
