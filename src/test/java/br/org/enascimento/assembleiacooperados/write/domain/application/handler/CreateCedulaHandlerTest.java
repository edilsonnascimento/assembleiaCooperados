package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CedulaInDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.EleitorDto;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateCedulaCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.ValidaCPF;
import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCedulaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.CedualNotExistedExcepetion;
import br.org.enascimento.assembleiacooperados.write.domain.exception.ValidaCPFException;
import helper.GeradorCPF;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.CPF_INVALID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Tag("unit")
class CreateCedulaHandlerTest extends TestHelper {

    @Mock
    private WriteCedulaRepository repository;
    @Mock
    private ValidaCPF serverValidaCPF;
    @InjectMocks
    private CreateCedulaHandler handler;

    @Test
    void DADO_CommandValida_DEVE_CriarUmVoto(){
        //given
        var captor = ArgumentCaptor.forClass(CedulaInDto.class);
        var command = new CreateCedulaCommand(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), Voto.FAVORAVEL);
        var actual = new EleitorDto(UUID.randomUUID(), 1L, 1L, Voto.FAVORAVEL);
        actual.setCpf(new GeradorCPF().gerar());
        when(repository.retrieveCedulaDto(any())).thenReturn(Optional.of(actual));
        when(serverValidaCPF.isAbleToVote(actual.getCpf())).thenReturn(false);

        //when
        handler.handle(command);

        //then
        verify(serverValidaCPF, timeout(1)).isAbleToVote(anyString());
        verify(repository, timeout(1)).retrieveCedulaDto(any());
        verify(repository, timeout(1)).create(captor.capture());
        var expected = captor.getValue();
        assertThat(actual.getUuid()).isEqualTo(expected.getUuid());
        assertThat(actual.getIdCooperado()).isEqualTo(expected.getIdCooperado());
        assertThat(actual.getIdSessao()).isEqualTo(expected.getIdSessao());
    }

    @Test
    void DADO_CommandInValida_DEVE_Retornar_CedualNotExistedExcepetion(){
        //given
        var command = new CreateCedulaCommand(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), Voto.FAVORAVEL);
        when(repository.retrieveCedulaDto(any())).thenReturn(Optional.empty());

        //when
        var exceptionExpected =
                assertThrows(CedualNotExistedExcepetion.class, ()-> handler.handle(command));

        //then
        verify(repository, never()).create(any());
        assertThat(exceptionExpected.getMessage()).isEqualTo("Invalid Cedula");
    }

    @Test
    void DADO_CPF_Invalido_DEVE_Lancar_Exception() {
        //given
        var command = new CreateCedulaCommand(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), Voto.FAVORAVEL);
        var actual = new EleitorDto(UUID.randomUUID(), 1L, 1L, Voto.FAVORAVEL);
        actual.setCpf(new GeradorCPF().gerar());
        when(repository.retrieveCedulaDto(any())).thenReturn(Optional.of(actual));
        when(serverValidaCPF.isAbleToVote(actual.getCpf())).thenReturn(true);

        //when
        var exceptionExpected =
                assertThrows(ValidaCPFException.class, ()-> handler.handle(command));

        //then
        verify(repository, never()).create(any());
        verify(repository, timeout(1)).retrieveCedulaDto(any());
        verify(serverValidaCPF, timeout(1)).isAbleToVote(anyString());
        assertThat(exceptionExpected.getMessage()).isEqualTo("invalid cpf for voting");
    }
}