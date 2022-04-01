package br.org.enascimento.assembleiacooperados.write.domain.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class WriteExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler
    public ResponseEntity<Object> onHttpMessageNotReadableException(HttpMessageNotReadableException exception){

        return getResponseEntity("Malformed JSON", null);

    }
    @ExceptionHandler
    public ResponseEntity<Object> onDataIntegrityViolationException(DataIntegrityViolationException exception){
        var errors = List
                .of(new FieldValidationError(exception.getClass().getSimpleName(),exception.getMessage()));

        return getResponseEntity("invalid Request", errors);
    }
    @ExceptionHandler
    public ResponseEntity<Object> onHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception){
        var errors = List
                .of(new FieldValidationError(exception.getMethod(),exception.getMessage()));

        return getResponseEntity("invalid Request", errors);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

            var mapErrors = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        return getResponseEntity("invalid data", mapErrors);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> onDomainException(DomainException exception) {

        var errors = List.of(new FieldValidationError("code", valueOf(exception.getCode())));

        return getResponseEntity(exception.getMessage(), errors);
    }

    private ResponseEntity<Object> getResponseEntity(String message, List<FieldValidationError> detailedErrors) {

        Map<String, Object> errorResult = new HashMap<>(Map.of("message", message));

        if (detailedErrors != null && !detailedErrors.isEmpty())
            errorResult.put("errors", detailedErrors);

        if (logger.isWarnEnabled())
            logger.warn(errorResult.toString());

        return new ResponseEntity<>(errorResult, BAD_REQUEST);
    }

    record FieldValidationError(String field, String detail) {
    }
}