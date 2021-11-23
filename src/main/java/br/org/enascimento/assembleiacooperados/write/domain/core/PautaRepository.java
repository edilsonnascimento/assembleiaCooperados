package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.util.Optional;
import java.util.UUID;

public interface PautaRepository {

    void create(Pauta pauta);
}
