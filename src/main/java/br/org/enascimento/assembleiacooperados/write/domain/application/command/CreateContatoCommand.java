package br.org.enascimento.assembleiacooperados.write.domain.application.command;

import br.org.enascimento.assembleiacooperados.write.domain.core.Operadora;

import java.math.BigDecimal;

public class CreateContatoCommand implements Command{

    private BigDecimal telefone;
    private Operadora operadora;
    private String nome;

    public CreateContatoCommand(BigDecimal telefone, Operadora operadora, String nome) {
        this.telefone = telefone;
        this.operadora = operadora;
        this.nome = nome;
    }
    public BigDecimal getTelefone() {
        return telefone;
    }
    public Operadora getOperadora() {
        return operadora;
    }
    public String getNome() {
        return nome;
    }
}