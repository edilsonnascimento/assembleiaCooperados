package br.org.enascimento.assembleiacooperados.red.domain.exception;

import br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException;

public class StatusNotExistedException extends DomainException {
    public StatusNotExistedException(Error error) {
        super(error);
    }
}
