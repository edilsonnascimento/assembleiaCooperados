package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.StatusDto;

import java.util.List;

public class ListAllStatusQuery implements Query{

    private List<StatusDto> result;

    public void setResult(List<StatusDto> result) {
        this.result = result;
    }

    public List<StatusDto> getResult() {
        return result;
    }
}
