package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadStatusRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.StatusNotExistedException;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.UpdateStatusCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteStatusRepositoy;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.STATUS_NOT_EXIST;

@Service
public class UpdateStatusHandler implements Handler<UpdateStatusCommand>{

    private final WriteStatusRepositoy repositoy;
    private final ReadStatusRepository repositoyRead;

    public UpdateStatusHandler(WriteStatusRepositoy repositoy, ReadStatusRepository repositoyRead) {
        this.repositoy = repositoy;
        this.repositoyRead = repositoyRead;
    }

    @Override
    public void handle(UpdateStatusCommand command) {

        var statusOptional = repositoyRead.findById(command.id());

        if(!statusOptional.isPresent())
            throw new StatusNotExistedException(STATUS_NOT_EXIST);

        var status = statusOptional.get();
        status.setDescricao(command.descricao());
        repositoy.update(status);
    }
}
