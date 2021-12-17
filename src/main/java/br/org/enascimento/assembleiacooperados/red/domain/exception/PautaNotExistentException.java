package br.org.enascimento.assembleiacooperados.red.domain.exception;

import br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException;

public class PautaNotExistentException extends DomainException {
    public PautaNotExistentException(Error error) {
        super(error);
    }
}
