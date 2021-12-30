package br.org.enascimento.assembleiacooperados.red.domain.exception;

import br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException;

public class SessaoNotExistedExcepetion extends DomainException {
    public SessaoNotExistedExcepetion(Error error) {
        super(error);
    }
}
