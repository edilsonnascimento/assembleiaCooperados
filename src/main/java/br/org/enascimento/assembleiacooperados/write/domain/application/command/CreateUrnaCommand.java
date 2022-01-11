package br.org.enascimento.assembleiacooperados.write.domain.application.command;

import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;

import java.util.UUID;

public record CreateUrnaCommand(
        UUID uuidUrna,
        UUID uuidSessao,
        UUID uuidCooperado,
        Voto voto
        ) implements Command{


}
