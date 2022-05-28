package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.time.LocalDateTime;
import java.util.UUID;

public class Pauta extends EntityDomain{
    private Long id;
    private UUID uuid;
    private String titulo;
    private String descricao;

    public Pauta() {
    }
    public Pauta(Long id, UUID uuid, String titulo, String descricao, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
        this.id = id;
        this.uuid = uuid;
        this.titulo = titulo;
        this.descricao = descricao;
    }
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
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}