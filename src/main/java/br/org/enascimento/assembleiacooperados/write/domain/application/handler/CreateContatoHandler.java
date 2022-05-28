package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.ContatoDTO;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateContatoCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateContatoHandler implements Handler<CreateContatoCommand>{

    @Autowired
    private WriteContatoRepository repository;

    @Override
    public void handle(CreateContatoCommand command) {
        var dto = new ContatoDTO(command.getTelefone(), command.getOperadora(), command.getNome(), command.getCodigo());
        repository.create(dto);
    }
}