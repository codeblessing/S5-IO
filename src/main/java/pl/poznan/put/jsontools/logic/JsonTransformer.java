package pl.poznan.put.jsontools.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class JsonTransformer {

    private final List<JsonTransform> _transforms;

    public JsonTransformer(List<JsonTransform> transforms) {
        this._transforms = new ArrayList<>();
        for (var transform : transforms) {
            this.addTransform(transform);
        }
    }

    public void addTransform(JsonTransform transform) {
        this._transforms.add(transform);
    }

    public List<String> execute(List<String> jsons) {
        List<String> out = new ArrayList<>();
        for (var json : jsons) {
            String transformed = json;
            for (var transform : _transforms) {
                transformed = transform.execute(transformed);
            }
            out.add(transformed);
        }

        return out;
    }
}
