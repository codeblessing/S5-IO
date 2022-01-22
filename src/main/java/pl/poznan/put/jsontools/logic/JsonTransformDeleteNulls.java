package pl.poznan.put.jsontools.logic;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.poznan.put.jsontools.error.JsonToolsInvalidJsonError;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class JsonTransformDeleteNulls extends JsonTransformer {

    /**
     * Class-level logger instance.
     */
    private static final Logger _logger = LoggerFactory.getLogger(JsonTransformRemoveAttributes.class);
    /**
     * Object mapper to retrieve JSON structure from string.
     */
    private static final ObjectMapper mapper = new ObjectMapper();


    public JsonTransformDeleteNulls(JsonTransform transform) {
        super(transform);
        _logger.debug("DeleteNulls transform created.");
    }

    @Override
    public String execute() {
        String input = super.execute();
        _logger.debug("Executing deleting null values");
        try {
            String json = input.replace("\"\"", "null");
            JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
            String s = jsonObject.toString();
            Gson gson = new GsonBuilder().create();
            s = gson.toJson(jsonObject);
            return s;
        }
        catch (Exception e) {
            throw new JsonToolsInvalidJsonError(e.getMessage());
        }
    }
}


