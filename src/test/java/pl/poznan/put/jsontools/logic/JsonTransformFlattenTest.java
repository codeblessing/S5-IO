package pl.poznan.put.jsontools.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonTransformFlattenTest {
    @Test
    public void testEmptyData() {
        String json = "{}";
        JsonTransformFlatten transform = new JsonTransformFlatten(new JsonBase(json));
        String excepted = "{}";
        assertEquals(excepted, transform.execute());
    }

    @Test
    public void testFlattenWithoutNestedObject() {
        String json = "{\"remove\":\"data\",\"numeric\":5,\"retain\":\"I'm alive\"}";
        JsonTransformFlatten transform = new JsonTransformFlatten(new JsonBase(json));

        String excepted = "{\"remove\":\"data\",\"numeric\":5,\"retain\":\"I'm alive\"}";
        assertEquals(excepted, transform.execute());
    }

    @Test
    public void testFlattenNestedObject() {
        String json = "{\"remove\":\"data\",\"numeric\":5,\"nested\":{\"remove\":\"data\",\"numeric\":5},\"retain\":\"I'm alive\"}";
        JsonTransformFlatten transform = new JsonTransformFlatten(new JsonBase(json));

        String excepted = "{\"remove\":\"data\",\"numeric\":5,\"nested.remove\":\"data\",\"nested.numeric\":5,\"retain\":\"I'm alive\"}";
        assertEquals(excepted, transform.execute());
    }

    @Test
    public void testFlattenNestedArray() {
        String json = "{\"remove\":\"data\",\"animals\":[\"cat\",\"dog\"],\"retain\":\"I'm alive\"}";
        JsonTransformFlatten transform = new JsonTransformFlatten(new JsonBase(json));

        String excepted = "{\"remove\":\"data\",\"animals1\":\"cat\",\"animals2\":\"dog\",\"retain\":\"I'm alive\"}";
        assertEquals(excepted, transform.execute());
    }

    @Test
    public void testFlattenNestedObjectAndArray() {
        String json = "{\"remove\":\"data\",\"numeric\":5,\"nested\":{\"fruits\":{\"apples\":\"5\",\"pears\":\"6\",\"banana\":\"7\"},\"vegetables\":{\"tomatoes\":\"8\",\"potatoes\":\"9\"},\"basket\":[\"apples\",\"pears\"],\"moreNested\":{\"nottomatoes\":{\"1\":\"1\",\"2\":\"2\"},\"notbanana\":{\"3\":\"3\",\"4\":\"4\"}}},\"array\":[\"garlic\",\"olive oil\",{\"nestedInAarray\":\"abc\",\"moreNestedInArray\":{\"letters\":\"xyz\",\"numbers\":\"123\"},\"nestedArrayInNestedArray\":[\"====\",\"---\",\"****\"]},\"salt\"],\"retain\":\"I'm alive\"}";
        JsonTransformFlatten transform = new JsonTransformFlatten(new JsonBase(json));

        String excepted = "{\"remove\":\"data\",\"numeric\":5,\"nested.fruits.apples\":\"5\",\"nested.fruits.pears\":\"6\",\"nested.fruits.banana\":\"7\",\"nested.vegetables.tomatoes\":\"8\",\"nested.vegetables.potatoes\":\"9\",\"nested.basket1\":\"apples\",\"nested.basket2\":\"pears\",\"nested.moreNested.nottomatoes.1\":\"1\",\"nested.moreNested.nottomatoes.2\":\"2\",\"nested.moreNested.notbanana.3\":\"3\",\"nested.moreNested.notbanana.4\":\"4\",\"array1\":\"garlic\",\"array2\":\"olive oil\",\"array3.nestedInAarray\":\"abc\",\"array3.moreNestedInArray.letters\":\"xyz\",\"array3.moreNestedInArray.numbers\":\"123\",\"array3.nestedArrayInNestedArray1\":\"====\",\"array3.nestedArrayInNestedArray2\":\"---\",\"array3.nestedArrayInNestedArray3\":\"****\",\"array4\":\"salt\",\"retain\":\"I'm alive\"}";
        assertEquals(excepted, transform.execute());
    }
}