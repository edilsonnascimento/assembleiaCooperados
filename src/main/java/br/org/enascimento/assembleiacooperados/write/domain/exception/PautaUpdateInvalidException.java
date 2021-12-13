package br.org.enascimento.assembleiacooperados.write.domain.exception;

public class PautaUpdateInvalidException extends  DomainException{
    public PautaUpdateInvalidException(Error error) {
        super(error);
    }
}
