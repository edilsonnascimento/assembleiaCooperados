package br.org.enascimento.assembleiacooperados.write.domain.core;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.ContatoDTO;

public interface WriteContatoRepository {
    boolean create(ContatoDTO dto);
}
