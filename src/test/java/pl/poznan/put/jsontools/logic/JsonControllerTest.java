package pl.poznan.put.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.poznan.put.jsontools.error.JsonToolsInvalidJsonError;
import pl.poznan.put.jsontools.rest.JsonToolsController;
import pl.poznan.put.jsontools.rest.JsonToolsFullRequest;
import pl.poznan.put.jsontools.rest.JsonToolsRequestTransform;
import pl.poznan.put.jsontools.rest.JsonToolsSingleRequest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {pl.poznan.put.jsontools.app.JsonToolsApplication.class})
@AutoConfigureMockMvc
public class JsonControllerTest {

    @Autowired
    private JsonToolsController jsonToolsController;

    @MockBean
    private JsonTransformService jsonTransformService;

    @Test
    public void basicTransformTest() throws JsonProcessingException {
        jsonToolsController.get(createJsonToolsFullRequest());

        verify(jsonTransformService, times(1)).transform(
                any()
        );

    }

    @Test
    public void checkOrderTest() {
        JsonBase jsonBase = spy(new JsonBase(""));
        JsonTransformSortFields jsonTransformSortFields = spy(new JsonTransformSortFields(jsonBase));
        JsonTransformFormat jsonTransformFormat = spy(new JsonTransformFormat(jsonTransformSortFields));

        InOrder inOrder = inOrder(jsonTransformFormat, jsonTransformSortFields, jsonBase);
        jsonTransformFormat.execute();
        inOrder.verify(jsonTransformFormat).execute();
        inOrder.verify(jsonTransformSortFields).execute();
        inOrder.verify(jsonBase).execute();

    }

    @Test
    public void invocationRemoveAttributesTest() throws JsonProcessingException {
        jsonToolsController.removeAttributes(createJsonToolsSingleRequest());
        verify(jsonTransformService, times(1)).removeAttributes(
                any()
        );
    }

    @Test
    public void invocationRetainAttributesTest() throws JsonProcessingException {
        jsonToolsController.retainAttributes(createJsonToolsSingleRequest());
        verify(jsonTransformService, times(1)).retainAttributes(
                any()
        );
    }

    @Test
    public void invocationFlattenTest() throws JsonProcessingException {
        jsonToolsController.flatten(createJsonToolsSingleRequest());
        verify(jsonTransformService, times(1)).flatten(
                any()
        );
    }

    @Test
    public void invocationMinifyTest() throws JsonProcessingException {
        jsonToolsController.minify(createJsonToolsSingleRequest());
        verify(jsonTransformService, times(1)).minify(
                any()
        );
    }

    @Test
    public void invocationFormatTest() throws JsonProcessingException {
        jsonToolsController.format(createJsonToolsSingleRequest());
        verify(jsonTransformService, times(1)).format(
                any()
        );
    }

    @Test
    public void invocationSortAttributesTest() throws JsonProcessingException {
        jsonToolsController.sortAttributes(createJsonToolsSingleRequest());
        verify(jsonTransformService, times(1)).sortAttributes(
                any()
        );
    }

    @Test
    public void invocationCountFieldsValuesTest() throws JsonProcessingException {
        jsonToolsController.countFieldsValues(createJsonToolsSingleRequest());
        verify(jsonTransformService, times(1)).countFieldsValues(
                any()
        );
    }

    @Test
    public void invocationNeverCountFieldsValuesTest() throws JsonProcessingException {
        jsonToolsController.sortAttributes(createJsonToolsSingleRequest());
        verify(jsonTransformService, never()).countFieldsValues(
                any()
        );
    }

    @Test
    public void throwJsonProcessingExceptionTest() throws JsonProcessingException {
        doThrow(JsonToolsInvalidJsonError.class).when(jsonTransformService).sortAttributes(createJsonToolsSingleRequest());
    }


    public JsonToolsFullRequest createJsonToolsFullRequest() throws JsonProcessingException {

        JsonToolsFullRequest jsonToolsFullRequest = new JsonToolsFullRequest();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree("\"{random\":28,\"regEx\":\"hellooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo to you\"}");

        JsonToolsRequestTransform jsonToolsRequestTransform = new JsonToolsRequestTransform();

        jsonToolsRequestTransform.name = "format";
        jsonToolsRequestTransform.attributes = List.of();

        jsonToolsFullRequest.data = List.of(jsonNode);
        jsonToolsFullRequest.transforms = List.of(jsonToolsRequestTransform);

        return jsonToolsFullRequest;
    }

    public JsonToolsSingleRequest createJsonToolsSingleRequest() throws JsonProcessingException {

        JsonToolsSingleRequest jsonToolsSingleRequest = new JsonToolsSingleRequest();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree("{\"random\":28,\"regEx\":\"hellooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo to you\"}");

        jsonToolsSingleRequest.attributes = List.of();
        jsonToolsSingleRequest.data = jsonNode;

        return jsonToolsSingleRequest;
    }
    
}
