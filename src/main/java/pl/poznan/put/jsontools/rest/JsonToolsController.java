package pl.poznan.put.jsontools.rest;

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
import java.util.stream.Stream;

@Validated
@RestController
@RequestMapping("/api")
public class JsonToolsController {
    private static final Logger _logger = LoggerFactory.getLogger(JsonToolsController.class);
    private static final ObjectMapper _mapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public String get(@Valid @RequestBody JsonToolsFullRequest request) {

        _logger.debug("Got request:\n" + request.toString());
        _logger.info("Processing transforms.");

        List<String> altered = new ArrayList<>();
        for (var json : request.data) {
//            try {
//                _mapper.readTree(json);
//            } catch (JsonProcessingException e) {
//                _logger.error("Invalid JSON:\n" + json);
//                throw new JsonToolsInvalidJsonError(e.getLocation().toString(), json);
//            }

            JsonTransform transform = new JsonBase(json.toString());
            JsonTransform baseTransform = transform;
            for (var tform : request.transforms) {
                switch (tform.name) {
                    case "remove-attributes":
                        _logger.debug("Remove transform added");
                        transform = new JsonTransformRemoveAttributes(baseTransform, tform.attributes);
                        baseTransform = transform;
                        break;
                    case "retain-attributes":
                        _logger.debug("Retain transform added");
                        transform = new JsonTransformRetainAttributes(baseTransform, tform.attributes);
                        baseTransform = transform;
                        break;
                    case "minify":
                        _logger.debug("Minify transform added");
                        transform = new JsonTransformMinify(baseTransform);
                        baseTransform = transform;
                        break;
                    case "format":
                        _logger.debug("Format transform added");
                        transform = new JsonTransformFormat(baseTransform);
                        baseTransform = transform;
                    default:
                        _logger.warn("No such transform: " + tform.name);
                        break;
                }

            }
            altered.add(transform.execute());
        }


        if (altered.size() == 1) {
            return altered.get(0);
        } else {
            return "[\n\t" + altered.stream().map(str -> str.replace("\n", "\n\t")).collect(Collectors.joining(",\n\t")) + "\n]";
        }

    }

    @RequestMapping(value = "/remove-attributes", method = RequestMethod.GET, produces = "application/json")
    public String removeAttributes(@Validated @RequestBody JsonToolsSingleRequest request) {
        var transform = new JsonTransformRemoveAttributes(new JsonBase(request.data.toString()), request.attributes);
        return transform.execute();
    }

    @RequestMapping(value = "/retain-attributes", method = RequestMethod.GET, produces = "application/json")
    public String retainAttributes(@Validated @RequestBody JsonToolsSingleRequest request) {
        var transform = new JsonTransformRetainAttributes(new JsonBase(request.data.toString()), request.attributes);
        return transform.execute();
    }

    @RequestMapping(value = "/minify", method = RequestMethod.GET, produces = "application/json")
    public String minify(@Validated @RequestBody JsonToolsSingleRequest request) {
        var transform = new JsonTransformMinify(new JsonBase(request.data.toString()));
        return transform.execute();
    }

    @RequestMapping(value = "/format", method = RequestMethod.GET, produces = "application/json")
    public String format(@Validated @RequestBody JsonToolsSingleRequest request) {
        var transform = new JsonTransformFormat(new JsonBase(request.data.toString()));
        return transform.execute();
    }
}


