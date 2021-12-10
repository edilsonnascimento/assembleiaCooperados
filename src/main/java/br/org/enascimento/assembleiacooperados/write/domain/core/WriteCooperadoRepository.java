package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.util.Optional;
import java.util.UUID;

public interface WriteCooperadoRepository {

    void create(Cooperado capture);

    Optional<Cooperado> findByUuid(UUID uuid);
}
