package br.org.enascimento.assembleiacooperados.write.adapter.in;

import br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
public class WriteExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> onMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {

        var errors = List
                .of(new FieldValidationError(exception.getName(), exception.getValue().toString()));

        return getResponseEntity(exception.getMessage(), errors, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        List<FieldValidationError> mapErrors = exception
                .getBindingResult()
                .getFieldErrors()
                .stream().map(fieldError -> {
                    return new FieldValidationError(fieldError.getField(), fieldError.getDefaultMessage());
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(mapErrors, BAD_REQUEST);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> onDomainException(DomainException exception) {

        var errors = List.of(new FieldValidationError("code", valueOf(exception.getCode())));

        return getResponseEntity(exception.getMessage(), errors, BAD_REQUEST);
    }

    private ResponseEntity<Object> getResponseEntity(String message, List<FieldValidationError> detailedErrors, HttpStatus status) {

        Map<String, Object> errorResult = new HashMap<>(Map.of("message", message));

        if (detailedErrors != null && !detailedErrors.isEmpty())
            errorResult.put("errors", detailedErrors);

        if (logger.isWarnEnabled())
            logger.warn(errorResult.toString());

        return new ResponseEntity<>(errorResult, status);
    }

    static class FieldValidationError {

        private final String field;
        private final String detail;

        public FieldValidationError(String field, String detail) {
            this.field = field;
            this.detail = detail;
        }

        public String getField() {
            return field;
        }

        public String getDetail() {
            return detail;
        }

        @Override
        public String toString() {
            return "field='" + field + '\'' +
                    ", detail='" + detail;
        }
    }
}
