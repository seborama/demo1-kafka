package seborama.demo2.kafka.processor;

import org.apache.kafka.streams.processor.AbstractProcessor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.slf4j.LoggerFactory;
import seborama.demo2.kafka.model.Order;

import java.util.Date;

public class OrderFulfilmentProcessor extends AbstractProcessor<String, Order> {

    private final static org.slf4j.Logger log = LoggerFactory.getLogger(OrderFulfilmentProcessor.class);

    /**
     * Process the record with the given key and value.
     *
     * @param key   the key for the record
     * @param order the value for the record
     */
    @Override
    public void process(String key, Order order) {
        if (order == null) return;

        order.setFulfilled(true);

        context().forward(order.getId() + "_" + Long.toString(new Date().getTime()), order);
    }

    /**
     * Initialize this processor with the given context. The framework ensures this is called once per processor when the topology
     * that contains it is initialized.
     * <p>
     * If this processor is to be {@link #punctuate(long) called periodically} by the framework, then this method should
     * {@link ProcessorContext#schedule(long) schedule itself} with the provided context.
     *
     * @param context the context; may not be null
     */
    @Override
    public void init(ProcessorContext context) {
        super.init(context);
    }
}
