package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.domain.core.ValidaCPF;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CedulaDto;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateCedulaCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCedulaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.CedualNotExistedExcepetion;
import br.org.enascimento.assembleiacooperados.write.domain.exception.ValidaCPFException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.*;

@Service
public class CreateCedulaHandler implements Handler<CreateCedulaCommand>{

    @Autowired
    private WriteCedulaRepository repository;
    @Autowired
    private ValidaCPF serverValidaCPF;

    @Override
    public void handle(CreateCedulaCommand command) {
        var cedulaDto = new CedulaDto(command.uuidCedula(),
                                      command.uuidSessao(),
                                      command.uuidCooperado(),
                                      command.voto());
        var optionalCedulaInDto = repository.retrieveCedulaDto(cedulaDto);
        if(optionalCedulaInDto.isEmpty())
            throw new CedualNotExistedExcepetion(CEDULA_INVALID);
        var eleitor = optionalCedulaInDto.get();

        if(serverValidaCPF.isAbleToVote(eleitor.getCpf()))
            throw new ValidaCPFException(CPF_INVALID);
        repository.create(eleitor);
    }
}