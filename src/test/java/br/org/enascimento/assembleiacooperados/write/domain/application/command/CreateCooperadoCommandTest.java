package br.org.enascimento.assembleiacooperados.write.domain.application.command;

import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Tag("unit")
public class CreateCooperadoCommandTest extends TestHelper {

    @Test
    void GIVEN_ValidDataCooperado_Must_RetrieveSameDate(){
        //given
        var uuid = randomUUID();
        var nome = faker.name().fullName();
        var cpf = faker.number().digits(11);

        //when
        var command = new CreateCooperadoCommand(uuid, nome, cpf);

        //then
        assertThat(command.uuid().equals(uuid));
        assertThat(command.nome().equals(nome));
        assertThat(command.cpf().equals(cpf));
    }

}
