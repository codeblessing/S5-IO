package pl.poznan.put.jsontools.logic;

/**
 * JsonTools main business logic class.
 */
public abstract class JsonTransformer implements JsonTransform {
    private final JsonTransform _wrapee;

    /**
     * @param transform JsonTransform interface.
     */
    public JsonTransformer(JsonTransform transform) {
        this._wrapee = transform;
    }

    /**
     * @return JSON file in form of String.
     */
    @Override
    public String execute() {
        return this._wrapee.execute();
    }
}
