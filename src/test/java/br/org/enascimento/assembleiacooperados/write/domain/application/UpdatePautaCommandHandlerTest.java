package br.org.enascimento.assembleiacooperados.write.domain.application;

import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import br.org.enascimento.assembleiacooperados.write.domain.core.WritePautaRepository;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
                .setId(1)
                .setUuid(uuid)
                .setTitulo("TITULO-ATUAL")
                .setDescricao("DESCRICAO-ATUAL");
        when(repository.findByUuid(uuid)).thenReturn(Optional.of(pautaMock));

        // when
        var handler = new UpdatePautaCommandHandler(repository);
        handler.handle(command);

        // then
        verify(repository).findByUuid(uuid);
        verify(repository).update(pautaMock);
        assertThat(pautaMock.getTitulo()).isEqualTo(titulo);
        assertThat(pautaMock.getDescricao()).isEqualTo(descricao);
    }
}
