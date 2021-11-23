package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.time.LocalDateTime;
import java.util.UUID;

public class Pauta {

    private int id;
    private UUID uuid;
    private String titulo;
    private String descricao;
    private LocalDateTime creatAt;
    private LocalDateTime updateAt;

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

    public LocalDateTime getCreatAt() {
        return creatAt;
    }

    public Pauta setCreatAt(LocalDateTime creatAt) {
        this.creatAt = creatAt;
        return this;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public Pauta setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
        return this;
    }
}
