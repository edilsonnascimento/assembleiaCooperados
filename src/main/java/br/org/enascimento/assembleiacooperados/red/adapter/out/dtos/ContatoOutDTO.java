package br.org.enascimento.assembleiacooperados.red.adapter.out.dtos;


import java.time.LocalDateTime;

public record ContatoOutDTO(
        String nomeContato,
        String telefone,
        String nomeOperadora,
        LocalDateTime dataCadastro) {
}