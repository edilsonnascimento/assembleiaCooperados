package br.org.enascimento.assembleiacooperados.red.adapter.out.dtos;


import br.org.enascimento.assembleiacooperados.write.domain.core.Operadora;

import java.time.LocalDateTime;

public record ContatoOutDTO(
        String nomeContato,
        String telefone,
        Operadora operadora,
        LocalDateTime dataCadastro) {
}