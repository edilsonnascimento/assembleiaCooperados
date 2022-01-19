package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.CedulaOutDto;

import java.util.UUID;

public class FindCedulaByUuidQuery implements Query{

    private UUID uuid;
    private CedulaOutDto result;

    public UUID getUuid() {
        return uuid;
    }

    public FindCedulaByUuidQuery setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public CedulaOutDto getResult() {
        return result;
    }

    public FindCedulaByUuidQuery setResult(CedulaOutDto result) {
        this.result = result;
        return this;
    }
}
