package br.org.enascimento.assembleiacooperados.write.domain.application.command;

import java.util.UUID;

public record UpdateCooperadoCommand(UUID uuid, String nome, String cpf) implements Command {
}
