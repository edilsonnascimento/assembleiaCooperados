package br.org.enascimento.assembleiacooperados.write.domain.application;

import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import br.org.enascimento.assembleiacooperados.write.domain.core.WritePautaRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdatePautaCommandHandler implements Handler<UpdatePautaCommand> {

    private WritePautaRepository repository;

    public UpdatePautaCommandHandler(WritePautaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(UpdatePautaCommand command) {

        var pauta = repository.findByUuid(command.uuid()).get();

        pauta
            .setTitulo(command.titulo())
            .setDescricao(command.descricao());

        repository.update(pauta);;
    }
}
