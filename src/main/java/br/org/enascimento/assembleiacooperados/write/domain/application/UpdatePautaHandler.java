package br.org.enascimento.assembleiacooperados.write.domain.application;

import br.org.enascimento.assembleiacooperados.write.domain.core.WritePautaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.PautaNotExistentException;
import br.org.enascimento.assembleiacooperados.write.domain.exception.PautaUpdateInvalidException;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.PAUTA_NOT_EXIST;
import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.PAUTA_NOT_UPDATE;

@Service
public class UpdatePautaHandler implements Handler<UpdatePautaCommand> {

    private WritePautaRepository repository;

    public UpdatePautaHandler(WritePautaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(UpdatePautaCommand command) {

        var pautaOptional = repository.findByUuid(command.uuid());

        if (!pautaOptional.isPresent())
            throw new PautaNotExistentException(PAUTA_NOT_EXIST);

        var pauta = pautaOptional.get();

        if (command.titulo() == null && command.descricao() == null)
            throw new PautaUpdateInvalidException(PAUTA_NOT_UPDATE);

        if(command.titulo() != null && command.descricao() == null) {
            pauta.setTitulo(command.titulo());
        }else
            if(command.descricao() != null && command.titulo() == null){
            pauta.setDescricao(command.descricao());
            }else{
                pauta
                        .setTitulo(command.titulo())
                        .setDescricao(command.descricao());
            }

        repository.update(pauta);
    }
}