package br.org.enascimento.assembleiacooperados.write.domain.exception;

public class ContatoExcepetion extends DomainException{
    public ContatoExcepetion(Error error, Throwable cause) {
        super(error, cause);
    }
    public ContatoExcepetion(Error error){
        super(error);
    }
}