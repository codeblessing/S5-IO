package pl.poznan.put.jsontools.logic;

/**
 * JsonTools main business logic class.
 */
public abstract class JsonTransformer implements JsonTransform {
    private final JsonTransform _wrapee;

    public JsonTransformer(JsonTransform transform) {
        this._wrapee = transform;
    }
    @Override
    public String execute() {
        return this._wrapee.execute();
    }
}
