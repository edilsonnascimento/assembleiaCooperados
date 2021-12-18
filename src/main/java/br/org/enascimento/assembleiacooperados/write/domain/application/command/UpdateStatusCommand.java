package br.org.enascimento.assembleiacooperados.write.domain.application.command;

public record UpdateStatusCommand(Long id, String descricao) implements Command {
}
