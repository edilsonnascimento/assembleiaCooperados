package br.org.enascimento.assembleiacooperados.write.domain.application;

import br.org.enascimento.assembleiacooperados.write.adapter.out.WritePautaRepositoryImpl;
import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@Tag("unit")
public class CreatePautaCommandHandlerTest extends TestHelper {

    private ArgumentCaptor<Pauta> captor = ArgumentCaptor.forClass(Pauta.class);

    @Test
    void Given_ValidCommand_Must_DelegateToHeadler(){
        // given
        var uuid = UUID.randomUUID();
        var titulo = faker.lorem().characters();
        var descricao = faker.lorem().characters();
        var command = new CreatePautaCommand(uuid, titulo, descricao);
        var repository = mock(WritePautaRepositoryImpl.class);

        // when
        var handler = new CreatePautaCommandHandler(repository);
        handler.handle(command);

        //then
        verify(repository).create(captor.capture());
        var pauta = captor.getValue();
        assertThat(pauta.getUuid()).isEqualTo(uuid);
        assertThat(pauta.getTitulo()).isEqualTo(titulo);
        assertThat(pauta.getDescricao()).isEqualTo(descricao);
    }
}
