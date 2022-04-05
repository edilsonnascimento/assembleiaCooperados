package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreatePautaCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import br.org.enascimento.assembleiacooperados.write.domain.core.WritePautaRepository;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@Tag("unit")
class CreatePautaHandlerTest extends TestHelper {

    @Mock
    private WritePautaRepository repository;
    @InjectMocks
    private CreatePautaHandler handler;

    @Test
    void Given_ValidCommand_Must_DelegateToHeadler(){
        // given
        var captor = ArgumentCaptor.forClass(Pauta.class);
        var uuid = UUID.randomUUID();
        var titulo = faker.lorem().characters();
        var descricao = faker.lorem().characters();
        var command = new CreatePautaCommand(uuid, titulo, descricao);

        // when
        handler.handle(command);

        //then
        verify(repository).create(captor.capture());
        var pauta = captor.getValue();
        assertThat(pauta.getUuid()).isEqualTo(uuid);
        assertThat(pauta.getTitulo()).isEqualTo(titulo);
        assertThat(pauta.getDescricao()).isEqualTo(descricao);
    }
}
