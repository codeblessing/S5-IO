package pl.poznan.put.jsontools.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class JsonToolsExceptionHandler extends ResponseEntityExceptionHandler {

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

