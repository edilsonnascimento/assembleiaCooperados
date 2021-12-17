package br.org.enascimento.assembleiacooperados.write.domain.application.commad;

import br.org.enascimento.assembleiacooperados.write.domain.application.command.UpdateCooperadoCommand;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("unit")
public class UpdateCooperadoCommandTest extends TestHelper {

    @Test
    void GIVEN_ValidData_Must_RetrieveSameDate(){
        //given
        var uuid = UUID.randomUUID();
        var nome = faker.name().fullName();
        var cpf = faker.number().digits(11);

        //when
        var command = new UpdateCooperadoCommand(uuid, nome, cpf);

        //then
        assertThat(command.uuid().equals(uuid));
        assertThat(command.nome().equals(nome));
        assertThat(command.cpf().equals(cpf));
    }

}
