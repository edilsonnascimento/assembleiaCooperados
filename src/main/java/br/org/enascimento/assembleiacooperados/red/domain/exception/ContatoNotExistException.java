package br.org.enascimento.assembleiacooperados.red.domain.exception;

import br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException;

public class ContatoNotExistException extends DomainException {

    protected ContatoNotExistException(Error error) {
        super(error);
    }
}
