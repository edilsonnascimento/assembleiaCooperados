package br.org.enascimento.assembleiacooperados.red.domain.exception;

import br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException;

public class StatusNotExistedExcepetion extends DomainException {
    public StatusNotExistedExcepetion(Error error) {
        super(error);
    }
}
