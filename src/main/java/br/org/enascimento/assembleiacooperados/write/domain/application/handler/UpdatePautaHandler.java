package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadPautaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.UpdatePautaCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import br.org.enascimento.assembleiacooperados.write.domain.core.WritePautaRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.PautaNotExistentException;
import br.org.enascimento.assembleiacooperados.write.domain.exception.PautaUpdateInvalidException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.PAUTA_NOT_EXIST;
import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.PAUTA_NOT_UPDATE;

@Service
public class UpdatePautaHandler implements Handler<UpdatePautaCommand> {

    private final WritePautaRepository repositoryWrite;
    private final ReadPautaRepository repositoryRead;

    public UpdatePautaHandler(WritePautaRepository repositoryWrite, ReadPautaRepository repositoryRead) {
        this.repositoryWrite = repositoryWrite;
        this.repositoryRead = repositoryRead;
    }

    @Override
    public void handle(UpdatePautaCommand command) {
        repositoryWrite.update(commandAccurate(command));
    }

    private Pauta commandAccurate(UpdatePautaCommand command) {

        if (command.titulo() == null && command.descricao() == null)
            throw new PautaUpdateInvalidException(PAUTA_NOT_UPDATE);

        var pautaOptional = repositoryRead.findByUuid(command.uuid());

        if (!pautaOptional.isPresent())
            throw new PautaNotExistentException(PAUTA_NOT_EXIST);

        var pauta = pautaOptional.get();

        if(command.titulo() != null && command.descricao() == null) {
            pauta.setTitulo(command.titulo());
        }else if(command.descricao() != null && command.titulo() == null){
            pauta.setDescricao(command.descricao());
        }else{
            pauta
                .setTitulo(command.titulo())
                .setDescricao(command.descricao());
        }
        pauta.setUpdatedAt(LocalDateTime.now());
        return pauta;
    }
}
