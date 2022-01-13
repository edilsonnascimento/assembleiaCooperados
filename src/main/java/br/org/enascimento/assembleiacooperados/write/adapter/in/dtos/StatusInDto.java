package br.org.enascimento.assembleiacooperados.write.adapter.in.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record StatusInDto(
        @NotNull @NotBlank @NotEmpty
        String descricao){
}
