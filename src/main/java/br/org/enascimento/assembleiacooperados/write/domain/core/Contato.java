package br.org.enascimento.assembleiacooperados.write.domain.core;

public class Contato extends EntityDomain{

    private String telefone;
    private Operadora operadora;

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