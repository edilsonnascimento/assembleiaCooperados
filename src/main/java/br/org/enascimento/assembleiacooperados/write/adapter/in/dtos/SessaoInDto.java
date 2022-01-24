package br.org.enascimento.assembleiacooperados.write.adapter.in.dtos;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record SessaoInDto(
        @NotNull
        UUID uuid,
        @NotNull
        UUID uuidPauta,
        @NotNull
        Long limiteSessao) {
}
