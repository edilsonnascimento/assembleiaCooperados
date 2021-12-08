package br.org.enascimento.assembleiacooperados.write.domain.exception;

public class DuplicatedDataException extends DomainException{

    public DuplicatedDataException(Error error, Throwable cause) {
        super(error, cause);
    }

}
