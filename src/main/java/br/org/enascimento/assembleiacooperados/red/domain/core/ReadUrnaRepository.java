package br.org.enascimento.assembleiacooperados.red.domain.core;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.CedulaOutDto;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cedula;

import java.util.Optional;
import java.util.UUID;

public interface ReadUrnaRepository {
    Optional<Cedula> findByUuid(UUID uuid);
    Optional<CedulaOutDto> findByUuidDto(UUID uuid);


}
