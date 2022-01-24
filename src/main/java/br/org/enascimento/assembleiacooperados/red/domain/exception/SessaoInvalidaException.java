package br.org.enascimento.assembleiacooperados.red.domain.exception;

import br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException;

public class SessaoInvalidaException extends DomainException {

    public SessaoInvalidaException(Error error) {
        super(error);
    }
}
