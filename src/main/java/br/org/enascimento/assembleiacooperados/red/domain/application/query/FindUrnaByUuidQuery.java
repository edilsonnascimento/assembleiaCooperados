package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.UrnaOutDto;

import java.util.UUID;

public class FindUrnaByUuidQuery implements Query{

    private UUID uuid;
    private UrnaOutDto result;

    public UUID getUuid() {
        return uuid;
    }

    public FindUrnaByUuidQuery setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public UrnaOutDto getResult() {
        return result;
    }

    public FindUrnaByUuidQuery setResult(UrnaOutDto result) {
        this.result = result;
        return this;
    }
}
