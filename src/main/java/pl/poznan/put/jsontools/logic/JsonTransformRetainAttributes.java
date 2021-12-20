package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class JsonTransformRetainAttributes extends JsonTransformer{
    private final List<String> _attributes;

    public JsonTransformRetainAttributes(JsonTransform transform, List<String> attributes) {
        super(transform);
        this._attributes = attributes;
    }

    @Override
    public String execute() {
        return null;
    }
}
