package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.adapter.in.consummers.validacpf.ValidaCPFConsumer;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CedulaDto;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateCedulaCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCedulaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.CedualNotExistedExcepetion;
import br.org.enascimento.assembleiacooperados.write.domain.exception.ValidaCPFException;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.*;

@Service
public class CreateCedulaHandler implements Handler<CreateCedulaCommand>{

    private WriteCedulaRepository repository;

    private final ValidaCPFConsumer serverValidaCPF;

    public CreateCedulaHandler(WriteCedulaRepository repository, ValidaCPFConsumer serverValidaCPF) {
        this.repository = repository;
        this.serverValidaCPF = serverValidaCPF;
    }

    @Override
    public void handle(CreateCedulaCommand command) {
        var cedulaDto = new CedulaDto(command.uuidCedula(),
                                           command.uuidSessao(),
                                           command.uuidCooperado(),
                                             command.voto());
        var optionalCedulaInDto = repository.retrieveCedulaDto(cedulaDto);
        if(optionalCedulaInDto.isEmpty()) throw new CedualNotExistedExcepetion(URNA_NOT_EXIST);
        var dto = optionalCedulaInDto.get();
        //if(repository.isDuplicateVoto(dto)) throw new DuplicatedDataException(INVALID_DUPLICATE_DATA);
        if(serverValidaCPF.isAbleToVote(dto.getCpf())) throw new ValidaCPFException(CPF_INAVALID);
        repository.create(dto);
    }
}