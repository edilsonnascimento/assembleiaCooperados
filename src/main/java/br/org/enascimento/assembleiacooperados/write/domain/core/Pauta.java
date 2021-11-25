package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Pauta {

    private int id;
    private UUID uuid;
    private String titulo;
    private String descricao;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Pauta() {
    }

    public int getId() {
        return id;
    }

    public Pauta setId(int id) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Pauta setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Pauta setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
