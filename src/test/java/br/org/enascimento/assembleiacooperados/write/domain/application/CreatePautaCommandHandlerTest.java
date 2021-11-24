package br.org.enascimento.assembleiacooperados.write.domain.application;

import br.org.enascimento.assembleiacooperados.write.adapter.out.PautaRepositoryImpl;
import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreatePautaCommandHandlerTest {

    ArgumentCaptor<Pauta> captor = ArgumentCaptor.forClass(Pauta.class);

    @Test
    void Given_ValidCommand_Must_DelegateToHeadler(){
        // given
        var uuid = UUID.randomUUID();
        var titulo = "Aumento da segurança";
        var descricao = "Contratar 5 seguranças fixos.";
        var command = new CreatePautaCommand(uuid, titulo, descricao);
        var repository = mock(PautaRepositoryImpl.class);

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
