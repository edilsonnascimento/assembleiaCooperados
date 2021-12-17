package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCooperadoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.UpdateCooperadoCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCooperadoRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.CooperadoNotExistentException;
import br.org.enascimento.assembleiacooperados.write.domain.exception.CooperadoUpdateInvalidException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.COOPERADO_NOT_EXIST;
import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.COOPERADO_NOT_UPDATE;

@Service
public class UpdateCooperadoHandler implements Handler<UpdateCooperadoCommand> {

    private WriteCooperadoRepository repositoryWrite;
    private ReadCooperadoRepository repositoryRead;

    public UpdateCooperadoHandler(WriteCooperadoRepository repositoryWrite, ReadCooperadoRepository repositoryRead) {
        this.repositoryWrite = repositoryWrite;
        this.repositoryRead = repositoryRead;
    }

    @Override
    public void handle(UpdateCooperadoCommand command) {

        var cooperado = commandAccurate(command);

        repositoryWrite.update(cooperado);
    }

    private Cooperado commandAccurate(UpdateCooperadoCommand command) {

        if (command.nome() == null && command.cpf() == null)
            throw new CooperadoUpdateInvalidException(COOPERADO_NOT_UPDATE);

        var cooperadoOptional = repositoryRead.findByUuid(command.uuid());

        if (!cooperadoOptional.isPresent())
            throw new CooperadoNotExistentException(COOPERADO_NOT_EXIST);

        var cooperado = cooperadoOptional.get();

        if(command.nome() != null && command.cpf() == null) {
            cooperado.setNome(command.nome());
        }else if(command.cpf() != null && command.nome() == null){
            cooperado.setCpf(command.cpf());
        }else{
            cooperado.setNome(command.nome())
                    .setCpf(command.cpf());
        }
        cooperado.setUpdatedAt(LocalDateTime.now());

        return cooperado;
    }
}
