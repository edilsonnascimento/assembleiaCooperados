package br.org.enascimento.assembleiacooperados.red.domain.core;

import java.util.List;

public interface ReadPautaRepository {
    List<PautaDto> findAll();
}
