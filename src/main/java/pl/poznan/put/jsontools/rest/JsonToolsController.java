package pl.poznan.put.jsontools.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.jsontools.logic.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api")
public class JsonToolsController {
    private static final Logger _logger = LoggerFactory.getLogger(JsonToolsController.class);

    private final JsonTransformService transformService;

    public JsonToolsController(JsonTransformService transformService) {
        this.transformService = transformService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public String get(@Valid @RequestBody JsonToolsFullRequest request) {
        _logger.debug("Got request:\n" + request.toString());

        return transformService.transform(request);
    }


    @RequestMapping(value = "/remove-attributes", method = RequestMethod.GET, produces = "application/json")
    public String removeAttributes(@Validated @RequestBody JsonToolsSingleRequest request) {
        return transformService.removeAttributes(request);
    }


    @RequestMapping(value = "/retain-attributes", method = RequestMethod.GET, produces = "application/json")
    public String retainAttributes(@Validated @RequestBody JsonToolsSingleRequest request) {
        return transformService.retainAttributes(request);
    }


    @RequestMapping(value = "/flatten", method = RequestMethod.GET, produces = "application/json")
    public String flatten(@Validated @RequestBody JsonToolsSingleRequest request) {
        return transformService.flatten(request);
    }


    @RequestMapping(value = "/minify", method = RequestMethod.GET, produces = "application/json")
    public String minify(@Validated @RequestBody JsonToolsSingleRequest request) {
        return transformService.minify(request);
    }


    @RequestMapping(value = "/format", method = RequestMethod.GET, produces = "application/json")
    public String format(@Validated @RequestBody JsonToolsSingleRequest request) {
        return transformService.format(request);
    }


    @RequestMapping(value = "/sort", method = RequestMethod.GET, produces = "application/json")
    public String sortAttributes(@Validated @RequestBody JsonToolsSingleRequest request) {
        return transformService.sortAttributes(request);
    }


    @RequestMapping(value = "/count", method = RequestMethod.GET, produces = "application/json")
    public String countFieldsValues(@Validated @RequestBody JsonToolsSingleRequest request) {
        return transformService.countFieldsValues(request);
    }

}


