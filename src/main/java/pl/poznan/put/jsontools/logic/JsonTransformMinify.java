package pl.poznan.put.jsontools.logic;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import pl.poznan.put.jsontools.error.JsonToolsInvalidJsonError;
import org.slf4j.Logger;

/**
 * Class enables to get minified JSON.
 */
public class JsonTransformMinify extends JsonTransformer {

    private static final Logger _logger = LoggerFactory.getLogger(JsonTransformMinify.class);

    /**
     * Object mapper to retrieve JSON structure from string.
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * @param transform transform to be executed before minification.
     */
    public JsonTransformMinify(JsonTransform transform) {
        super(transform);
        _logger.debug("Minify transform created.");
    }

    /**
     * Removes line breaks, whitespaces, tabs from JSON.
     *
     * @return Transformed JSON.
     */
    @Override
    public String execute() {
        _logger.debug("Executing JSON minification.");
        try {
            return mapper.readTree(super.execute()).toString();
        } catch (JsonProcessingException e) {
            throw new JsonToolsInvalidJsonError(e.getMessage());
        }

    }
}
