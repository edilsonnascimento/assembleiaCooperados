package br.org.enascimento.assembleiacooperados.write.domain.core;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.EleitorDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.UrnaInDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CedulaDto;

import java.util.Optional;

public interface WriteUrnaRepository {

    boolean create(UrnaInDto dto);
    Optional<EleitorDto> retrieveUrnaDto(CedulaDto dtoUrna);
}
