package br.org.enascimento.assembleiacooperados.red.domain.core;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.ContatoOutDTO;
import br.org.enascimento.assembleiacooperados.write.domain.core.Contato;

import java.util.List;
import java.util.Optional;

public interface ReadContatoRepository {
    Optional<List<ContatoOutDTO>> findAll();
    Optional<Contato> findByCodigo(String codigo);
}