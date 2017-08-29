package seborama.demo2.kafka.topology;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.processor.TopologyBuilder;
import seborama.demo2.kafka.model.Order;
import seborama.demo2.kafka.processor.OrderCompletionProcessor;
import seborama.demo2.kafka.processor.OrderDispatchProcessor;
import seborama.demo2.kafka.processor.OrderFulfilmentProcessor;
import seborama.demo2.kafka.serde.JsonDeserializer;
import seborama.demo2.kafka.serde.JsonSerializer;

public class OrderLifeCycle {

    public static final String ORDER_COMPLETION_STREAMS_TOPIC = "order-completion-streams-topic";
    public static final String ORDER_CREATION_STREAMS_TOPIC = "order-creation-streams-topic";

    public static TopologyBuilder buildTopology(String sourceTopic) {
        TopologyBuilder builder = new TopologyBuilder();

        StringSerializer stringSerializer = new StringSerializer();
        StringDeserializer stringDeserializer = new StringDeserializer();

        JsonDeserializer<Order> orderDeserializer = new JsonDeserializer<>(Order.class);
        JsonSerializer<Order> orderSerializer = new JsonSerializer<>(Order.class.getSimpleName());

        builder.addSource("order-creation-stream-source", stringDeserializer, orderDeserializer, sourceTopic)
                .addProcessor("order-fulfilment-processor", OrderFulfilmentProcessor::new, "order-creation-stream-source")
                .addProcessor("order-dispatch-processor", OrderDispatchProcessor::new, "order-fulfilment-processor")
                .addProcessor("order-completion-processor", OrderCompletionProcessor::new, "order-dispatch-processor")
                .addSink("order-completion-reporter-sink", ORDER_COMPLETION_STREAMS_TOPIC, stringSerializer, orderSerializer, "order-completion-processor")
        ;


        return builder;
    }

    @Override
    public String toString() {
        return "CustomerInvite{}";
    }
}
