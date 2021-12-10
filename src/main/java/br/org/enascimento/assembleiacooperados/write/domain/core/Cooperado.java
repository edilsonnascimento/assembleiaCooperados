package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.time.LocalDateTime;
import java.util.UUID;

public class Cooperado {

    private int id;
    private UUID uuid;
    private String nome;
    private String cpf;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public int getId() {
        return id;
    }

    public Cooperado setId(int id) {
        this.id = id;
        return this;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Cooperado setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Cooperado setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getCpf() {
        return cpf;
    }

    public Cooperado setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Cooperado setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Cooperado setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
