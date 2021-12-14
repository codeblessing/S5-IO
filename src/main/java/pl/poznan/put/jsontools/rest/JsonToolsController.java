package pl.poznan.put.jsontools.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.jsontools.logic.JsonValidator;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api")
public class JsonToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JsonToolsController.class);

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public String get(@Valid @RequestBody JsonToolsRequest request) {
        logger.debug("Got request:");
        logger.debug(request.toString());

        for (String json : request.data) {
            JsonValidator.validate(json);
        }

        return "Hello";
    }

}


