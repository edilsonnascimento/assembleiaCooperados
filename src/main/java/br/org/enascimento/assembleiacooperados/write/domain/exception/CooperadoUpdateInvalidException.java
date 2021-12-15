package br.org.enascimento.assembleiacooperados.write.domain.exception;

public class CooperadoUpdateInvalidException extends DomainException{

    public CooperadoUpdateInvalidException(Error error) {
        super(error);
    }
}
