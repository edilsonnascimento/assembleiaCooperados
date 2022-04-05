package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadPautaRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.PautaNotExistentException;
import br.org.enascimento.assembleiacooperados.red.domain.exception.SessaoInvalidaException;
import br.org.enascimento.assembleiacooperados.red.domain.exception.StatusNotExistedException;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateSessaoCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Sessao;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteSessaoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.PAUTA_NOT_EXIST;
import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.STATUS_NOT_EXIST;

@Service
public class CreateSessaoHandler implements Handler<CreateSessaoCommand>{

    private static final Long UMA_HORA = 1L;
    @Autowired
    private WriteSessaoRepository repository;
    @Autowired
    private ReadPautaRepository repositoryRead;

    @Override
    public void handle(CreateSessaoCommand command) {
        var pautaOptional = repositoryRead.findByUuid(command.dto().uuidPauta());
        if(!pautaOptional.isPresent())
            throw new PautaNotExistentException(PAUTA_NOT_EXIST);
        var idPauta = pautaOptional.get().getId();

        var statusOptinal = repository.findStatus(1l);
        if(statusOptinal.isEmpty())
            throw new StatusNotExistedException(STATUS_NOT_EXIST);

        var sessao = new Sessao()
                .setUuid(command.dto().uuid())
                .setIdPauta(idPauta);

        var limeteSessao = command.dto().limiteSessao() != null? command.dto().limiteSessao() : UMA_HORA;
        sessao.setFimSessao(sessao.getInicioSessao().plusMinutes(limeteSessao));

        if(sessao.verificaLimite()) throw new SessaoInvalidaException(DomainException.Error.LIMIT_SESSAO);
        repository.create(sessao);
    }
}