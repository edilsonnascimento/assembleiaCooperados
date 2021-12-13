package br.org.enascimento.assembleiacooperados.write.domain.application;

import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import br.org.enascimento.assembleiacooperados.write.domain.core.WritePautaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.PautaNotExistentException;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Tag("unit")
public class UpdatePautaCommandHandlerTest extends TestHelper {

    @Test
    void GIVEN_ValidCommand_MUST_UpdatePautaInDatabase() {

        // given
        var titulo = "TITULO-NOVO";
        var descricao = "DESCRICAO-NOVA";
        var uuid = UUID.fromString("3731c747-ea27-42e5-a52b-1dfbfa9617db");
        var command = new UpdatePautaCommand(uuid, titulo, descricao);
        var repository = mock(WritePautaRepository.class);
        var pautaMock = new Pauta()
                .setId(1l)
                .setUuid(uuid)
                .setTitulo("TITULO-ATUAL")
                .setDescricao("DESCRICAO-ATUAL");
        when(repository.findByUuid(uuid)).thenReturn(Optional.of(pautaMock));

        // when
        var handler = new UpdatePautaHandler(repository);
        handler.handle(command);

        // then
        verify(repository).findByUuid(uuid);
        verify(repository).update(pautaMock);
        assertThat(pautaMock.getTitulo()).isEqualTo(titulo);
        assertThat(pautaMock.getDescricao()).isEqualTo(descricao);
    }

    @Test
    void GIVEN_NotExistPauta_MUST_ThrowException() {

        // given
        var titulo = faker.team().name();
        var descricao = faker.lorem().characters();
        var uuid = UUID.randomUUID();
        var command = new UpdatePautaCommand(uuid, titulo, descricao);
        var repository = mock(WritePautaRepository.class);
        when(repository.findByUuid(any())).thenReturn(Optional.empty());
        // when
        var handler = new UpdatePautaHandler(repository);
        var exceptionExpected =
                assertThrows(PautaNotExistentException.class, ()-> handler.handle(command));

        // then
        verify(repository).findByUuid(uuid);
        assertThat(exceptionExpected.getMessage()).isEqualTo("Pauta not exist");
    }
}
