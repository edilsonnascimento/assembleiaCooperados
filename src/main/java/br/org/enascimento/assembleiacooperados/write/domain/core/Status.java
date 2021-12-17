package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.time.LocalDateTime;

public class Status {

    private Long id;
    private String descricao;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public Status setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public Status setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Status setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Status setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
