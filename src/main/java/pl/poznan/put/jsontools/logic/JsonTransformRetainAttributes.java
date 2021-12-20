package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.poznan.put.jsontools.error.JsonToolsInvalidJsonError;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class enables to get JSON containing only certain properties.
 */
public class JsonTransformRetainAttributes extends JsonTransformer{

    /**
     * Attributes to save from JSON.
     */
    private final List<String> _attributes;
    /**
     * Class-level logger instance.
     */
    private static final Logger _logger = LoggerFactory.getLogger(JsonTransformRetainAttributes.class);


    /**
     * @param transform transform to be executed before attributes will be save.
     * @param attributes attributes to be saved from JSON.
     */
    public JsonTransformRetainAttributes(JsonTransform transform, List<String> attributes) {
        super(transform);
        this._attributes = attributes;
        _logger.debug("RetainAttributes transform created.");
    }

    /**
     * It retains given attributes.
     * @return  JSON containing only attributes specified by  {@link #_attributes}
     */
    @Override
    public String execute() {
        String json = super.execute();
        _logger.debug("Executing saving attributes");
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonnode = mapper.readTree(json);
            List<String> jsonattributes = new ArrayList<>();
            Iterator<String> NameIterator = jsonnode.fieldNames();
            while(NameIterator.hasNext()) {
                jsonattributes.add(NameIterator.next());
            }
            for(String attribute: jsonattributes){
                if(!this._attributes.contains(attribute)){
                    ((ObjectNode)jsonnode).remove(attribute);
                }
            }

            return mapper.convertValue(jsonnode, JsonNode.class).toString();
        }
        catch (JsonProcessingException e) {
            throw new JsonToolsInvalidJsonError("Invalid JSON", json);
        }
    }
}
