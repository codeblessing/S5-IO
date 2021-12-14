package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.poznan.put.jsontools.error.JsonToolsInvalidJsonError;

/**
 * Validates JSON syntax.
 */
public class JsonValidator {

    /**
     * @param json JSON source to validate.
     *             <p>
     *             Checks whether given JSON is correctly formed.
     *             </p>
     * @throws JsonToolsInvalidJsonError in case of malformed JSON source.
     */
    public static void validate(String json) throws JsonToolsInvalidJsonError {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.readTree(json);
        } catch (JsonProcessingException e) {
            var location = e.getLocation();
            String message = "Invalid JSON at line " + location.getLineNr() + " column " + location.getColumnNr() + ".";
            throw new JsonToolsInvalidJsonError(message, json);
        }
    }
}
