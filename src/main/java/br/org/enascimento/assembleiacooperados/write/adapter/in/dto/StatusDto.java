package br.org.enascimento.assembleiacooperados.write.adapter.in.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record StatusDto(
        @NotNull
        @Size(min = 3, max = 50)
        String descricao) {
}
