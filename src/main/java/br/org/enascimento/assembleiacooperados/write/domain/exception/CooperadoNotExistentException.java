package br.org.enascimento.assembleiacooperados.write.domain.exception;

public class CooperadoNotExistentException extends DomainException {

    public CooperadoNotExistentException(Error error) {
        super(error);
    }
}
