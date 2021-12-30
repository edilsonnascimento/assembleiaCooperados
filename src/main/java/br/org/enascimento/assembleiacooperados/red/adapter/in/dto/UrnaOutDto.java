package br.org.enascimento.assembleiacooperados.red.adapter.in.dto;

import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;

import java.time.LocalDateTime;
import java.util.UUID;

public class UrnaOutDto{

        private UUID uuidUrna;
        private UUID uuidSessao;
        private UUID uuidCooperado;
        private Voto voto;
        private LocalDateTime dataVoto;

    public UUID getUuidUrna() {
        return uuidUrna;
    }

    public UrnaOutDto setUuidUrna(UUID uuidUrna) {
        this.uuidUrna = uuidUrna;
        return this;
    }

    public UUID getUuidSessao() {
        return uuidSessao;
    }

    public UrnaOutDto setUuidSessao(UUID uuidSessao) {
        this.uuidSessao = uuidSessao;
        return this;
    }

    public UUID getUuidCooperado() {
        return uuidCooperado;
    }

    public UrnaOutDto setUuidCooperado(UUID uuidCooperado) {
        this.uuidCooperado = uuidCooperado;
        return this;
    }

    public Voto getVoto() {
        return voto;
    }

    public UrnaOutDto setVoto(Voto voto) {
        this.voto = voto;
        return this;
    }

    public LocalDateTime getDataVoto() {
        return dataVoto;
    }

    public UrnaOutDto setDataVoto(LocalDateTime dataVoto) {
        this.dataVoto = dataVoto;
        return this;
    }
}
