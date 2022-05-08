package br.org.enascimento.assembleiacooperados.write.domain.core;

public class Status extends EntityDomain {
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
    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}