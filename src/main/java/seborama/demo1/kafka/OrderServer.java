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

    public void stopServer(long timeoutMillis) throws InterruptedException {
        if (!isActive) {
            System.err.printf("Ignoring request to close %s server when it is already stopped\n", serverName);
            return;
        }

        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() - start <= timeoutMillis) {
            if (this.consumer != null) {
                this.consumer.stop();
                isActive = false;
                break;
            } else {
                Thread.sleep(10);
            }
        }

        if (isActive)
            System.err.printf("Unable to stop %s server: not initialised\n", serverName);
        else
            System.err.printf("%s server has been stopped\n", serverName);
    }

    public String name() {
        return this.serverName;
    }

}
