package br.org.enascimento.assembleiacooperados.red.domain.core;

import br.org.enascimento.assembleiacooperados.write.domain.core.Status;

import java.util.List;
import java.util.Optional;

public interface ReadStatusRepository {
    Optional<Status> findById(Long id);
    Optional<List<Status>> findAll();
}
