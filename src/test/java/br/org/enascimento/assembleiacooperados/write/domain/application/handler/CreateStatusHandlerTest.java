package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.adapter.out.WriteStatusRepositoryImpl;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateStatusCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Status;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@Tag("unit")
class CreateStatusHandlerTest extends TestHelper {

    @Mock
    private WriteStatusRepositoryImpl repository;
    @InjectMocks
    private CreateStatusHandler handler;

    @Test
    void Given_ValidCommand_Must_DelegateToHeadler(){
        // given
        var captor = ArgumentCaptor.forClass(Status.class);
        var descricao = faker.lorem().characters(50);
        var command = new CreateStatusCommand(descricao);

        // when
        handler.handle(command);

        //then
        verify(repository).create(captor.capture());
        var expected = captor.getValue();
        assertThat(descricao).isEqualTo(expected.getDescricao());
    }
}
