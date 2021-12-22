package pl.poznan.put.jsontools.logic;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.poznan.put.jsontools.error.JsonToolsInvalidJsonError;

public class JsonTransformMinify extends JsonTransformer {
    private static final ObjectMapper mapper = new ObjectMapper();

    public JsonTransformMinify(JsonTransform transform) {
        super(transform);
    }

    @Override
    public String execute() {
        try {
            return mapper.readTree(super.execute()).toString();
        } catch (JsonProcessingException e) {
            throw new JsonToolsInvalidJsonError(e.getMessage());
        }

    }
}
