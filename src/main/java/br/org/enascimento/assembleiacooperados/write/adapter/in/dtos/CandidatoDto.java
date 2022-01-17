package br.org.enascimento.assembleiacooperados.write.adapter.in.dtos;

import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;

import java.util.UUID;

public class CandidatoDto extends UrnaInDto{

    private String cpf;

    public CandidatoDto(UUID uuid, Long idSessao, Long idCooperado, Voto voto) {
        super(uuid, idSessao, idCooperado, voto);
    }
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
