package br.org.enascimento.assembleiacooperados.write.adapter.in.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public record CooperadoDto(
        @NotNull
        UUID uuid,

        @Size(min = 5, max = 100)
        @NotNull
        String nome,

        @NotNull
        @Size(min = 11, max = 11, message = "Size must be 11")
        String cpf) {
}
