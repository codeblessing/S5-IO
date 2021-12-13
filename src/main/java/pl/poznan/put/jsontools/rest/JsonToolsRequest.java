package pl.poznan.put.jsontools.rest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class JsonToolsRequest {
    @NotNull(message = "transforms has to be non-empty")
    @NotEmpty
    public List<String> transforms;
    @NotNull(message = "data has to be non-empty")
    @NotEmpty
    public List<String> data;
}
