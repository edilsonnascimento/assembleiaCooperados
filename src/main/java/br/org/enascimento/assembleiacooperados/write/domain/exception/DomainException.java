package br.org.enascimento.assembleiacooperados.write.domain.exception;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class DomainException extends RuntimeException{

   private int code;

    public enum Error{
        INVALID_DUPLICATE_DATA("Invalid duplicated data", 1000),
        BUCKET_NOT_EXIST("Pauta not exist", 1001);

        private String message;
        private int code;
        Error(String message, int code) {
            this.message = message;
            this.code = code;
        }
    }

    private Map<String, Object> errors = new LinkedHashMap<>();

    public DomainException(Error error){
        super(error.message);
        code = error.code;

    }
    public DomainException(Error error, Throwable cause) {
        super(error.message , cause);
        code = error.code;
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

    public int getCode() {
        return code;
    }
}
