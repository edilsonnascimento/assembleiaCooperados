package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.adapter.in.consummers.validacpf.ValidaCPFConsumer;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.EleitorDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CedulaInDto;
import br.org.enascimento.assembleiacooperados.write.adapter.out.WriteCedulaRepositoryImpl;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateCedulaCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;
import helper.GeradorCPF;
import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CreateCedulaHandlerIT extends IntegrationHelper {

    @MockBean
    private ValidaCPFConsumer serverValidaCPF;

    @Test
    void Given_ValidCommand_Abl_Must_DelegateToHeadler(){

        //given
        var captor = ArgumentCaptor.forClass(CedulaInDto.class);
        var repository = mock(WriteCedulaRepositoryImpl.class);
        var handler = new CreateCedulaHandler(repository, serverValidaCPF);
        var command = new CreateCedulaCommand(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), Voto.FAVORAVEL);
        var actual = new EleitorDto(command.uuidCedula(), 1l, 1l, command.voto());
        actual.setCpf(new GeradorCPF().gerar());
        when(repository.retrieveCedulaDto(any())).thenReturn(Optional.of(actual));
        var dto = mock(EleitorDto.class);
        when(serverValidaCPF.isAbleToVote(dto.getCpf())).thenReturn(true);

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
    void Given_ValidCommand_ABLE_TO_VOTE_And_Not_Duplicated_Must_DelegateToHeadler(){
        //given
        var captor = ArgumentCaptor.forClass(CedulaInDto.class);
        var repository = mock(WriteCedulaRepositoryImpl.class);
        var handler = new CreateCedulaHandler(repository, serverValidaCPF);
        var command = new CreateCedulaCommand(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), Voto.FAVORAVEL);
        var actual = new EleitorDto(command.uuidCedula(), 1l, 1l, command.voto());
        actual.setCpf(new GeradorCPF().gerar());
        when(repository.retrieveCedulaDto(any())).thenReturn(Optional.of(actual));
        var dto = mock(EleitorDto.class);
        when(serverValidaCPF.isAbleToVote(dto.getCpf())).thenReturn(true);

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


}