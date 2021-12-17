package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.StatusDto;

public class FindStatusByIdQuery implements Query{

    private StatusDto result;
    private Long id;

    public StatusDto getResult() {
        return result;
    }

    public FindStatusByIdQuery setResult(StatusDto result) {
        this.result = result;
        return this;
    }

    public Long getId() {
        return id;
    }

    public FindStatusByIdQuery setId(Long id) {
        this.id = id;
        return this;
    }
}
