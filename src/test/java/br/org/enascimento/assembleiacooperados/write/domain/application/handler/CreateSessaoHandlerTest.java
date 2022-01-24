package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadPautaRepository;
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

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Tag("unit")
public class CreateSessaoHandlerTest extends TestHelper {

    private CreateSessaoHandler handler;
    private WriteSessaoRepository repository;
    private ReadPautaRepository repositoryRead;

    public CreateSessaoHandlerTest() {
        this.repository = mock(WriteSessaoRepository.class);
        this.repositoryRead = mock(ReadPautaRepository.class);
        this.handler = new CreateSessaoHandler(repository, repositoryRead);
    }

    @Test
    void Given_ValidCreateSessaoCommand_Must_DelegateToHeadler(){
        var captor = ArgumentCaptor.forClass(Sessao.class);

        //given
        var limiteSessao = 5L;
        var dto = new SessaoInDto(UUID.randomUUID(), UUID.randomUUID(), limiteSessao);
        var expectedDate = LocalDateTime.now().plusMinutes(limiteSessao);
        var command = new CreateSessaoCommand(dto);
        var pauta = new Pauta()
                .setId(1l)
                .setUuid(UUID.randomUUID())
                .setTitulo("PRIMEIRO-TITULO")
                .setDescricao("PRIMEIRA-DESCICAO");
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
        var captor = ArgumentCaptor.forClass(Sessao.class);

        //given
        Long limiteSessao = null;
        var dto = new SessaoInDto(UUID.randomUUID(), UUID.randomUUID(), limiteSessao);
        var expectedDate = LocalDateTime.now().plusMinutes(1L);
        var command = new CreateSessaoCommand(dto);
        var pauta = new Pauta()
                .setId(1l)
                .setUuid(UUID.randomUUID())
                .setTitulo("PRIMEIRO-TITULO")
                .setDescricao("PRIMEIRA-DESCICAO");
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
}
