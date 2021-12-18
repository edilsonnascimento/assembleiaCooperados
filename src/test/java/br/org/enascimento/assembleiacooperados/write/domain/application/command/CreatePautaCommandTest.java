package br.org.enascimento.assembleiacooperados.write.domain.application.command;

import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("unit")
public class CreatePautaCommandTest extends TestHelper {

    @Test
    void GIVEN_ValidData_Must_RetrieveSameDate(){
        // given
        var uuid = UUID.randomUUID();
        var titulo = faker.lorem().characters();
        var descricao = faker.lorem().characters();

        //when
        var command = new CreatePautaCommand(uuid, titulo, descricao);

        //then
        assertTrue(command.uuid().equals(uuid));
        assertTrue(command.descricao().equals(descricao));
        assertTrue(command.titulo().equals(titulo));
    }
}
