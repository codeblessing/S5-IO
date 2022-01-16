package pl.poznan.put.jsontools.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTransformFormatTest {
    @Test
    public void testEmptyFile() {
        String input = "{}";
        JsonTransformFormat transform = new JsonTransformFormat(new JsonBase(input));
        String expected = "{ }";

        assertEquals(expected, transform.execute());
    }

    @Test
    public void testEasyFormat() {
        String input = "{\"random\":28,\"regEx\":\"hellooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo to you\"}";
        JsonTransformFormat transform = new JsonTransformFormat(new JsonBase(input));
        String expected = "{\n" +
                "  \"random\" : 28,\n" +
                "  \"regEx\" : \"hellooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo to you\"\n" +
                "}";

        assertEquals(expected, transform.execute());
    }

    @Test
    public void testModerateFormat() {
        String input = "{\"friends\":[{\"id\":0,\"name\":\"Dianna Madden\"},{\"id\":1,\"name\":\"Blanche Aguilar\"},{\"id\":2,\"name\":\"Lesley Jacobson\"}]}";
        JsonTransformFormat transform = new JsonTransformFormat(new JsonBase(input));
        String expected = "{\n" +
                "  \"friends\" : [ {\n" +
                "    \"id\" : 0,\n" +
                "    \"name\" : \"Dianna Madden\"\n" +
                "  }, {\n" +
                "    \"id\" : 1,\n" +
                "    \"name\" : \"Blanche Aguilar\"\n" +
                "  }, {\n" +
                "    \"id\" : 2,\n" +
                "    \"name\" : \"Lesley Jacobson\"\n" +
                "  } ]\n" +
                "}";

        assertEquals(expected, transform.execute());
    }
}
