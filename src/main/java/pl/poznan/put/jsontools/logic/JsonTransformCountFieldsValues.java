package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.poznan.put.jsontools.error.JsonToolsInvalidJsonError;

import java.util.*;

/**
 * Counts values in JSON nodes.
 */


public class JsonTransformCountFieldsValues extends JsonTransformer {
    private static final Logger logger = LoggerFactory.getLogger(JsonTransformCountFieldsValues.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public JsonTransformCountFieldsValues(JsonTransform transform) {
        super(transform);
        logger.info("CountFieldsValues transform created.");
    }

    @Override
    public String execute() {
        String json = super.execute();
        logger.debug("Executing counting nodes' values.");
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode counted = count(jsonNode);

            return counted.toString();
        } catch (JsonProcessingException e) {
            logger.error("Invalid JSON: " + json);
            throw new JsonToolsInvalidJsonError(e.getLocation().toString(), json);
        }
    }

    private JsonNode count(JsonNode jsonNode) {

        List<Map.Entry<String, JsonNode>> fields = new ArrayList<>();
        jsonNode.fields().forEachRemaining(fields::add);
        ObjectNode objectNode = objectMapper.createObjectNode();

        for (var field : fields) {
            if (field.getValue().isArray()) {
                String fieldName = field.getKey();
                int fieldValues = field.getValue().size();
                objectNode.put(fieldName, fieldValues + " elements in array");
            } else if (field.getValue().isObject()) {
                String fieldName = field.getKey();
                int fieldValues = field.getValue().size();
                objectNode.put(fieldName, fieldValues + " fields in object");
            } else if (field.getValue().isEmpty()) {
                String fieldName = field.getKey();
                objectNode.put(fieldName, 1 + " value in node");
            }

        }
        return objectNode;
    }
}
