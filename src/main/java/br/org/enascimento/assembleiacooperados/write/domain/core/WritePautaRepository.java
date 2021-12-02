package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.util.Optional;
import java.util.UUID;

public interface WritePautaRepository {

    void create(Pauta pauta);

    Optional<Pauta> findByUuidOrTitulo(UUID uuid, String titulo);

    Optional<Pauta> findByUuid(UUID uuid);
}
