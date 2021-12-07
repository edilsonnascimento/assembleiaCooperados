package br.org.enascimento.assembleiacooperados.write.domain.exception;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class DomainException extends RuntimeException{

    public enum Error{
        INVALID_DUPLICATE_DATA("Invalid duplicated data"),
        BUCKET_NOT_EXIST("Pauta not exist");

        private String message;
        Error(String message) {
            this.message = message;
        }
    }

    private Map<String, Object> errors = new LinkedHashMap<>();

    public DomainException(Error error){
        super(error.message);
    }
    public DomainException(Error error, Throwable cause) {
        super(error.message , cause);
    }

    public Map<String, Object> getErrors() {
        return errors;
    }

    public void addErrors(String key, Object value) {
        errors.put(key, value);
    }

    public boolean hasError() {
        return !errors.isEmpty();
    }
}
