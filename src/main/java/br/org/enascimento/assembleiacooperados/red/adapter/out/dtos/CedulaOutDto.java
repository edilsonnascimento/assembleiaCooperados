package br.org.enascimento.assembleiacooperados.red.adapter.out.dtos;

import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;

import java.time.LocalDateTime;
import java.util.UUID;

public class CedulaOutDto {

    private UUID uuidCedula;
    private UUID uuidSessao;
    private UUID uuidCooperado;
    private Voto voto;
    private LocalDateTime dataVoto;

    public UUID getUuidCedula() {
        return uuidCedula;
    }

    public CedulaOutDto setUuidCedula(UUID uuidCedula) {
        this.uuidCedula = uuidCedula;
        return this;
    }

    public UUID getUuidSessao() {
        return uuidSessao;
    }

    public CedulaOutDto setUuidSessao(UUID uuidSessao) {
        this.uuidSessao = uuidSessao;
        return this;
    }

    public UUID getUuidCooperado() {
        return uuidCooperado;
    }

    public CedulaOutDto setUuidCooperado(UUID uuidCooperado) {
        this.uuidCooperado = uuidCooperado;
        return this;
    }

    public Voto getVoto() {
        return voto;
    }

    public CedulaOutDto setVoto(Voto voto) {
        this.voto = voto;
        return this;
    }

    public LocalDateTime getDataVoto() {
        return dataVoto;
    }

    public CedulaOutDto setDataVoto(LocalDateTime dataVoto) {
        this.dataVoto = dataVoto;
        return this;
    }
}
