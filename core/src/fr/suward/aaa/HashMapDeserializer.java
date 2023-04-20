package fr.suward.aaa;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import fr.suward.game.entities.stats.Stat;

import java.io.IOException;
import java.util.HashMap;

public class HashMapDeserializer extends StdDeserializer<HashMap> {

    public HashMapDeserializer() {
        super((JavaType) null);
    }

    public HashMapDeserializer(Class<?> vc) {
        super(vc);
    }

    public HashMapDeserializer(JavaType valueType) {
        super(valueType);
    }

    @Override
    public HashMap deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Stat> result = mapper.readValue(jp.getValueAsString(), new TypeReference<HashMap<String,Stat>>() {});
        return result;
    }
}
