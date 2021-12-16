package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.CooperadoInDto;

import java.util.List;

public class ListAllCooperadosQuery implements Query {

    List<CooperadoInDto> result;

    public List<CooperadoInDto> getResult() {
        return result;
    }

    public void setResult(List<CooperadoInDto> result) {
        this.result = result;
    }
}
