package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Class enables to get JSON containing only certain properties.
 */
public class JsonTransformRetainAttributes implements JsonTransform {
    private final List<String> _attributes;
    /**
     * Class constructor specifying list of attributes to filter.
     */
    public JsonTransformRetainAttributes(List<String> attributes) {
        this._attributes = attributes;
    }

    /**
     * It enables to enter JsonNode  that will be simplified to structure containing certain attributes.
     * @param json JSON to be simplified
     * @return json This is JSON containing only attributes specified by  {@link #_attributes}
     */
    @Override
    public JsonNode execute(JsonNode json) {
        List<String> jsonAttributes = new ArrayList<>();
        Iterator<String> NameIterator = json.fieldNames();
        while(NameIterator.hasNext()) {
            String fieldName = NameIterator.next();
            jsonAttributes.add(fieldName);
        }
        for(String attribute: jsonAttributes){
            if(!this._attributes.contains(attribute)){
                ((ObjectNode)json).remove(attribute);
            }
        }
        return json;
    }
}
