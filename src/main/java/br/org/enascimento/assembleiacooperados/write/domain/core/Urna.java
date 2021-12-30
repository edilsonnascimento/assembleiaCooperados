package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.util.UUID;

public class Urna extends EntityDomain{

    private Long id;
    private UUID uuid;
    private Long idSessao;
    private Long idCoopereado;
    private Voto voto;

    public Long getId() {
        return id;
    }

    public Urna setId(Long id) {
        this.id = id;
        return this;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Urna setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public Long getIdSessao() {
        return idSessao;
    }

    public Urna setIdSessao(Long idSessao) {
        this.idSessao = idSessao;
        return this;
    }

    public Long getIdCoopereado() {
        return idCoopereado;
    }

    public Urna setIdCoopereado(Long idCoopereado) {
        this.idCoopereado = idCoopereado;
        return this;
    }

    public Voto getVoto() {
        return voto;
    }

    public Urna setVoto(Voto voto) {
        this.voto = voto;
        return this;
    }
}
