package br.org.enascimento.assembleiacooperados.write.domain.application.command;

import java.util.UUID;

public record CreatePautaCommand(
        UUID uuid,
        String titulo,
        String descricao) implements Command {
}
