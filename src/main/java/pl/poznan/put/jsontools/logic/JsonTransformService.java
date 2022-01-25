package pl.poznan.put.jsontools.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.poznan.put.jsontools.rest.JsonToolsFullRequest;
import pl.poznan.put.jsontools.rest.JsonToolsSingleRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JsonTransformService {

    private static final Logger _logger = LoggerFactory.getLogger(JsonTransformService.class);

    public String transform(JsonToolsFullRequest request) {

        _logger.info("Processing transforms.");

        List<String> altered = new ArrayList<>();
        for (var json : request.data) {
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
                        break;
                    case "flatten":
                        _logger.debug("Flatten transform added");
                        transform = new JsonTransformFlatten(baseTransform);
                        baseTransform = transform;
                        break;
                    case "sort":
                        _logger.debug("Sort transform added");
                        transform = new JsonTransformSortFields(baseTransform);
                        baseTransform = transform;
                        break;
                    case "count":
                        _logger.debug("Count transform added");
                        transform = new JsonTransformCountFieldsValues(baseTransform);
                        baseTransform = transform;
                        break;
                    case "delete-nulls":
                        _logger.debug("Delete nulls transform added");
                        transform = new JsonTransformDeleteNulls(baseTransform);
                        baseTransform = transform;
                        break;
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
            return "[\n\t" + altered.stream()
                    .map(str -> str.replace("\n", "\n\t"))
                    .collect(Collectors.joining(",\n\t")) + "\n]";
        }
    }

    public String removeAttributes(JsonToolsSingleRequest request) {
        var transform = new JsonTransformRemoveAttributes(new JsonBase(request.data.toString()), request.attributes);
        return transform.execute();
    }

    public String retainAttributes(JsonToolsSingleRequest request) {
        var transform = new JsonTransformRetainAttributes(new JsonBase(request.data.toString()), request.attributes);
        return transform.execute();
    }

    public String flatten(JsonToolsSingleRequest request) {
        var transform = new JsonTransformFlatten(new JsonBase(request.data.toString()));
        return transform.execute();
    }

    public String minify(JsonToolsSingleRequest request) {
        var transform = new JsonTransformMinify(new JsonBase(request.data.toString()));
        return transform.execute();
    }

    public String format(JsonToolsSingleRequest request) {
        var transform = new JsonTransformFormat(new JsonBase(request.data.toString()));
        return transform.execute();
    }

    public String sortAttributes(JsonToolsSingleRequest request) {
        var transform = new JsonTransformSortFields(new JsonBase(request.data.toString()));
        return transform.execute();
    }

    public String countFieldsValues(JsonToolsSingleRequest request) {
        var transform = new JsonTransformCountFieldsValues(new JsonBase(request.data.toString()));
        return transform.execute();
    }

    public String deleteNulls(JsonToolsSingleRequest request) {
        var transform = new JsonTransformDeleteNulls(new JsonBase(request.data.toString()));
        return transform.execute();
    }


}
