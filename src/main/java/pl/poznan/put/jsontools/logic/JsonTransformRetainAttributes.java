package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class JsonTransformRetainAttributes implements JsonTransform {
    private final List<String> _attributes;

    public JsonTransformRetainAttributes(List<String> attributes) {
        this._attributes = attributes;
    }

    @Override
    public JsonNode execute(JsonNode json) {
        return null;
    }
}
