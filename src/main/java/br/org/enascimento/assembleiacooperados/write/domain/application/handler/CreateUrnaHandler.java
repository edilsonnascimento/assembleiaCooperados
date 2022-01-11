package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.UrnaIntoDto;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateUrnaCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteUrnaRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateUrnaHandler implements Handler<CreateUrnaCommand>{

    private WriteUrnaRepository repository;

    public CreateUrnaHandler(WriteUrnaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(CreateUrnaCommand command) {
        var dtoParametro = new UrnaIntoDto(command.uuidUrna(),
                                           command.uuidSessao(),
                                           command.uuidCooperado(),
                                           command.voto());
        var dto = repository.retrieveUrnaDto(dtoParametro).get();
        repository.create(dto);
    }
}
