package seborama.demo2.kafka.serde;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.log4j.Logger;

import java.util.Map;

public class JsonSerializer<T> implements Serializer<T> {

    private final static org.apache.log4j.Logger log = Logger.getLogger(JsonSerializer.class);
    private Gson gson = new Gson();
    private String name;

    public JsonSerializer(String name) {
        this.name = name;
    }

    public void configure(Map<String, ?> map, boolean b) {
    }

    public byte[] serialize(String topic, T t) {
        if (t == null) {
            log.warn("serialize - " + name + " - topic=" + topic + " - data=null");
            return null;
        }

        byte[] jsonStr = gson.toJson(t).getBytes();
        log.debug("serialize - " + name + " - class=" + t.getClass().getName() + " - topic=" + topic + " - data=" + new String(jsonStr));

        return jsonStr;
    }

    public void close() {
    }
}
