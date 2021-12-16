package pl.poznan.put.jsontools.rest;

import com.fasterxml.jackson.databind.JsonNode;

import javax.validation.constraints.NotNull;
import java.util.List;

public class JsonToolsSingleRequest {
    @NotNull
    public JsonNode data;
    public List<String> attributes;
}
