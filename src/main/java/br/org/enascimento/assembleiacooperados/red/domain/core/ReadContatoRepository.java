package br.org.enascimento.assembleiacooperados.red.domain.core;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.ContatoOutDTO;

import java.util.List;
import java.util.Optional;

public interface ReadContatoRepository {
    Optional<List<ContatoOutDTO>> findAll();
}