package br.org.enascimento.assembleiacooperados.write.adapter.in;

import br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException;
import br.org.enascimento.assembleiacooperados.write.domain.exception.DuplicatedDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class WriteExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public ResponseEntity<Object> onDuplicatedDataException(DomainException exception) {
               List<FieldValidationError> errors = new ArrayList<>();
                exception
                    .getErrors()
                    .forEach((k, v) -> errors.add(new FieldValidationError(k, v.toString())));
               return getResponseEntity(exception.getMessage(), errors, BAD_REQUEST);
    }

    private ResponseEntity<Object> getResponseEntity(String message, List<FieldValidationError> detailedErrors, HttpStatus status) {

        Map<String, Object> errorResult = new HashMap<>(Map.of("message", message));

        if (detailedErrors != null && !detailedErrors.isEmpty())
            errorResult.put("errors", detailedErrors);

        if(logger.isWarnEnabled())
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
    }
}
