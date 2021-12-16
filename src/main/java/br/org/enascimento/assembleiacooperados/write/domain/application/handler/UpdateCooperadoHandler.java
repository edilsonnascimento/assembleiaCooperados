package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.domain.application.command.UpdateCooperadoCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCooperadoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.CooperadoUpdateInvalidException;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.COOPERADO_NOT_UPDATE;

@Service
public class UpdateCooperadoHandler implements Handler<UpdateCooperadoCommand> {

    private WriteCooperadoRepository repository;

    public UpdateCooperadoHandler(WriteCooperadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(UpdateCooperadoCommand command) {

        if(command.cpf() == null && command.nome() == null)
            throw new CooperadoUpdateInvalidException(COOPERADO_NOT_UPDATE);

        var cooperado = repository.findByUuidOrCpf(command.uuid(), command.cpf()).get();

        cooperado
                .setUuid(command.uuid())
                .setNome(command.nome())
                .setCpf(command.cpf());

        repository.update(cooperado);

    }
}
