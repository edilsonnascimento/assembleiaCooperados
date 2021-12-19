package br.org.enascimento.assembleiacooperados.write.domain.application.command;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.StatusInDto;
import helper.TestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
public class UpdateStatusCommandTest extends TestHelper {

    @Test
    void GIVEN_ValidData_Must_RetrieveSameDate(){
        //given
        var statusInDto = new StatusInDto("NOVO STATU");
        var id = 1l;
        //when
        var command = new UpdateStatusCommand(id, statusInDto.descricao());

        //then
        Assertions.assertThat(command.descricao()).isEqualTo(statusInDto.descricao());

    }
}
