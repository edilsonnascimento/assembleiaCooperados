package br.org.enascimento.assembleiacooperados.red.domain.exception;

import br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException;

public class CedulaNotExistentException extends DomainException {
    public CedulaNotExistentException(Error error) {
        super(error);
    }
}
