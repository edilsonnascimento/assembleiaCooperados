package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.SessaoOutDto;

import java.util.List;

public class ListAllSessaoQuery implements Query{

    private List<SessaoOutDto> result;

    public List<SessaoOutDto> getResult() {
        return result;
    }

    public ListAllSessaoQuery setResult(List<SessaoOutDto> result) {
        this.result = result;
        return this;
    }
}
