package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.CooperadoDto;

import java.util.UUID;

public class FindCooperadoByUuidQuery implements Query {

    private CooperadoDto result;
    private UUID uuid;

    public CooperadoDto getResult() {
        return result;
    }

    public void setResult(CooperadoDto result) {
        this.result = result;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
