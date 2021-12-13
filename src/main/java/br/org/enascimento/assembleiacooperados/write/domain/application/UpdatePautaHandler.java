package br.org.enascimento.assembleiacooperados.write.domain.application;

import br.org.enascimento.assembleiacooperados.write.domain.core.WritePautaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.PautaNotExistentException;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.BUCKET_NOT_EXIST;

@Service
public class UpdatePautaHandler implements Handler<UpdatePautaCommand> {

    private WritePautaRepository repository;

    public UpdatePautaHandler(WritePautaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(UpdatePautaCommand command) {

        var pautaOptional = repository.findByUuid(command.uuid());

        if(!pautaOptional.isPresent())
            throw new PautaNotExistentException(BUCKET_NOT_EXIST);

        var pauta = pautaOptional.get();

        pauta
            .setTitulo(command.titulo())
            .setDescricao(command.descricao());

        repository.update(pauta);;
    }
}
