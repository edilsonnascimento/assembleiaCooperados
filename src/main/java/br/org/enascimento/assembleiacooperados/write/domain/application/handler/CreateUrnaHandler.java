package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.UrnaIntoDto;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateUrnaCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteUrnaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.UrnaNotExistedExcepetion;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.URNA_NOT_EXIST;

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
        var optionalUrnaInDto = repository.retrieveUrnaDto(dtoParametro);
        if(optionalUrnaInDto.isEmpty()) throw new UrnaNotExistedExcepetion(URNA_NOT_EXIST);
        var dto = optionalUrnaInDto.get();
        repository.create(dto);
    }
}
