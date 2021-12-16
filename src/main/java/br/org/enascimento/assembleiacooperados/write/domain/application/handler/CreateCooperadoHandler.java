package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateCooperadoCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCooperadoRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCooperadoHandler implements Handler<CreateCooperadoCommand> {

    private final WriteCooperadoRepository repository;

    public CreateCooperadoHandler(WriteCooperadoRepository repository) {
        this.repository = repository;
    }

    public void handle(CreateCooperadoCommand command) {

        var cooperado = new Cooperado()
                .setUuid(command.uuid())
                .setNome(command.nome())
                .setCpf(command.cpf());
        repository.create(cooperado);
    }
}
