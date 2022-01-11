package br.org.enascimento.assembleiacooperados.write.adapter.in.dto;

import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record UrnaIntoDto(
        @NotNull
        UUID uuidUrna,
        @NotNull
        UUID uuidSessao,
        @NotNull
        UUID uuidCooperado,
        @NotNull
        Voto voto) {
}
