package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.CedulaOutDto;

import java.util.List;

public class ListAllCedulaQuery implements Query {

    private List<CedulaOutDto> result;

    public List<CedulaOutDto> getResult() {
        return result;
    }

    public ListAllCedulaQuery setResult(List<CedulaOutDto> result) {
        this.result = result;
        return this;
    }
}
