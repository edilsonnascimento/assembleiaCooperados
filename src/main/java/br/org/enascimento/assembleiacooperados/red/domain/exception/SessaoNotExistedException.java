package br.org.enascimento.assembleiacooperados.red.domain.exception;

import br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException;

public class SessaoNotExistedException extends DomainException {
    public SessaoNotExistedException(Error error) {
        super(error);
    }
}
