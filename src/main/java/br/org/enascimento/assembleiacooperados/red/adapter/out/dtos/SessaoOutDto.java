package br.org.enascimento.assembleiacooperados.red.adapter.out.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record SessaoOutDto(
        UUID uuid,
        BigDecimal quantidadeParticipantes,
        String tituloPauta,
        String descricaoPauta,
        LocalDateTime dataInicioSessao,
        LocalDateTime dataFimSessao,
        BigDecimal quantiadeVotosFavoraveis,
        BigDecimal quantiadeVotosContra,
        String estadoSessao) {
}
