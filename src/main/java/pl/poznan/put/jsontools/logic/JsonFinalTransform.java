package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.databind.JsonNode;

public interface JsonFinalTransform {
    String execute(JsonNode json);
}
