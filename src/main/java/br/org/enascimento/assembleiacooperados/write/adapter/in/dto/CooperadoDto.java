package br.org.enascimento.assembleiacooperados.write.adapter.in.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public record CooperadoDto(
        @NotNull
        UUID uuid,

        @Size(min = 5, max = 100, message = "About Me must be between 5 and 200 characters")
        @NotNull
        String nome,

        @NotBlank
        String cpf) {
}