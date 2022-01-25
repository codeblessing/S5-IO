package pl.poznan.put.jsontools.logic;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonTransformRetainAttributesTest {
    @Test
    public void testEmptyAttributes() {
        String json = "{\"remove\":\"data\",\"numeric\":5,\"array\":[\"garlic\",\"olive oil\",\"pepper\",\"salt\"],\"retain\":\"I'm alive\"}";
        JsonTransformRetainAttributes transform = new JsonTransformRetainAttributes(new JsonBase(json), Collections.emptyList());

        String excepted = "{}";
        assertEquals(excepted, transform.execute());
    }

    @Test
    public void testEmptyData() {
        String json = "{}";
        JsonTransformRetainAttributes transform = new JsonTransformRetainAttributes(new JsonBase(json), Arrays.asList("wolves", "amidst"));

        String excepted = "{}";
        assertEquals(excepted, transform.execute());
    }

    @Test
    public void testRetainAttributes1() {
        String json = "{\"remove\":\"data\",\"numeric\":5,\"array\":[\"garlic\",\"olive oil\",\"pepper\",\"salt\"],\"retain\":\"I'm alive\"}";
        JsonTransformRetainAttributes transform = new JsonTransformRetainAttributes(new JsonBase(json), Arrays.asList("retain", "numeric"));

        String excepted = "{\"numeric\":5,\"retain\":\"I'm alive\"}";
        assertEquals(excepted, transform.execute());
    }

    @Test
    public void testRetainAttributes2() {
        String json = "{\"remove\":\"data\",\"numeric\":5,\"array\":[\"garlic\",\"olive oil\",\"pepper\",\"salt\"],\"retain\":\"I'm alive\"}";
        JsonTransformRetainAttributes transform = new JsonTransformRetainAttributes(new JsonBase(json), Arrays.asList("array", "remove"));

        String excepted = "{\"remove\":\"data\",\"array\":[\"garlic\",\"olive oil\",\"pepper\",\"salt\"]}";
        assertEquals(excepted, transform.execute());
    }

    @Test
    public void testRetainAttributes3() {
        String json = "{\"remove\":\"data\",\"numeric\":5, \"obj\":{\"a1\":\"haahk\",\"a2\":\"hah\"}}";
        JsonTransformRetainAttributes transform = new JsonTransformRetainAttributes(new JsonBase(json), List.of("obj"));

        String excepted = "{\"obj\":{\"a1\":\"haahk\",\"a2\":\"hah\"}}";
        assertEquals(excepted, transform.execute());
    }
}