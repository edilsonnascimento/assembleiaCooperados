package br.org.enascimento.assembleiacooperados.write.domain.exception;

public class DuplicatedDataException extends RuntimeException{

    public DuplicatedDataException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        var fieldName = "";
        var message = super.getMessage();

        if(message.contains("PAUTA(TITULO)"))
            fieldName = "titulo";

        if(message.contains("PAUTA(UUID)"))
            fieldName = "uuid";

        return "Invalid duplicated data: " + fieldName;
    }
}
