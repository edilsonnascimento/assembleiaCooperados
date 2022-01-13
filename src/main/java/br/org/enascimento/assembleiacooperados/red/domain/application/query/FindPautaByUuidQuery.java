package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dtos.PautaInDto;

import java.util.UUID;

public class FindPautaByUuidQuery implements Query{

    private PautaInDto result;
    private UUID uuid;

    public PautaInDto getResult() {
        return result;
    }

    public void setResult(PautaInDto result) {
        this.result = result;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}

