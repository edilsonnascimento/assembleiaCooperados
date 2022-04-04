package br.org.enascimento.assembleiacooperados.write.domain.core;

import javax.validation.ValidationException;

public interface ValidaCPF {
    boolean isAbleToVote(String cpf) throws ValidationException;
}
