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
import pl.poznan.put.jsontools.rest.JsonToolsController;
import pl.poznan.put.jsontools.rest.JsonToolsFullRequest;
import pl.poznan.put.jsontools.rest.JsonToolsRequestTransform;

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


        verify(jsonTransformService, times(2)).transform(
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

    public JsonToolsFullRequest createJsonToolsFullRequest() throws JsonProcessingException {

        JsonToolsFullRequest jsonToolsFullRequest = new JsonToolsFullRequest();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree("{\"random\":28,\"regEx\":\"hellooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo to you\"}");

        JsonToolsRequestTransform jsonToolsRequestTransform = new JsonToolsRequestTransform();

        jsonToolsRequestTransform.name = "format";
        jsonToolsRequestTransform.attributes = List.of();

        jsonToolsFullRequest.data = List.of(jsonNode);
        jsonToolsFullRequest.transforms = List.of(jsonToolsRequestTransform);

        return jsonToolsFullRequest;
    }


}
