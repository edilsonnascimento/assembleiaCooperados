package br.org.enascimento.assembleiacooperados.write.adapter.in.dto;

import java.util.UUID;

public record PautaDto(UUID uuid, String titulo, String descricao) {
}
