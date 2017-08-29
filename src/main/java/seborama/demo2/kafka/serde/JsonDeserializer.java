package seborama.demo2.kafka.serde;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.log4j.Logger;

import java.util.Map;

public class JsonDeserializer<T> implements Deserializer<T> {

    private final static org.apache.log4j.Logger log = Logger.getLogger(JsonDeserializer.class);
    private Gson gson = new Gson();
    private Class<T> deserializedClass;

    public JsonDeserializer(Class<T> deserializedClass) {
        this.deserializedClass = deserializedClass;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        if (deserializedClass == null) {
            deserializedClass = (Class<T>) configs.get("serializedClass");
        }
    }

    @Override
    public T deserialize(String topic, byte[] bytes) {
        if (bytes == null) {
            log.warn("deserialize - " + deserializedClass.getName() + " - topic=" + topic + " - data=null");
            return null;
        }

        log.debug("deserialize - " + deserializedClass.getName() + " - topic=" + topic + " - data=" + new String(bytes));

        try {
            return gson.fromJson(new String(bytes), deserializedClass);
        } catch (JsonParseException e) {
            log.error("deserialize - JsonParseException - " + deserializedClass.getName() + " - topic=" + topic + " - data=" + new String(bytes) + " - error=" + e.getMessage());
            return null;
        }
    }

    @Override
    public void close() {
    }
}
