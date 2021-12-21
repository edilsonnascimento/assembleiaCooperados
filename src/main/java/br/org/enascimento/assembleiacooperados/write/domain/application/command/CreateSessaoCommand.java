package br.org.enascimento.assembleiacooperados.write.domain.application.command;


import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.SessaoInDto;

public record CreateSessaoCommand(SessaoInDto dto) implements Command{

}
