package br.org.enascimento.assembleiacooperados.red.domain.core;

import br.org.enascimento.assembleiacooperados.red.adapter.in.PautaDto;

import java.util.List;

public interface ReadPautaRepository {
    List<PautaDto> findAll();
}
