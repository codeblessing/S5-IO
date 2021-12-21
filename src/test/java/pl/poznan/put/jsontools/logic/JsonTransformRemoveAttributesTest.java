package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonTransformRemoveAttributesTest {
    @Test
    public void testEmptyAttributes() {
        var json = "{\"hello\":\"there\",\"general\":\"kenobi\"}";
        var transform = new JsonTransformRemoveAttributes(new JsonBase(json), new ArrayList<>());

        assertEquals(json, transform.execute());
    }

    @Test
    public void testEmptyData() {
        var json = "{}";
        var transform = new JsonTransformRemoveAttributes(new JsonBase(json), Arrays.asList("wolves", "amidst"));

        assertEquals(json, transform.execute());
    }

    @Test
    public void testRemoveAttributes() {
        var json = "{\"remove\":\"data\",\"numeric\":5,\"array\":[\"garlic\",\"olive oil\",\"pepper\",\"salt\"],\"retain\":\"I'm alive\"}";
        var expected = "{\"retain\":\"I'm alive\"}";

        var transform = new JsonTransformRemoveAttributes(new JsonBase(json), Arrays.asList("remove", "numeric", "array"));

        assertEquals(expected, transform.execute());
    }

    @Test
    public void testRetainOrder() {
        var json = "{\"wolves\":\"asleep\",\"numeric\":5,\"array\":[\"garlic\",\"olive oil\",\"pepper\",\"salt\"],\"retain\":\"I'm alive\"}";
        var expected = "{\"wolves\":\"asleep\",\"retain\":\"I'm alive\"}";

        var transform = new JsonTransformRemoveAttributes(new JsonBase(json), Arrays.asList("numeric", "array"));

        assertEquals(expected, transform.execute());
    }
}