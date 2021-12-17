package pl.poznan.put.jsontools.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.jsontools.logic.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api")
public class JsonToolsController {
    private static final Logger logger = LoggerFactory.getLogger(JsonToolsController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String[] get(@Valid @RequestBody JsonToolsFullRequest request) {
        logger.debug("Got request:\n" + request.toString());

        logger.info("Processing transforms.");

        var transformer = new JsonTransformer();

        for (var transform : request.transforms) {
            switch (transform.name) {
                case "remove-attributes":
                    transformer.addTransform(new JsonTransformRemoveAttributes(transform.attributes));
                    break;
                case "retain-attributes":
                    transformer.addTransform(new JsonTransformRetainAttributes(transform.attributes));
                    break;
                case "minify":
                    transformer.addFinalTransform(new JsonTransformMinify());
                    break;
                case "format":
                    transformer.addFinalTransform(new JsonTransformFormat());
                default:
                    break;
            }
        }

        logger.info("Processing JSON data");
        return request.data.parallelStream().map(transformer::execute).toArray(String[]::new);
    }

    @RequestMapping(value = "/remove-attributes", method = RequestMethod.POST, produces = "application/json")
    public String removeAttributes(@Validated @RequestBody JsonToolsSingleRequest request) {
        var transform = new JsonTransformRemoveAttributes(request.attributes);

        return transform.execute(request.data).toString();
    }
}


