package br.org.enascimento.assembleiacooperados.write.adapter.in.consummers.validacpf;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.StatusCPF;
import br.org.enascimento.assembleiacooperados.write.domain.core.ValidaCPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
public class ValidaCPFImpl implements ValidaCPF {

    @Autowired
    private ValidaCPFServiceAdapter service;

    @Override
    public boolean isAbleToVote(String cpf) throws ValidationException {
        var statusCPF = service.sendWebServiceValidar(cpf);
        return isAbilitadoVotar(statusCPF);
    }

    private boolean isAbilitadoVotar(StatusCPF retorno){
        return (retorno != null && retorno.status().equals("ABLE_TO_VOTE"));
    }
}