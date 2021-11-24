package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Pauta {

    private int id;
    private UUID uuid;
    private String titulo;
    private String descricao;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pauta pauta = (Pauta) o;
        return uuid.equals(pauta.uuid) && titulo.equals(pauta.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, titulo);
    }

    @Override
    public String toString() {
        return "Pauta{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
