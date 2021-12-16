package pl.poznan.put.jsontools.error;

public class JsonToolsInvalidJsonError extends RuntimeException {
    private final String _json;

    public JsonToolsInvalidJsonError() {
        super();
        this._json = "";
    }

    public JsonToolsInvalidJsonError(String message, String json) {
        super(message);
        this._json = json;
    }

    public String get_json() {
        return _json;
    }
}
