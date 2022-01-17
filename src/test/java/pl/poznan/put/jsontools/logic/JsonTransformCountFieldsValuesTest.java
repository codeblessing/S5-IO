package pl.poznan.put.jsontools.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonTransformCountFieldsValuesTest {
    @Test
    public void testEmptyData() {
        String json = "{}";
        JsonTransformCountFieldsValues transform = new JsonTransformCountFieldsValues(new JsonBase(json));

        assertEquals(json, transform.execute());
    }

    @Test
    public void testArrayElementsCount() {
        String json = "{\"array\":[\"Lory\",\"Evita\",\"Modestia\",\"Hildegaard\",\"Kate\"]}";
        String expected = "{\"array\":\"5 elements in array\"}";

        assertEquals(expected, new JsonTransformCountFieldsValues(new JsonBase(json)).execute());
    }

    @Test
    public void testObjectElementsCount() {
        String json = "{\"array of objects\":{\"index\":0,\"index start at 5\":5,\"size\":\"biiig\"}}";
        String expected = "{\"array of objects\":\"3 fields in object\"}";

        assertEquals(expected, new JsonTransformCountFieldsValues(new JsonBase(json)).execute());
    }

    @Test
    public void testNodeValuesCount(){
        String json = "{\"random\":28,\"random float\":90.398,\"bool\":true,\"date\":\"1995-04-18\",\"regEx\":\"hellooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo to you\"}";
        String expected = "{\"random\":\"1 value in node\",\"random float\":\"1 value in node\",\"bool\":\"1 value in node\",\"date\":\"1 value in node\",\"regEx\":\"1 value in node\"}";

        assertEquals(expected, new JsonTransformCountFieldsValues(new JsonBase(json)).execute());
    }
}
