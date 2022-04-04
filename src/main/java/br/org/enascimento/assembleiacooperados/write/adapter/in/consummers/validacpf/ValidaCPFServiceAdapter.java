package br.org.enascimento.assembleiacooperados.write.adapter.in.consummers.validacpf;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.StatusCPF;

import javax.validation.ValidationException;

public interface ValidaCPFServiceAdapter {
    StatusCPF sendWebServiceValidar(String cpf) throws ValidationException;
}
