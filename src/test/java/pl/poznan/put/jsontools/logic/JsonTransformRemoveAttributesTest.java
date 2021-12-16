package pl.poznan.put.jsontools.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class JsonTransformRemoveAttributesTest {
    @Test
    public void testEmptyAttributes() {
        String json = "{\"hello\":\"there\",\"general\":\"kenobi\"}";
        JsonTransformRemoveAttributes transform = new JsonTransformRemoveAttributes(new ArrayList<>());

        assertEquals(json, transform.execute(json));
    }

    @Test
    public void testEmptyData() {
        String json = "{}";
        JsonTransformRemoveAttributes transform = new JsonTransformRemoveAttributes(Arrays.asList("wolves", "amidst"));

        assertEquals(json, transform.execute(json));
    }

    @Test
    public void testRemoveAttributes() {
        String json = "{\"remove\":\"data\",\"numeric\":5,\"array\":[\"garlic\",\"olive oil\",\"pepper\",\"salt\"],\"retain\":\"I'm alive\"}";
        String expected = "{\"retain\":\"I'm alive\"}";

        JsonTransformRemoveAttributes transform = new JsonTransformRemoveAttributes(Arrays.asList("remove", "numeric", "array"));

        assertEquals(expected, transform.execute(json));
    }

    @Test
    public void testRetainOrder() {
        String json = "{\"wolves\":\"asleep\",\"numeric\":5,\"array\":[\"garlic\",\"olive oil\",\"pepper\",\"salt\"],\"retain\":\"I'm alive\"}";
        String expected = "{\"wolves\":\"asleep\",\"retain\":\"I'm alive\"}";

        JsonTransformRemoveAttributes transform = new JsonTransformRemoveAttributes(Arrays.asList("numeric", "array"));

        assertEquals(expected, transform.execute(json));
    }
}