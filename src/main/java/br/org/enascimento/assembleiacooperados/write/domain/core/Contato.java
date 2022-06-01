package br.org.enascimento.assembleiacooperados.write.domain.core;

public class Contato extends EntityDomain{

    private Long id;
    private String nome;
    private String codigo;
    private String telefone;
    private Operadora operadora;

    public Contato() {
        super();
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public Operadora getOperadora() {
        return operadora;
    }
    public void setOperadora(Operadora operadora) {
        this.operadora = operadora;
    }
}