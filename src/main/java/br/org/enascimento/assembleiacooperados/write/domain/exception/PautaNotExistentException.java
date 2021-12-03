package br.org.enascimento.assembleiacooperados.write.domain.exception;

public class PautaNotExistentException extends  DomainException{
    public PautaNotExistentException(Error error) {
        super(error);
    }
}
