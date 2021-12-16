package br.org.enascimento.assembleiacooperados.write.domain.application.command;

import java.util.UUID;

public record CreateCooperadoCommand(UUID uuid, String nome, String cpf) implements Command {
}
