package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadPautaRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.PautaNotExistentException;
import br.org.enascimento.assembleiacooperados.red.domain.exception.SessaoInvalidaException;
import br.org.enascimento.assembleiacooperados.red.domain.exception.StatusNotExistedException;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.SessaoInDto;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateSessaoCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import br.org.enascimento.assembleiacooperados.write.domain.core.Sessao;
import br.org.enascimento.assembleiacooperados.write.domain.core.Status;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteSessaoRepository;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Tag("unit")
class CreateSessaoHandlerTest extends TestHelper {

    @Mock
    private WriteSessaoRepository repository;
    @Mock
    private ReadPautaRepository repositoryRead;
    @InjectMocks
    private CreateSessaoHandler handler;

    @Test
    void Given_ValidCreateSessaoCommand_Must_DelegateToHeadler(){
        var captor = ArgumentCaptor.forClass(Sessao.class);

        //given
        var limiteSessao = 5L;
        var dto = new SessaoInDto(UUID.randomUUID(), UUID.randomUUID(), limiteSessao);
        var expectedDate = LocalDateTime.now().plusMinutes(limiteSessao);
        var command = new CreateSessaoCommand(dto);
        var pauta = new Pauta();
        pauta.setId(1l);
        pauta.setUuid(UUID.randomUUID());
        pauta.setTitulo("PRIMEIRO-TITULO");
        pauta.setDescricao("PRIMEIRA-DESCICAO");
        when(repositoryRead.findByUuid(any())).thenReturn(Optional.of(pauta));
        var status = new Status().setId(1l);
        when(repository.findStatus(anyLong())).thenReturn(Optional.of(status));

        //when
        handler.handle(command);

        //then
        verify(repositoryRead, timeout(1)).findByUuid(any());
        verify(repository, timeout(1)).create(captor.capture());
        var actualDate= captor.getValue().getFimSessao();
        assertThat(actualDate).isEqualToIgnoringNanos(expectedDate);
    }

    @Test
    void Given_ValidCreateSessaoLimiteEmptyCommand_Must_DelegateToHeadler(){

        //given
        var captor = ArgumentCaptor.forClass(Sessao.class);
        Long limiteSessao = null;
        var dto = new SessaoInDto(UUID.randomUUID(), UUID.randomUUID(), limiteSessao);
        var expectedDate = LocalDateTime.now().plusMinutes(1L);
        var command = new CreateSessaoCommand(dto);
        var pauta = new Pauta();
        pauta.setId(1l);
        pauta.setUuid(UUID.randomUUID());
        pauta.setTitulo("PRIMEIRO-TITULO");
        pauta.setDescricao("PRIMEIRA-DESCICAO");
        when(repositoryRead.findByUuid(any())).thenReturn(Optional.of(pauta));
        var status = new Status().setId(1l);
        when(repository.findStatus(anyLong())).thenReturn(Optional.of(status));

        //when
        handler.handle(command);

        //then
        verify(repositoryRead, timeout(1)).findByUuid(any());
        verify(repository, timeout(1)).create(captor.capture());
        var actualDate= captor.getValue().getFimSessao();
        assertThat(actualDate).isEqualToIgnoringNanos(expectedDate);
    }

    @Test
    void Given_InValidCreateSessaoLimiteOver_Must_ReturnException(){
        //given
        var dto = new SessaoInDto(UUID.randomUUID(), UUID.randomUUID(), 61L);
        var command = new CreateSessaoCommand(dto);
        var pauta = new Pauta();
        pauta.setId(1l);
        pauta.setUuid(UUID.randomUUID());
        pauta.setTitulo("PRIMEIRO-TITULO");
        pauta.setDescricao("PRIMEIRA-DESCICAO");
        when(repositoryRead.findByUuid(any())).thenReturn(Optional.of(pauta));
        var status = new Status().setId(1l);
        when(repository.findStatus(anyLong())).thenReturn(Optional.of(status));

        //when
        var expectedExcepetion = assertThrows(SessaoInvalidaException.class, ()->
                handler.handle(command));

        //then
        assertThat(expectedExcepetion.getMessage()).isEqualTo("Out of session limit");
    }

    @Test
    void DADO_CommandInvalid_DEVE_LancarExcessaoPauta(){
        //given
        var dto = new SessaoInDto(UUID.randomUUID(), UUID.randomUUID(), 61L);
        var command = new CreateSessaoCommand(dto);
        when(repositoryRead.findByUuid(any())).thenReturn(Optional.empty());

        //when
        var expectedExcepetion = assertThrows(PautaNotExistentException.class, ()->
                handler.handle(command));

        //then
        verify(repositoryRead, timeout(1)).findByUuid(any());
        verify(repository, never()).findStatus(anyLong());
        verify(repository, never()).create(any());
        assertThat(expectedExcepetion.getMessage()).isEqualTo("Pauta not exist");
    }

    @Test
    void DADO_CommandInvalid_DEVE_LancarExcessaoStatus() {
        //given
        var dto = new SessaoInDto(UUID.randomUUID(), UUID.randomUUID(), 61L);
        var command = new CreateSessaoCommand(dto);
        var pauta = mock(Pauta.class);
        when(repositoryRead.findByUuid(any())).thenReturn(Optional.of(pauta));
        when(repository.findStatus(anyLong())).thenReturn(Optional.empty());

        //when
        var expectedExcepetion = assertThrows(StatusNotExistedException.class, ()->
                handler.handle(command));

        //then
        verify(repositoryRead, timeout(1)).findByUuid(any());
        verify(repository, timeout(1)).findStatus(anyLong());
        verify(repository, never()).create(any());
        assertThat(expectedExcepetion.getMessage()).isEqualTo("Status not exist");
    }
}