package br.org.enascimento.assembleiacooperados.red.domain.exception;

import br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException;

public class CooperadoNotExistentException extends DomainException {

    public CooperadoNotExistentException(Error error) {
        super(error);
    }
}
