package br.org.enascimento.assembleiacooperados.write.domain.core;

public class Status {

    private Long id;
    private String descricao;

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
}
