package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.time.LocalDateTime;

public abstract class EntityDomain {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EntityDomain() {
    }
    public EntityDomain(LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}