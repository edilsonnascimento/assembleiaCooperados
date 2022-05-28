package br.org.enascimento.assembleiacooperados.write.adapter.in.dtos;

import br.org.enascimento.assembleiacooperados.write.domain.core.Operadora;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ContatoDTO(
        @NotNull
        BigDecimal telefone,
        @NotNull
        Operadora operadora,
        @NotEmpty
        String nomeContato,
        @NotEmpty
        String codigo) {
}
