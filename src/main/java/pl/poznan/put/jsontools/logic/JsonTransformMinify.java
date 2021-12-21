package pl.poznan.put.jsontools.logic;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTransformMinify extends JsonTransformer {
    public JsonTransformMinify(JsonTransform transform) {
        super(transform);
    }
    private static final ObjectMapper mapper = new ObjectMapper();
    @Override
    public String execute() {
        try {
            return mapper.readTree(super.execute()).toString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
