package br.org.enascimento.assembleiacooperados.write.domain.application;

import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import br.org.enascimento.assembleiacooperados.write.domain.core.PautaRepository;
import org.springframework.stereotype.Service;

@Service
public class CreatePautaCommandHandler implements Handler<CreatePautaCommand>{

    private PautaRepository repository;

    public CreatePautaCommandHandler(PautaRepository repository) {
        this.repository = repository;
    }

    public void handle(CreatePautaCommand pautaCommand) {
        var pauta = new Pauta()
                .setUuid(pautaCommand.uuid())
                .setTitulo(pautaCommand.titulo())
                .setDescricao(pautaCommand.descricao());
        repository.create(pauta);
    }
}
