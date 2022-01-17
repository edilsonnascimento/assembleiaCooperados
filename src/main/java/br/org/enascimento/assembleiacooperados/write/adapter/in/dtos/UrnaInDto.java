package br.org.enascimento.assembleiacooperados.write.adapter.in.dtos;

import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;

import java.util.UUID;

public class UrnaInDto {
    private UUID uuid;
    private Long idSessao;
    private Long idCooperado;
    private Voto voto;

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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getIdSessao() {
        return idSessao;
    }

    public void setIdSessao(Long idSessao) {
        this.idSessao = idSessao;
    }

    public Long getIdCooperado() {
        return idCooperado;
    }

    public void setIdCooperado(Long idCooperado) {
        this.idCooperado = idCooperado;
    }

    public Voto getVoto() {
        return voto;
    }

    public void setVoto(Voto voto) {
        this.voto = voto;
    }
}
