package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.util.Optional;

public interface WriteSessaoRepository {
    boolean create(Sessao sessao);
    Optional<Status> findStatus(Long id);
    void fecharSessoes();
}
