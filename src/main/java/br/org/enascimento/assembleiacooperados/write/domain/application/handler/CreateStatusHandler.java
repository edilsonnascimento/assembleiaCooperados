package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.adapter.out.WriteStatusRepositoryImpl;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateStatusCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateStatusHandler implements Handler<CreateStatusCommand>{

    @Autowired
    private WriteStatusRepositoryImpl repository;

    @Override
    public void handle(CreateStatusCommand command) {
        var status = new Status().setDescricao(command.descricao());
        repository.create(status);
    }
}
