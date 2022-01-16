package pl.poznan.put.jsontools.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonTransformSortFieldsTest {

    @Test
    public void test_sort_unsorted() {
        var unsorted = "{\"a\":\"Hello\",\"b\":\"Nice\",\"f\":\"Isildur\",\"d\":\"Samwise\",\"c\":{\"d\":\"Meriadok\",\"b\":\"Peregrin\",\"cdata\":\"<!-- CDATA -->\",\"abba\":\"Mamma Mia!\"},\"e\":\"Eomer\"}";
        var expected = "{\"a\":\"Hello\",\"b\":\"Nice\",\"c\":{\"abba\":\"Mamma Mia!\",\"b\":\"Peregrin\",\"cdata\":\"<!-- CDATA -->\",\"d\":\"Meriadok\"},\"d\":\"Samwise\",\"e\":\"Eomer\",\"f\":\"Isildur\"}";

        var actual = new JsonTransformSortFields(new JsonBase(unsorted)).execute();

        assertEquals(expected, actual);
    }

    @Test
    public void test_sort_sorted() {
        var expected = "{\"a\":\"Hello\",\"b\":\"Nice\",\"c\":{\"abba\":\"Mamma Mia!\",\"b\":\"Peregrin\",\"cdata\":\"<!-- CDATA -->\",\"d\":\"Meriadok\"},\"d\":\"Samwise\",\"e\":\"Eomer\",\"f\":\"Isildur\"}";
        var actual = new JsonTransformSortFields(new JsonBase(expected)).execute();

        assertEquals(expected, actual);
    }
}