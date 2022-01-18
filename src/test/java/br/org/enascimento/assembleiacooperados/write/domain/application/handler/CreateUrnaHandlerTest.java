package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.adapter.in.consummers.validacpf.ValidaCPFConsumer;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.EleitorDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.UrnaInDto;
import br.org.enascimento.assembleiacooperados.write.adapter.out.WriteUrnaRepositoryImpl;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateUrnaCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;
import br.org.enascimento.assembleiacooperados.write.domain.exception.UrnaNotExistedExcepetion;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;
import java.util.UUID;

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
        var service = mock(ValidaCPFConsumer.class);
        var handler = new CreateUrnaHandler(repository, service);
        var command = new CreateUrnaCommand(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), Voto.FAVORAVEL);
        var actual = new EleitorDto(command.uuidUrna(), 1l, 1l, command.voto());
        when(repository.retrieveUrnaDto(any())).thenReturn(Optional.of(actual));

        //when
        handler.handle(command);

        //then
        verify(repository, timeout(1)).retrieveUrnaDto(any());
        verify(repository, timeout(1)).create(captor.capture());
        var expected = captor.getValue();
        assertThat(actual.getUuid()).isEqualTo(expected.getUuid());
        assertThat(actual.getIdCooperado()).isEqualTo(expected.getIdCooperado());
        assertThat(actual.getIdSessao()).isEqualTo(expected.getIdSessao());
    }

    @Test
    void GIVEN_InvalidCommandUrna_MUST_TrowException(){
        //given
        var repository = mock(WriteUrnaRepositoryImpl.class);
        var service = mock(ValidaCPFConsumer.class);
        var handler = new CreateUrnaHandler(repository, service);
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
