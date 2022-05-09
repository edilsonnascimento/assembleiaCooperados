package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.ContatoOutDTO;

import java.util.List;

public class ListAllContatoQuery implements Query{

    private List<ContatoOutDTO> result;

    public List<ContatoOutDTO> getResult() {
        return result;
    }
    public ListAllContatoQuery setResult(List<ContatoOutDTO> result) {
        this.result = result;
        return this;
    }
}