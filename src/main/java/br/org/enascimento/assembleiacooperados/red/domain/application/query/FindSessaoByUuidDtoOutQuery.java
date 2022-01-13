package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.SessaoOutDto;

import java.util.UUID;

public class FindSessaoByUuidDtoOutQuery implements Query{

    private UUID uuid;
    private SessaoOutDto result;

    public UUID getUuid() {
        return uuid;
    }

    public FindSessaoByUuidDtoOutQuery setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public SessaoOutDto getResult() {
        return result;
    }

    public FindSessaoByUuidDtoOutQuery setResult(SessaoOutDto result) {
        this.result = result;
        return this;
    }
}
