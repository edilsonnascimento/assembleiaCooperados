package br.org.enascimento.assembleiacooperados.write.domain.exception;

public abstract class DomainException extends RuntimeException{

    public enum Error{
        INVALID_DUPLICATE_DATA("Invalid duplicated data");
        private String message;
        Error(String message) {
            this.message = message;
        }
    }

    public DomainException(Error error, Throwable cause) {
        super(error.message, cause);
    }
}
