package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.red.domain.exception.SessaoNotExistedExcepetion;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.UrnaInDto;
import br.org.enascimento.assembleiacooperados.write.adapter.out.WriteUrnaRepositoryImpl;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateUrnaCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;
import br.org.enascimento.assembleiacooperados.write.domain.exception.UrnaNotExistedExcepetion;
import helper.TestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Tag("unit")
public class CreateUrnaHandlerTest extends TestHelper {

    @Test
    void Given_ValidCommand_Must_DelegateToHeadler(){

        //given
        var captor = ArgumentCaptor.forClass(UrnaInDto.class);
        var repository = mock(WriteUrnaRepositoryImpl.class);
        var handler = new CreateUrnaHandler(repository);
        var command = new CreateUrnaCommand(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), Voto.FAVORAVEL);
        var actual = new UrnaInDto(command.uuidUrna(), 1l, 1l, command.voto());
        when(repository.retrieveUrnaDto(any())).thenReturn(Optional.of(actual));

        //when
        handler.handle(command);

        //then
        verify(repository, timeout(1)).retrieveUrnaDto(any());
        verify(repository, timeout(1)).create(captor.capture());
        var expected = captor.getValue();
        assertThat(actual.uuid()).isEqualTo(expected.uuid());
        assertThat(actual.idCooperado()).isEqualTo(expected.idCooperado());
        assertThat(actual.idSessao()).isEqualTo(expected.idSessao());
    }

    @Test
    void GIVEN_InvalidCommandUrna_MUST_TrowException(){
        //given
        var repository = mock(WriteUrnaRepositoryImpl.class);
        var handler = new CreateUrnaHandler(repository);
        var command = new CreateUrnaCommand(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), Voto.FAVORAVEL);
        when(repository.retrieveUrnaDto(any())).thenReturn(Optional.empty());

        //when
        var exceptionExpected =
                assertThrows(UrnaNotExistedExcepetion.class, ()-> handler.handle(command));

        //then
        verify(repository, never()).create(any());
        assertThat(exceptionExpected.getMessage()).isEqualTo("Urna not exist");
    }

    

}
