package br.org.enascimento.assembleiacooperados.red.domain.core;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dtos.PautaInDto;
import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadPautaRepository {
    List<PautaInDto> findAll();
    Optional<Pauta> findByUuid(UUID uuid);
    Optional<Pauta> findByUuidOrTitulo(UUID uuid, String titulo);
}
