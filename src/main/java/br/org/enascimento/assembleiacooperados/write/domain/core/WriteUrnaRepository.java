package br.org.enascimento.assembleiacooperados.write.domain.core;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.UrnaInDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.UrnaIntoDto;

import java.util.Optional;

public interface WriteUrnaRepository {

    boolean create(UrnaInDto dto);
    Optional<UrnaInDto> retrieveUrnaDto(UrnaIntoDto dtoUrna);
}
