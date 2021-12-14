package br.org.enascimento.assembleiacooperados.write.domain.application;

import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCooperadoRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateCooperadoHandler implements Handler<UpdateCooperadoCommand> {

    private WriteCooperadoRepository repository;

    public UpdateCooperadoHandler(WriteCooperadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(UpdateCooperadoCommand command) {
        var cooperado = repository.findByUuidOrCpf(command.uuid(), null).get();

        cooperado
                .setUuid(command.uuid())
                .setNome(command.nome())
                .setCpf(command.cpf());

        repository.update(cooperado);

    }
}
