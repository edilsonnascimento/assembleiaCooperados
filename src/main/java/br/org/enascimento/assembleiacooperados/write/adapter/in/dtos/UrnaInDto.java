package br.org.enascimento.assembleiacooperados.write.adapter.in.dtos;

import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;

import java.util.UUID;

public class UrnaInDto {
    private final UUID uuid;
    private final Long idSessao;
    private final Long idCooperado;
    private final Voto voto;

    public UrnaInDto(
            UUID uuid,
            Long idSessao,
            Long idCooperado,
            Voto voto
    ) {
        this.uuid = uuid;
        this.idSessao = idSessao;
        this.idCooperado = idCooperado;
        this.voto = voto;
    }

    public UUID uuid() {
        return uuid;
    }

    public Long idSessao() {
        return idSessao;
    }

    public Long idCooperado() {
        return idCooperado;
    }

    public Voto voto() {
        return voto;
    }
}
