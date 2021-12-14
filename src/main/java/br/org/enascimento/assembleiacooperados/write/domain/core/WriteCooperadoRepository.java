package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.util.Optional;
import java.util.UUID;

public interface WriteCooperadoRepository {

    void create(Cooperado capture);

    Optional<Cooperado> findByUuidOrCpf(UUID uuid, String cpf);

    void update(Cooperado cooperado);
}
