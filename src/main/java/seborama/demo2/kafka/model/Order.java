package seborama.demo2.kafka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    private String id;
    private boolean fulfilled;
    private boolean dispatched;
    private boolean completed;

    public void setId(String id) {
        this.id = id;
    }

    public void setFulfilled(Boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public void setDispatched(Boolean dispatched) {
        this.dispatched = dispatched;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getId() {
        return id;
    }

    public Boolean getFulfilled() {
        return fulfilled;
    }

    public Boolean getDispatched() {
        return dispatched;
    }

    public Boolean getCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", fulfilled=" + fulfilled +
                ", dispatched=" + dispatched +
                ", completed=" + completed +
                '}';
    }
}
