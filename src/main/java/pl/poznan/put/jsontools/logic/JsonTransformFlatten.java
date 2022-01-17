package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.poznan.put.jsontools.error.JsonToolsInvalidJsonError;

import java.util.ArrayList;
import java.util.List;

/**
 * Class enables to get  flattened JSON
 */
public class JsonTransformFlatten extends JsonTransformer {

    /**
     * Class-level logger instance.
     */
    private static final Logger _logger = LoggerFactory.getLogger(JsonTransformFlatten.class);


    /**
     * @param transform transform to be executed before flatten JSON
     */
    public JsonTransformFlatten(JsonTransform transform) {
        super(transform);
        _logger.debug("Flatten transform created.");
    }

    /**
     * flatten json.
     *
     * @return recursively flattened JSON
     */
    @Override
    public String execute() {
        String json = super.execute();
        _logger.debug("Executing flatten json");
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(json);
            JsonNode flattenJsonNode = mapper.createObjectNode();

            flattenNodes(jsonNode, flattenJsonNode, "");

            return flattenJsonNode.toString();
        } catch (JsonProcessingException e) {
            throw new JsonToolsInvalidJsonError(e.getLocation().toString(), json);
        }
    }

    private void flattenNodes(JsonNode root, JsonNode flattenJson, String nestedName) {
        if (root.isObject()) {
            List<String> jsonAttributes = new ArrayList<>();
            root.fieldNames().forEachRemaining(jsonAttributes::add);

            for (String attribute : jsonAttributes) {
                JsonNode fieldValue = root.get(attribute);
                if (fieldValue.isObject() || fieldValue.isArray()) {
                    if (nestedName.equals(""))
                        flattenNodes(fieldValue, flattenJson, nestedName + attribute);
                    else
                        flattenNodes(fieldValue, flattenJson, nestedName + "." + attribute);
                } else {
                    if (nestedName.equals(""))
                        ((ObjectNode) flattenJson).set(nestedName + attribute, fieldValue);
                    else
                        ((ObjectNode) flattenJson).set(nestedName + "." + attribute, fieldValue);

                }
            }
        } else if (root.isArray()) {
            Integer i = 0;
            for (JsonNode fieldValue : root) {
                if (fieldValue.isObject() || fieldValue.isArray()) {
                    flattenNodes(fieldValue, flattenJson, nestedName + (++i).toString());
                } else {
                    ((ObjectNode) flattenJson).set(nestedName + (++i).toString(), fieldValue);
                }
            }

        }

    }
}



