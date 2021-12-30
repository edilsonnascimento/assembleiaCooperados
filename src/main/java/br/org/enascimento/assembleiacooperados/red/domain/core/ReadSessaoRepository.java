package br.org.enascimento.assembleiacooperados.red.domain.core;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.SessaoOutDto;
import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.UrnaOutDto;
import br.org.enascimento.assembleiacooperados.write.domain.core.Sessao;

import java.util.Optional;
import java.util.UUID;

public interface ReadSessaoRepository {
    Optional<Sessao> findByUuid(UUID uuid);
    Optional<SessaoOutDto> findByUuidReturnDto(UUID uuid);
}
