package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.util.List;
import java.util.UUID;

public class Cooperado extends EntityDomain{
    private Long id;
    private UUID uuid;
    private String nome;
    private String cpf;
    private List<Contato> contatos;

    public Long getId() {
        return id;
    }
    public Cooperado setId(Long id) {
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
    public List<Contato> getContatos() {
        return contatos;
    }
    public Cooperado setContatos(List<Contato> contatos) {
        this.contatos = contatos;
        return this;
    }
}