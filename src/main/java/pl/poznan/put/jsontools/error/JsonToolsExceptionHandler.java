package pl.poznan.put.jsontools.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class JsonToolsExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(JsonToolsExceptionHandler.class);

    @ExceptionHandler(value = JsonToolsInvalidJsonError.class)
    protected ResponseEntity<Object> handleInvalidJson(JsonToolsInvalidJsonError error, WebRequest request) {
        String response = error.getMessage() + "\nin:\n" + error.get_json();

        logger.error("Invalid json." + response);

        return handleExceptionInternal(
                error,
                response,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String failed_fields = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " = " + error.getRejectedValue() + "\n")
                .collect(Collectors.joining());

        String response =
                "Invalid input.\n" +
                        "Expected input:\n" +
                        "    {\n" +
                        "        transforms: [...],\n" +
                        "        data: [...]\n" +
                        "    }\n" +
                        "Validation failed for fields:\n" +
                        failed_fields;

        return handleExceptionInternal(
                exception,
                response,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }
}

