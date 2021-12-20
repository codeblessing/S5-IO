package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;

public class JsonTransformRemoveAttributes implements JsonTransform {
    private static final Logger logger = LoggerFactory.getLogger(JsonTransformRemoveAttributes.class);
    private final List<String> _attributes;

    public JsonTransformRemoveAttributes(List<String> attributes) {
        this._attributes = attributes;
    }

    @Override
    public JsonNode execute(JsonNode json) {
        var mapper = new ObjectMapper();
        LinkedHashMap<String, Object> parsed = mapper.convertValue(json, new TypeReference<>() {
        });

        for (var attr : this._attributes) {
            parsed.remove(attr);
        }

        return mapper.convertValue(parsed, JsonNode.class);
    }
}
