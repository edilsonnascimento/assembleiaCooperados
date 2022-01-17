package br.org.enascimento.assembleiacooperados.write.domain.exception;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class DomainException extends RuntimeException{

   private int code;

    public enum Error{
        INVALID_DUPLICATE_DATA("Invalid duplicated data", 1000),
        PAUTA_NOT_EXIST("Pauta not exist", 1001),
        PAUTA_NOT_UPDATE("Pauta not update", 1002),
        COOPERADO_NOT_UPDATE("Cooperado not update", 1003),
        COOPERADO_NOT_EXIST("Cooperado not exist", 1004),
        STATUS_NOT_EXIST("Status not exist", 1005),
        SESSAO_NOT_EXIST("Sessao not exist", 1006),
        URNA_NOT_EXIST("Urna not exist", 1007),
        CPF_INAVALID("invalid cpf for voting", 1008),
        UNABLE_TO_VOTE("Unable to vote", 1009);

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
