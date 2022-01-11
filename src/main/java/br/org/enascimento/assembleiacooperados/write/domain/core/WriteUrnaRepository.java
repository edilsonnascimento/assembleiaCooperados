package br.org.enascimento.assembleiacooperados.write.domain.core;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.UrnaInDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.UrnaIntoDto;

import java.util.Optional;

public interface WriteUrnaRepository {

    boolean create(UrnaInDto dto);
    Optional<UrnaInDto> retrieveUrnaDto(UrnaIntoDto dtoUrna);
}
