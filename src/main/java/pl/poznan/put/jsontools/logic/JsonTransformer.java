package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

/**
 * JsonTools main business logic class.
 */
public class JsonTransformer {
    /**
     * List of non-final transforms to run on JSON.
     * <p>
     * These transforms operate on JSON nodes, where formatting
     * is not important.
     */
    private final List<JsonTransform> _transforms;

    /**
     * Final transform to run on JSON.
     * <p>
     * This transform returns JSON as a String.
     * It's intended to operations where formatting matters.
     */
    private JsonFinalTransform _finalTransform;

    public JsonTransformer() {
        this._transforms = new ArrayList<>();
    }


    /**
     * @param transform non-final JSON transform to apply.
     *
     *                  <p>
     *                  Appends transform to transform queue.
     *                  <p>
     *                  Does not check for duplicates.
     *                  </p>
     */
    public void addTransform(JsonTransform transform) {
        this._transforms.add(transform);
    }

    /**
     * @param transform final JSON transform to apply.
     *
     *                  <p>
     *                  Sets final transform that should be done on JSON.
     *                  <p>
     *                  It does not check whether different transform is already set,
     *                  so only last final transform is executed on JSON.
     *                  <p>
     *                  It is silently assumed that final transformations cannot be executed between
     *                  non-final transformations, so it does not matter if this function is called after all,
     *                  or in between the {@link #addTransform(JsonTransform) addTransform} functions.
     *                  </p>
     */
    public void addFinalTransform(JsonFinalTransform transform) {
        this._finalTransform = transform;
    }

    /**
     * @param json JSON to be transformed.
     *             Executes requested non-final transformations in order, ending with last final transform
     *             as described in {@link #addFinalTransform(JsonFinalTransform) addFinalTransform} docs.
     * @return Stringified JSON after all transformations.
     */
    public String execute(JsonNode json) {
        var transformed = json;
        for (var transform : this._transforms) {
            transformed = transform.execute(transformed);
        }

        if (this._finalTransform != null) {
            return this._finalTransform.execute(transformed);
        }

        return transformed.toString();
    }
}
