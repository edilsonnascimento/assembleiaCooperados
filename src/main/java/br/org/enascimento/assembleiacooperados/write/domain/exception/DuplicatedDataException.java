package br.org.enascimento.assembleiacooperados.write.domain.exception;

import java.util.LinkedHashMap;
import java.util.Map;

public class DuplicatedDataException extends DomainException{

    private Map<String, Object> errors = new LinkedHashMap<>();

    public DuplicatedDataException(Error message, Throwable exception) {
        super(message, exception);
    }

    public Map<String, Object> getErrors() {
        return errors;
    }

    public void addErrors(String key, Object value) {
        errors.put(key, value);
    }
}
