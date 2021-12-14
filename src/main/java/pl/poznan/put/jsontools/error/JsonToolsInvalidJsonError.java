package pl.poznan.put.jsontools.error;

public class JsonToolsInvalidJsonError extends RuntimeException {
    private String json;

    public JsonToolsInvalidJsonError() {
        super();
    }

    public JsonToolsInvalidJsonError(String message, String json) {
        super(message);
        this.json = json;
    }

    public String getJson() {
        return json;
    }
}
