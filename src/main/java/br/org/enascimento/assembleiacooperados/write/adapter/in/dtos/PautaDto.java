package br.org.enascimento.assembleiacooperados.write.adapter.in.dtos;

import java.util.UUID;

public record PautaDto(UUID uuid, String titulo, String descricao) {
}
