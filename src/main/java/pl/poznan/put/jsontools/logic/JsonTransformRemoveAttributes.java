package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.poznan.put.jsontools.error.JsonToolsInvalidJsonError;

import java.util.LinkedHashMap;
import java.util.List;

public class JsonTransformRemoveAttributes implements JsonTransform {
    private static final Logger logger = LoggerFactory.getLogger(JsonTransformRemoveAttributes.class);
    private final List<String> _attributes;

    public JsonTransformRemoveAttributes(List<String> attributes) {
        this._attributes = attributes;
    }

    @Override
    public String execute(String json) {
        var mapper = new ObjectMapper();
        try {
            var destructured = mapper.readValue(json, new TypeReference<LinkedHashMap<String, Object>>() {
            });

            for (var attr : _attributes) {
                destructured.remove(attr);
            }

            return mapper.writeValueAsString(destructured);
        } catch (JsonProcessingException e) {
            logger.error("Invalid JSON. THIS IS BUG AND SHOULD BE SUBMITTED TO https://github.com/codeblessing/json-tools/issues.");
            throw new JsonToolsInvalidJsonError();
        }
    }
}
