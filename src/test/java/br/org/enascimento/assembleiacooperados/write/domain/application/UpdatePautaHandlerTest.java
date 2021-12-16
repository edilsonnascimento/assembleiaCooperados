package br.org.enascimento.assembleiacooperados.write.domain.application;

import br.org.enascimento.assembleiacooperados.write.domain.application.command.UpdatePautaCommand;
import br.org.enascimento.assembleiacooperados.write.domain.application.handler.UpdatePautaHandler;
import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import br.org.enascimento.assembleiacooperados.write.domain.core.WritePautaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.PautaNotExistentException;
import br.org.enascimento.assembleiacooperados.write.domain.exception.PautaUpdateInvalidException;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Tag("unit")
public class UpdatePautaHandlerTest extends TestHelper {

    private final WritePautaRepository repository;
    private final UpdatePautaHandler handler;

    public UpdatePautaHandlerTest() {
        repository = mock(WritePautaRepository.class);
        handler = new UpdatePautaHandler(repository);
    }

    @Test
    void GIVEN_InvalidCommandPauta_MUST_TrowException(){

        //given
        var uuid = UUID.randomUUID();
        var command = new UpdatePautaCommand(uuid, null, null);

        //when
        var exceptionExpected =
                assertThrows(PautaUpdateInvalidException.class, ()-> handler.handle(command));

        //then
        verify(repository, never()).findByUuid(uuid);
        assertThat(exceptionExpected.getMessage()).isEqualTo("Pauta not update");
    }

    @Test
    void GIVEN_NotExistPauta_MUST_ThrowException() {

        // given
        var titulo = faker.team().name();
        var descricao = faker.lorem().characters();
        var uuid = UUID.randomUUID();
        var command = new UpdatePautaCommand(uuid, titulo, descricao);
        when(repository.findByUuid(any())).thenReturn(Optional.empty());
        // when
        var exceptionExpected =
                assertThrows(PautaNotExistentException.class, ()-> handler.handle(command));

        // then
        verify(repository).findByUuid(uuid);
        assertThat(exceptionExpected.getMessage()).isEqualTo("Pauta not exist");
    }

    @Test
    void GIVEN_ValidCommand_MUST_UpdatePautaTitulo_DelegateRepository() {

        // given
        var titulo = "TITULO-NOVO";
        var uuid = UUID.fromString("3731c747-ea27-42e5-a52b-1dfbfa9617db");
        var command = new UpdatePautaCommand(uuid, titulo, null);
        var pautaMock = new Pauta()
                .setId(1l)
                .setUuid(uuid)
                .setTitulo("TITULO-ATUAL")
                .setDescricao("DESCRICAO-ATUAL");
        when(repository.findByUuid(uuid)).thenReturn(Optional.of(pautaMock));

        // when
        handler.handle(command);

        // then
        verify(repository).findByUuid(uuid);
        verify(repository).update(pautaMock);
        assertThat(pautaMock.getTitulo()).isEqualTo(titulo);
        assertThat(pautaMock.getDescricao()).isEqualTo("DESCRICAO-ATUAL");
    }

    @Test
    void GIVEN_ValidCommand_MUST_UpdatePautaDescricao_DelegateRepository() {

        // given
        var descricao = "DESCRICAO-NOVA";
        var uuid = UUID.fromString("3731c747-ea27-42e5-a52b-1dfbfa9617db");
        var command = new UpdatePautaCommand(uuid, null, descricao);
        var pautaMock = new Pauta()
                .setId(1l)
                .setUuid(uuid)
                .setTitulo("TITULO-ATUAL")
                .setDescricao("DESCRICAO-ATUAL");
        when(repository.findByUuid(uuid)).thenReturn(Optional.of(pautaMock));

        // when
        handler.handle(command);

        // then
        verify(repository).findByUuid(uuid);
        verify(repository).update(pautaMock);
        assertThat(pautaMock.getTitulo()).isEqualTo("TITULO-ATUAL");
        assertThat(pautaMock.getDescricao()).isEqualTo(descricao);
    }

    @Test
    void GIVEN_ValidCommand_MUST_UpdatePauta_DelegateRepository() {

        // given
        var titulo = "TITULO-NOVO";
        var descricao = "DESCRICAO-NOVA";
        var uuid = UUID.fromString("3731c747-ea27-42e5-a52b-1dfbfa9617db");
        var command = new UpdatePautaCommand(uuid, titulo, descricao);
        var pautaMock = new Pauta()
                .setId(1l)
                .setUuid(uuid)
                .setTitulo("TITULO-ATUAL")
                .setDescricao("DESCRICAO-ATUAL");
        when(repository.findByUuid(uuid)).thenReturn(Optional.of(pautaMock));

        // when
        handler.handle(command);

        // then
        verify(repository).findByUuid(uuid);
        verify(repository).update(pautaMock);
        assertThat(pautaMock.getTitulo()).isEqualTo(titulo);
        assertThat(pautaMock.getDescricao()).isEqualTo(descricao);
    }

}
