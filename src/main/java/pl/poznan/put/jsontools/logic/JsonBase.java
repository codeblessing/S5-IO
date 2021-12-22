package pl.poznan.put.jsontools.logic;

public class JsonBase implements JsonTransform {
    private final String _json;

    public JsonBase(String json) {
        this._json = json;
    }

    @Override
    public String execute() {
        return _json;
    }
}
