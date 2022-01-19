package br.org.enascimento.assembleiacooperados.write.domain.core;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.EleitorDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CedulaInDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CedulaDto;

import java.util.Optional;

public interface WriteCedulaRepository {

    boolean create(CedulaInDto dto);
    Optional<EleitorDto> retrieveCedulaDto(CedulaDto dtoUrna);
}
