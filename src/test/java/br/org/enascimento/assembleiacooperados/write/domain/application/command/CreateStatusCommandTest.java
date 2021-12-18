package br.org.enascimento.assembleiacooperados.write.domain.application.command;

import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateStatusCommand;
import helper.TestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
public class CreateStatusCommandTest extends TestHelper {

    @Test
    void GIVEN_ValidData_Must_RetrieveSameDate(){
        //given
        var descricao = faker.lorem().characters(50);

        //when
        var command = new CreateStatusCommand(descricao);

        //then
        Assertions.assertThat(command.descricao()).isEqualTo(descricao);
    }
}
