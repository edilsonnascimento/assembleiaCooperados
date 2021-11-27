package br.org.enascimento.assembleiacooperados.write.domain.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CreatePautaCommandTest {

    @Test
    void GIVEN_ValidData_Must_RetrieveSameDate(){
        // given
        var uuid = UUID.randomUUID();
        var titulo = "Aumento da segurança";
        var descricao = "Contratar 5 seguranças fixos.";

        //when
        var command = new CreatePautaCommand(uuid, titulo, descricao);

        //then
        assertTrue(command.uuid().equals(uuid));
        assertTrue(command.descricao().equals(descricao));
        assertTrue(command.titulo().equals(titulo));
    }
}
