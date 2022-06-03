package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.ContatoOutDTO;

public class FindContatoByCodigoDTOOutQuery implements Query{

    private String codigo;
    private ContatoOutDTO result;

    public String getCodigo() {
        return codigo;
    }
    public void  setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public ContatoOutDTO getResult() {
        return result;
    }
    public void setResult(ContatoOutDTO result) {
        this.result = result;
    }
}