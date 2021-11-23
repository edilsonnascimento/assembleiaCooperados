package br.org.enascimento.assembleiacooperados.write.domain.application;

import java.util.UUID;

public record CreatePautaCommand(UUID uuid, String titulo, String descricao) {

}
