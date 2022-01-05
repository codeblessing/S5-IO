package pl.poznan.put.jsontools.logic;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class JsonTransformFlattenTest {
    @Test
    public void testEmptyData() {
        String json = "{}";
        JsonTransformFlatten transform = new JsonTransformFlatten(new JsonBase(json));
        String excepted = "{}";
        assertEquals(excepted, transform.execute());
    }

    @Test
    public void testFlattenNestedObject(){
        String json = "{\"remove\":\"data\",\"numeric\":5,\"nested\":{\"remove\":\"data\",\"numeric\":5},\"retain\":\"I'm alive\"}";
        JsonTransformFlatten transform = new JsonTransformFlatten(new JsonBase(json));

        String excepted = "{\"remove\":\"data\",\"numeric\":5,\"nestedremove\":\"data\",\"nestednumeric\":5,\"retain\":\"I'm alive\"}";
        assertEquals(excepted, transform.execute());
    }

    @Test
    public void testFlattenNestedArray(){
        String json = "{\"remove\":\"data\",\"animals\":[\"cat\",\"dog\"],\"retain\":\"I'm alive\"}";
        JsonTransformFlatten transform = new JsonTransformFlatten(new JsonBase(json));

        String excepted = "{\"remove\":\"data\",\"animals1\":\"cat\",\"animals2\":\"dog\",\"retain\":\"I'm alive\"}";
        assertEquals(excepted, transform.execute());
    }
    @Test
    public void testFlattenNestedObjectAndArray(){
        String json = "{\"remove\":\"data\",\"numeric\":5,\"nested\":{\"fruits\":{\"apples\":\"5\",\"pears\":\"6\",\"banana\":\"7\"},\"vegetables\":{\"tomatoes\":\"8\",\"potatoes\":\"9\"},\"basket\":[\"apples\",\"pears\"],\"moreNested\":{\"nottomatoes\":{\"1\":\"1\",\"2\":\"2\"},\"notbanana\":{\"3\":\"3\",\"4\":\"4\"}}},\"array\":[\"garlic\",\"olive oil\",{\"nestedInAarray\":\"abc\",\"moreNestedInArray\":{\"letters\":\"xyz\",\"numbres\":\"123\"},\"nestedArrayInNestedArray\":[\"====\",\"---\",\"****\"]},\"salt\"],\"retain\":\"I'm alive\"}";
        JsonTransformFlatten transform = new JsonTransformFlatten(new JsonBase(json));

        String excepted = "{\"remove\":\"data\",\"numeric\":5,\"nestedfruitsapples\":\"5\",\"nestedfruitspears\":\"6\",\"nestedfruitsbanana\":\"7\",\"nestedvegetablestomatoes\":\"8\",\"nestedvegetablespotatoes\":\"9\",\"nestedbasket1\":\"apples\",\"nestedbasket2\":\"pears\",\"nestedmoreNestednottomatoes1\":\"1\",\"nestedmoreNestednottomatoes2\":\"2\",\"nestedmoreNestednotbanana3\":\"3\",\"nestedmoreNestednotbanana4\":\"4\",\"array1\":\"garlic\",\"array2\":\"olive oil\",\"array3nestedInAarray\":\"abc\",\"array3moreNestedInArrayletters\":\"xyz\",\"array3moreNestedInArraynumbres\":\"123\",\"array3nestedArrayInNestedArray1\":\"====\",\"array3nestedArrayInNestedArray2\":\"---\",\"array3nestedArrayInNestedArray3\":\"****\",\"array4\":\"salt\",\"retain\":\"I'm alive\"}";
        assertEquals(excepted, transform.execute());
    }
}