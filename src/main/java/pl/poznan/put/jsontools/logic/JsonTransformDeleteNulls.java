package pl.poznan.put.jsontools.logic;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.poznan.put.jsontools.error.JsonToolsInvalidJsonError;

/**
 * Class enables to get JSON with no empty fields.
 */
public class JsonTransformDeleteNulls extends JsonTransformer {

    /**
     * Class-level logger instance.
     */
    private static final Logger _logger = LoggerFactory.getLogger(JsonTransformDeleteNulls.class);

    /**
     * @param transform transform to be executed before empty values removal.
     */
    public JsonTransformDeleteNulls(JsonTransform transform) {
        super(transform);
        _logger.debug("DeleteNulls transform created.");
    }

    /**
     * Removes attributes with empty values.
     *
     * @return Transformed JSON.
     */
    @Override
    public String execute() {
        String input = super.execute();
        _logger.debug("Executing deleting null values");
        try {
            String json = input.replace("\"\"", "null");
            JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
            Gson gson = new GsonBuilder().create();
            return gson.toJson(jsonObject);
        } catch (Exception e) {
            throw new JsonToolsInvalidJsonError(e.getMessage());
        }
    }
}


