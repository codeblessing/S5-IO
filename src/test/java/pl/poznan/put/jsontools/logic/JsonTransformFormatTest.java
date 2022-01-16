package pl.poznan.put.jsontools.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTransformFormatTest {
    @Test
    public void testEmptyFile() {
        String input = "{}";
        JsonTransformFormat transform = new JsonTransformFormat(new JsonBase(input));
        String expected = "{}";

        assertEquals(expected, transform.execute());
    }

    @Test
    public void testEasyFormat() {
        String input = "{\"random\":28,\"regEx\":\"hellooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo to you\"}";
        JsonTransformFormat transform = new JsonTransformFormat(new JsonBase(input));
        String expected = "{\r\n" +
                "  \"random\" : 28,\r\n" +
                "  \"regEx\" : \"hellooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo to you\"\r\n" +
                "}";

        assertEquals(expected, transform.execute());
    }

    @Test
    public void testModerateFormat() {
        String input = "{\"friends\":[{\"id\":0,\"name\":\"Dianna Madden\"},{\"id\":1,\"name\":\"Blanche Aguilar\"},{\"id\":2,\"name\":\"Lesley Jacobson\"}]}";
        JsonTransformFormat transform = new JsonTransformFormat(new JsonBase(input));
        String expected = "{\r\n" +
                "  \"friends\" : [ {\r\n" +
                "    \"id\" : 0,\r\n" +
                "    \"name\" : \"Dianna Madden\"\r\n" +
                "  }, {\r\n" +
                "    \"id\" : 1,\r\n" +
                "    \"name\" : \"Blanche Aguilar\"\r\n" +
                "  }, {\r\n" +
                "    \"id\" : 2,\r\n" +
                "    \"name\" : \"Lesley Jacobson\"\r\n" +
                "  } ]\r\n" +
                "}";

        assertEquals(expected, transform.execute());
    }
}
