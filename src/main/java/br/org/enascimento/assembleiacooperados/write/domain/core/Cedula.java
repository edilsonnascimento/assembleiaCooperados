package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.util.UUID;

public class Cedula extends EntityDomain{

    private Long id;
    private UUID uuid;
    private Long idSessao;
    private Long idCoopereado;
    private Voto voto;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public Long getIdCoopereado() {
        return idCoopereado;
    }
    public void setIdCoopereado(Long idCoopereado) {
        this.idCoopereado = idCoopereado;
    }
    public Voto getVoto() {
        return voto;
    }
    public void setVoto(Voto voto) {
        this.voto = voto;
    }
}