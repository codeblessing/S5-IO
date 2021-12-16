package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.poznan.put.jsontools.error.JsonToolsInvalidJsonError;

import java.util.List;

/**
 * Validates JSON syntax.
 */
public class JsonValidator {
//
//    /**
//     * @param json JSON source to validate.
//     *             <p>
//     *             Checks whether given JSON is correctly formed.
//     *             </p>
//     * @throws JsonToolsInvalidJsonError in case of malformed JSON source.
//     */
//    public static void validate(String json) throws JsonToolsInvalidJsonError {
//        ObjectMapper mapper = new ObjectMapper();
//
//        _validate(json, mapper);
//    }
//
//    public static void validateAll(List<JsonNode> jsons) throws JsonToolsInvalidJsonError {
//        ObjectMapper mapper = new ObjectMapper();
//
//        for (var json : jsons) {
//            _validate(json, mapper);
//        }
//    }
//
//    private static void _validate(String json, ObjectMapper mapper) {
//        try {
//            mapper.readTree(json);
//        } catch (JsonProcessingException e) {
//            var location = e.getLocation();
//            String message = "Invalid JSON at line " + location.getLineNr() + " column " + location.getColumnNr() + ".";
//            throw new JsonToolsInvalidJsonError(message, json);
//        }
//    }
}
