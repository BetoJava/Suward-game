package fr.suward.aaa;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.HashMap;

public class HashMapSerializer extends StdSerializer<HashMap> {

    public HashMapSerializer() {
        this(null);
    }

    public HashMapSerializer(Class<HashMap> t) {
        super(t);
    }

    public HashMapSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    @Override
    public void serialize(HashMap value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(jgen, value);
    }
}
