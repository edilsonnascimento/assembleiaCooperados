package br.org.enascimento.assembleiacooperados.red.domain.core;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.PautaInDto;

import java.util.List;

public interface ReadPautaRepository {
    List<PautaInDto> findAll();
}
