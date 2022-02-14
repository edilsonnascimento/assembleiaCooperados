package br.org.enascimento.assembleiacooperados.write.domain.application.command;

import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@Tag("unit")
class CreateCedulaCommandTest extends TestHelper {

    @Test
    void GIVEN_ValidData_Must_RetrieveSameDate() {
        //given
        var uuidUrna = UUID.randomUUID();
        var uuidSessao = UUID.randomUUID();
        var uuidCooperado = UUID.randomUUID();
        var voto = Voto.FAVORAVEL;

        //when
        var command = new CreateCedulaCommand(
                uuidUrna,
                uuidSessao,
                uuidCooperado,
                voto
        );

        //then
        assertThat(command.uuidCedula()).isEqualTo(uuidUrna);
        assertThat(command.uuidSessao()).isEqualTo(uuidSessao);
        assertThat(command.uuidCooperado()).isEqualTo(uuidCooperado);
        assertThat(command.voto()).isEqualTo(voto);
    }

    @Test
    void MUST_ImplementInterface(){

        //given
        var uuidUrna = UUID.randomUUID();
        var uuidSessao = UUID.randomUUID();
        var uuidCooperado = UUID.randomUUID();
        var voto = Voto.FAVORAVEL;

        //when
        var command = new CreateCedulaCommand(
                uuidUrna,
                uuidSessao,
                uuidCooperado,
                voto
        );

        //then
        assertThat(command).isInstanceOf(Command.class);
    }
}
