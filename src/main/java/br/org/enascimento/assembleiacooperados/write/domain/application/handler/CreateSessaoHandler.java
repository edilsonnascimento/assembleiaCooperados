package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadPautaRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.SessaoInvalidaException;
import br.org.enascimento.assembleiacooperados.red.domain.exception.StatusNotExistedException;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateSessaoCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Sessao;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteSessaoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.STATUS_NOT_EXIST;

@Service
public class CreateSessaoHandler implements Handler<CreateSessaoCommand>{

    private static final Long UMA_HORA = 1L;
    private WriteSessaoRepository repository;
    private ReadPautaRepository repositoryRead;

    public CreateSessaoHandler(WriteSessaoRepository repository, ReadPautaRepository repositoryRead) {
        this.repository = repository;
        this.repositoryRead = repositoryRead;
    }

    @Override
    public void handle(CreateSessaoCommand command) {
        var idPauta = repositoryRead.findByUuid(command.dto().uuidPauta()).get().getId();
        var statusOptinal = repository.findStatus(1l);
        if(statusOptinal.isEmpty())
            throw new StatusNotExistedException(STATUS_NOT_EXIST);

        var sessao = new Sessao()
                .setUuid(command.dto().uuid())
                .setIdPauta(idPauta);

        var limeteSessao = command.dto().limiteSessao() != null? command.dto().limiteSessao() : 1l;
        sessao.setFimSessao(sessao.getInicioSessao().plusMinutes(limeteSessao));

        if(sessao.verificaLimite()) throw new SessaoInvalidaException(DomainException.Error.LIMIT_SESSAO);
        repository.create(sessao);
    }
}
