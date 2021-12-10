package br.org.enascimento.assembleiacooperados.red.domain.core;

import br.org.enascimento.assembleiacooperados.write.adapter.in.CooperadoDto;

import java.util.List;

public interface ReadCooperadoRepository {
    List<CooperadoDto> findAll();
}
