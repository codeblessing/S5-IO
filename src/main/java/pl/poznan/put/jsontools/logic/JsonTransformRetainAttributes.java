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


public class JsonTransformRetainAttributes extends JsonTransformer{


    private final List<String> _attributes;

    private static final Logger _logger = LoggerFactory.getLogger(JsonTransformRetainAttributes.class);



    public JsonTransformRetainAttributes(JsonTransform transform, List<String> attributes) {
        super(transform);
        this._attributes = attributes;
        _logger.debug("RetainAttributes transform created.");
    }


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
