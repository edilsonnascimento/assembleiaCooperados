package br.org.enascimento.assembleiacooperados.red.domain.core;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dtos.CooperadoInDto;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadCooperadoRepository {
    List<CooperadoInDto> findAll();

    Optional<Cooperado> findByUuid(UUID uuid);

    Optional<Cooperado> findByUuidOrCpf(UUID uuid, String cpf);
}
