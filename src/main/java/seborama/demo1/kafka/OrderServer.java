package seborama.demo1.kafka;

import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;

import java.util.Map;

public abstract class OrderServer {
    private final String serverName;
    private final KafkaOrderConsumer consumer;

    protected OrderServer(String serverName, KafkaOrderConsumer consumer) {
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
        boolean isStopped = false;
        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() - start <= timeoutMillis) {
            if (this.consumer != null) {
                this.consumer.stop();
                isStopped = true;
                break;
            } else {
                Thread.sleep(10);
            }
        }

        if (isStopped)
            System.err.printf("%s server has been stopped\n", serverName);
        else
            System.err.printf("Unable to stop %s server: not initialised\n", serverName);
    }

    public String name() {
        return this.serverName;
    }

}
