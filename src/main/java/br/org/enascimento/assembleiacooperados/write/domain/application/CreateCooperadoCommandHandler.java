package br.org.enascimento.assembleiacooperados.write.domain.application;

import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCooperadoRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCooperadoCommandHandler {

    private final WriteCooperadoRepository repository;

    public CreateCooperadoCommandHandler(WriteCooperadoRepository repository) {
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
