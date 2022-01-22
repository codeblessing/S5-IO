package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.poznan.put.jsontools.error.JsonToolsInvalidJsonError;

/**
 * Class enables to transform minified JSON file into JSON full form
 */
public class JsonTransformFormat extends JsonTransformer {
    private static final Logger _logger = LoggerFactory.getLogger(JsonTransformFormat.class);
    public JsonTransformFormat(JsonTransform transform) {
        super(transform);
        _logger.info("Format transform created.");
    }

    /**
     * ObjectMapper to apply formatting to the JSON file
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * @return String prettified file (with proper formatting, e.g. spaces, tabs, newlines)
     */
    @Override
    public String execute() {
        try {
            return mapper.readTree(super.execute()).toPrettyString();
        } catch (JsonProcessingException e) {
            throw new JsonToolsInvalidJsonError(e.getMessage());
        }
    }

}
