package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.poznan.put.jsontools.error.JsonToolsInvalidJsonError;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * RemoveAttributes decorator.
 *
 * Removes plain attributes given in constructor from JSON retrieved from `transform`.
 * Does not remove nested attributes.
 */
public class JsonTransformRemoveAttributes extends JsonTransformer {
    /**
     * Class-level logger instance.
     */
    private static final Logger _logger = LoggerFactory.getLogger(JsonTransformRemoveAttributes.class);
    /**
     * Object mapper to retrieve JSON structure from string.
     */
    private static final ObjectMapper _mapper = new ObjectMapper();

    /**
     * Attributes to be removed from JSON.
     */
    private final List<String> _attributes;

    /**
     * @param transform transform to be executed before attribute removal. Used to retrieve JSON for transform.
     * @param attributes attributes to be removed from JSON.
     */
    public JsonTransformRemoveAttributes(JsonTransform transform, List<String> attributes) {
        super(transform);
        this._attributes = attributes;
        _logger.debug("RemoveAttributes transform created.");
    }

    /**
     * Removes given attributes from JSON.
     * @return Transformed JSON.
     */
    @Override
    public String execute() {
        var preprocessed = super.execute();
        _logger.debug("Executing attributes removal.");
        try {
            var json = _mapper.readValue(preprocessed, new TypeReference<LinkedHashMap<String, Object>>() {
            });

            for (var attr : this._attributes) {
                var result = json.remove(attr);
                if (result != null) {
                    _logger.debug("Attribute " + attr + " removed from JSON");
                }
            }

            return _mapper.convertValue(json, JsonNode.class).toString();

        } catch (JsonProcessingException e) {
            _logger.error("Invalid JSON:\n" + preprocessed);
            throw new JsonToolsInvalidJsonError("Invalid JSON", preprocessed);
        }
    }
}
