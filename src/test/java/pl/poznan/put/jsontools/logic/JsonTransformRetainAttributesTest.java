package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class JsonTransformRetainAttributesTest {
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testEmptyAttributes() throws JsonProcessingException {
        JsonNode json = mapper.readTree("{\"remove\":\"data\",\"numeric\":5,\"array\":[\"garlic\",\"olive oil\",\"pepper\",\"salt\"],\"retain\":\"I'm alive\"}");
        JsonNode expected = mapper.readTree("{}");

        JsonTransformRetainAttributes transform = new JsonTransformRetainAttributes(Arrays.asList());

        assertEquals(expected, transform.execute(json));
    }

    @Test
    public void testEmptyData() throws JsonProcessingException {
        JsonNode json = mapper.readTree("{}");
        JsonNode expected = mapper.readTree("{}");
        JsonTransformRetainAttributes transform = new JsonTransformRetainAttributes(Arrays.asList("wolves", "amidst"));

        assertEquals(expected, transform.execute(json));
    }

    @Test
    public void testRetainAttributes() throws JsonProcessingException {
        JsonNode json = mapper.readTree("{\"remove\":\"data\",\"numeric\":5,\"array\":[\"garlic\",\"olive oil\",\"pepper\",\"salt\"],\"retain\":\"I'm alive\"}");
        JsonNode expected = mapper.readTree("{\"numeric\":5,\"retain\":\"I'm alive\"}");

        JsonTransformRetainAttributes transform = new JsonTransformRetainAttributes(Arrays.asList("retain", "numeric"));

        assertEquals(expected, transform.execute(json));
    }

    @Test
    public void testRetainAttributes2() throws JsonProcessingException {
        JsonNode json = mapper.readTree("{\"remove\":\"data\",\"numeric\":5,\"array\":[\"garlic\",\"olive oil\",\"pepper\",\"salt\"],\"retain\":\"I'm alive\"}");
        JsonNode expected = mapper.readTree("{\"remove\":\"data\",\"array\":[\"garlic\",\"olive oil\",\"pepper\",\"salt\"]}");

        JsonTransformRetainAttributes transform = new JsonTransformRetainAttributes(Arrays.asList("array", "remove"));

        assertEquals(expected, transform.execute(json));
    }
    @Test
    public void testRetainAttributes3() throws JsonProcessingException {
        JsonNode json = mapper.readTree("{\"remove\":\"data\",\"numeric\":5, \"obj\":{\"a1\":\"haahk\",\"a2\":\"hah\"} }");
        JsonNode expected = mapper.readTree("{\"obj\":{\"a1\":\"haahk\",\"a2\":\"hah\"} }");

        JsonTransformRetainAttributes transform = new JsonTransformRetainAttributes(Arrays.asList("obj"));

        assertEquals(expected, transform.execute(json));
    }
}