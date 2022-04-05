package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreatePautaCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import br.org.enascimento.assembleiacooperados.write.domain.core.WritePautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreatePautaHandler implements Handler<CreatePautaCommand> {

    @Autowired
    private WritePautaRepository repository;

    public void handle(CreatePautaCommand pautaCommand) {
        var pauta = new Pauta()
                .setUuid(pautaCommand.uuid())
                .setTitulo(pautaCommand.titulo())
                .setDescricao(pautaCommand.descricao());
        repository.create(pauta);
    }
}