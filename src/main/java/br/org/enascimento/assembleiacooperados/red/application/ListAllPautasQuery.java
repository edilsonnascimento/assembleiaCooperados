package br.org.enascimento.assembleiacooperados.red.application;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.PautaInDto;

import java.util.List;

public class ListAllPautasQuery implements Query{

    private List<PautaInDto> result;

    public List<PautaInDto> getResult() {
        return result;
    }

    public void setResult(List<PautaInDto> result) {
        this.result = result;
    }


}
