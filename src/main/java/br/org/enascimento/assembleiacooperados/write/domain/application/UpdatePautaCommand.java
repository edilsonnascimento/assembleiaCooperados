package br.org.enascimento.assembleiacooperados.write.domain.application;

import java.util.UUID;

public record UpdatePautaCommand(UUID uuid, String titulo, String descricao) implements Command{
}
