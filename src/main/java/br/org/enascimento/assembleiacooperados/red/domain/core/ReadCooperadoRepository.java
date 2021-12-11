package br.org.enascimento.assembleiacooperados.red.domain.core;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.CooperadoInDto;

import java.util.List;

public interface ReadCooperadoRepository {
    List<CooperadoInDto> findAll();
}
