package br.org.enascimento.assembleiacooperados.red.domain.exception;

import br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException;

public class ValidaCpfException extends DomainException {
    public ValidaCpfException(Error error, Throwable cause) {
        super(error, cause);
    }
}
