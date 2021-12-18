package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class JsonTransformRemoveAttributesTest {
    @Test
    public void testEmptyAttributes() throws JsonProcessingException {
        JsonNode json = new ObjectMapper().readTree("{\"hello\":\"there\",\"general\":\"kenobi\"}");
        JsonTransformRemoveAttributes transform = new JsonTransformRemoveAttributes(new ArrayList<>());

        assertEquals(json, transform.execute(json));
    }

    @Test
    public void testEmptyData() throws JsonProcessingException {
        JsonNode json = new ObjectMapper().readTree("{}");
        JsonTransformRemoveAttributes transform = new JsonTransformRemoveAttributes(Arrays.asList("wolves", "amidst"));

        assertEquals(json, transform.execute(json));
    }

    @Test
    public void testRemoveAttributes() throws JsonProcessingException {
    var mapper = new ObjectMapper();
        JsonNode json = mapper.readTree("{\"remove\":\"data\",\"numeric\":5,\"array\":[\"garlic\",\"olive oil\",\"pepper\",\"salt\"],\"retain\":\"I'm alive\"}");
        JsonNode expected = mapper.readTree("{\"retain\":\"I'm alive\"}");

        JsonTransformRemoveAttributes transform = new JsonTransformRemoveAttributes(Arrays.asList("remove", "numeric", "array"));

        assertEquals(expected, transform.execute(json));
    }


}