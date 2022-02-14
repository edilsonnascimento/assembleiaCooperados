package br.org.enascimento.assembleiacooperados.write.domain.application.command;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.SessaoInDto;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class CreateSessaoCommandTest extends TestHelper {

    @Test
    void GIVEN_ValidData_Must_RetrieveSameDate(){
        //given
        var dto = new SessaoInDto(UUID.randomUUID(), UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20"), 5L);

        //when
        var command = new CreateSessaoCommand(dto);

        //then
        assertThat(command.dto().uuid()).isEqualTo(dto.uuid());
        assertThat(command.dto().uuidPauta()).isEqualTo(dto.uuidPauta());
        assertThat(command.dto().limiteSessao()).isEqualTo(dto.limiteSessao());
    }
}
