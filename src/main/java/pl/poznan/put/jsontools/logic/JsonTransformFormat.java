package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTransformFormat extends JsonTransformer {
    public JsonTransformFormat(JsonTransform transform) {
        super(transform);
    }

    @Override
    public String execute() {
        try {
            return new ObjectMapper().readTree(super.execute()).toPrettyString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
