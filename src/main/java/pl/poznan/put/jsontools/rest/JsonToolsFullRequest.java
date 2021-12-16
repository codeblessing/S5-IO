package pl.poznan.put.jsontools.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonToolsFullRequest {
    @NotNull(message = "transforms has to be non-empty")
    @NotEmpty
    public List<JsonNode> transforms;
    @NotNull(message = "data has to be non-empty")
    @NotEmpty
    public List<JsonNode> data;
}
