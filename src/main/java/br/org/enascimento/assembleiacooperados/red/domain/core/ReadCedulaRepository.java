package br.org.enascimento.assembleiacooperados.red.domain.core;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.CedulaOutDto;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cedula;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadCedulaRepository {
    Optional<Cedula> findByUuid(UUID uuid);
    Optional<CedulaOutDto> findByUuidDto(UUID uuid);
    Optional<List<CedulaOutDto>> findAll();
}
