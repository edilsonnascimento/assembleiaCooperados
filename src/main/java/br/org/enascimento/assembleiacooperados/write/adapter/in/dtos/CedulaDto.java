package br.org.enascimento.assembleiacooperados.write.adapter.in.dtos;

import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record CedulaDto(
        @NotNull
        UUID uuidCedula,
        @NotNull
        UUID uuidSessao,
        @NotNull
        UUID uuidCooperado,
        @NotNull
        Voto voto) {
}
