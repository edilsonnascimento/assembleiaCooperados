package br.org.enascimento.assembleiacooperados.red.domain.core;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.UrnaOutDto;
import br.org.enascimento.assembleiacooperados.write.domain.core.Urna;

import java.util.Optional;
import java.util.UUID;

public interface ReadUrnaRepository {
    Optional<Urna> findByUuid(UUID uuid);
    Optional<UrnaOutDto> findByUuidDto(UUID uuid);


}
