package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import pl.poznan.put.jsontools.error.JsonToolsInvalidJsonError;

import java.util.*;

/**
 * Recursively sorts attributes in JSON.
 * Does not change items in lists.
 */
public class JsonTransformSortFields extends JsonTransformer {
    private static final Logger _logger = LoggerFactory.getLogger(JsonTransformSortFields.class);
    private static final ObjectMapper _mapper = new ObjectMapper();

    public JsonTransformSortFields(JsonTransform transform) {
        super(transform);
        _logger.info("SortFields transform created.");
    }

    @Override
    public String execute() {
        var preprocessed = super.execute();
        try {
            var json = _mapper.readTree(preprocessed);
            var sorted = sort(json);
            return sorted.toString();
        } catch (JsonProcessingException e) {
            throw new JsonToolsInvalidJsonError("Failed to process JSON", preprocessed);
        }
    }

    /**
     * Recursively sorts attributes in JSON object.
     * Does not change attributes in lists, just object and directly nested objects.
     *
     * @param input JsonNode to be sorted.
     * @return JSON with sorted attributes in form of Jackson's JsonNode.
     */
    private JsonNode sort(JsonNode input) {
        var fields = input.fields();
        List<Map.Entry<String, JsonNode>> names = new ArrayList<>();
        fields.forEachRemaining(names::add);

        TreeMap<String, JsonNode> output = new TreeMap<>();
        for(var entry : names) {
            if(entry.getValue().isObject()) {
                var value = sort(entry.getValue());
                output.put(entry.getKey(), value);
            } else {
                output.put(entry.getKey(), entry.getValue());
            }
        }

        return _mapper.convertValue(output, JsonNode.class);
    }
}
