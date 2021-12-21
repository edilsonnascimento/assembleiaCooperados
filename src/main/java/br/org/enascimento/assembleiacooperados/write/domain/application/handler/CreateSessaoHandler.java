package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadPautaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateSessaoCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Sessao;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteSessaoRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateSessaoHandler implements Handler<CreateSessaoCommand>{

    private WriteSessaoRepository repository;
    private ReadPautaRepository repositoryRead;

    public CreateSessaoHandler(WriteSessaoRepository repository, ReadPautaRepository repositoryRead) {
        this.repository = repository;
        this.repositoryRead = repositoryRead;
    }

    @Override
    public void handle(CreateSessaoCommand command) {
        var idPauta = repositoryRead.findByUuid(command.dto().uuidPauta()).get().getId();
        var sessao = new Sessao()
                .setUuid(command.dto().uuid())
                .setIdPauta(idPauta);

        repository.create(sessao);
    }
}
