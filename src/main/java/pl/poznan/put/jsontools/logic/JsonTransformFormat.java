package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Class enables to transform minified JSON file into JSON full form
 */
public class JsonTransformFormat extends JsonTransformer {
    public JsonTransformFormat(JsonTransform transform) {
        super(transform);
    }
    /**
     * ObjectMapper to apply formatting to the JSON file
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     *
     * @return String prettified file (with proper formatting, e.g. spaces, tabs, newlines)
     */
    @Override
    public String execute() {
        try {
            return mapper.readTree(super.execute()).toPrettyString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

}
