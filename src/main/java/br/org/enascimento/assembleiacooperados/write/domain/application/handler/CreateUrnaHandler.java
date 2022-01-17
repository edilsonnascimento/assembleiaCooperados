package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.adapter.in.consummers.validacpf.ValidaCPFConsumer;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CedulaDto;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateUrnaCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteUrnaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.UrnaNotExistedExcepetion;
import br.org.enascimento.assembleiacooperados.write.domain.exception.ValidaCPFException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.CPF_INAVALID;
import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.URNA_NOT_EXIST;

@Service
public class CreateUrnaHandler implements Handler<CreateUrnaCommand>{

    private WriteUrnaRepository repository;

    private final ValidaCPFConsumer serverValidaCPF;

    public CreateUrnaHandler(WriteUrnaRepository repository, ValidaCPFConsumer serverValidaCPF) {
        this.repository = repository;
        this.serverValidaCPF = serverValidaCPF;
    }

    @Override
    public void handle(CreateUrnaCommand command) {
        var cedulaDto = new CedulaDto(command.uuidUrna(),
                                           command.uuidSessao(),
                                           command.uuidCooperado(),
                                             command.voto());
        var optionalUrnaInDto = repository.retrieveUrnaDto(cedulaDto);
        if(optionalUrnaInDto.isEmpty()) throw new UrnaNotExistedExcepetion(URNA_NOT_EXIST);
        var dto = optionalUrnaInDto.get();
        if(serverValidaCPF.isAbleToVote(dto.getCpf())) throw new ValidaCPFException(CPF_INAVALID);
        repository.create(dto);
    }
}