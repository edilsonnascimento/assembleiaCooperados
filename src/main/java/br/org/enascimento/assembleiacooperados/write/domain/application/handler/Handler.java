package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.domain.application.command.Command;

public interface Handler<T extends Command> {
    void handle(T command);
}
