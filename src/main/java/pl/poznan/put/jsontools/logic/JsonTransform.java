package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.databind.JsonNode;

public interface JsonTransform {
    JsonNode execute(JsonNode json);
}
