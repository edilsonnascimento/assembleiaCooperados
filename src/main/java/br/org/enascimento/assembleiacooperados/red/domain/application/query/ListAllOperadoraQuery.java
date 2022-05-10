package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.write.domain.core.Operadora;

import java.util.List;

public class ListAllOperadoraQuery implements Query{
    private List<Operadora> result;

    public List<Operadora> getResult() {
        return result;
    }
    public ListAllOperadoraQuery setResult(List<Operadora> result) {
        this.result = result;
        return this;
    }
}