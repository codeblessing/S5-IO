package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class enables to transform minified JSON file into JSON full form
 */
public class JsonTransformFormat implements JsonFinalTransform {

    /**
     * ObjectMapper to apply formatting to the JSON file
     */
    private static final ObjectMapper mapper = new ObjectMapper();
    /**
     * Log errors in JsonTransformFormat class
     */
    private static final Logger logger = LoggerFactory.getLogger(JsonTransformFormat.class);

    /**
     * @param json minified JSON file
     * @return String prettified file (with proper formatting, e.g. spaces, tabs, newlines)
     */
    @Override
    public String execute(JsonNode json) {

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (JsonProcessingException e) {
            logger.error("Could not deminify JSON!", e);
            return null;
        }
    }
}
