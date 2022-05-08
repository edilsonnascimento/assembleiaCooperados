package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.util.UUID;

public class Pauta extends EntityDomain{
    private Long id;
    private UUID uuid;
    private String titulo;
    private String descricao;

    public Long getId() {
        return id;
    }
    public Pauta setId(Long id) {
        this.id = id;
        return this;
    }
    public UUID getUuid() {
        return uuid;
    }
    public Pauta setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }
    public String getTitulo() {
        return titulo;
    }
    public Pauta setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }
    public String getDescricao() {
        return descricao;
    }
    public Pauta setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }
}