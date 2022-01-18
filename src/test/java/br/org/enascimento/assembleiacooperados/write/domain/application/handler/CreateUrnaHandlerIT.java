package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.adapter.in.consummers.validacpf.ValidaCPFConsumer;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.EleitorDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.UrnaInDto;
import br.org.enascimento.assembleiacooperados.write.adapter.out.WriteUrnaRepositoryImpl;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateUrnaCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;
import helper.CPFHelper;
import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CreateUrnaHandlerIT extends IntegrationHelper {

    @MockBean
    private ValidaCPFConsumer serverValidaCPF;

    @Test
    void Given_ValidCommand_Abl_Must_DelegateToHeadler(){

        //given
        var captor = ArgumentCaptor.forClass(UrnaInDto.class);
        var repository = mock(WriteUrnaRepositoryImpl.class);
        var handler = new CreateUrnaHandler(repository, serverValidaCPF);
        var command = new CreateUrnaCommand(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), Voto.FAVORAVEL);
        var actual = new EleitorDto(command.uuidUrna(), 1l, 1l, command.voto());
        actual.setCpf(new CPFHelper().cpf());
        when(repository.retrieveUrnaDto(any())).thenReturn(Optional.of(actual));
        var dto = mock(EleitorDto.class);
        when(serverValidaCPF.isAbleToVote(eq(dto.getCpf()))).thenReturn(true);

        //when
         handler.handle(command);

        //then
        verify(serverValidaCPF, timeout(1)).isAbleToVote(anyString());
        verify(repository, timeout(1)).retrieveUrnaDto(any());
        verify(repository, timeout(1)).create(captor.capture());
        var expected = captor.getValue();
        assertThat(actual.getUuid()).isEqualTo(expected.getUuid());
        assertThat(actual.getIdCooperado()).isEqualTo(expected.getIdCooperado());
        assertThat(actual.getIdSessao()).isEqualTo(expected.getIdSessao());
    }
}