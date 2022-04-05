package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadStatusRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.StatusNotExistedException;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.UpdateStatusCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Status;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteStatusRepositoy;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Tag("unit")
class UpdateStatusHandlerTest extends TestHelper {

    @Mock
    private WriteStatusRepositoy repositoy;
    @Mock
    private ReadStatusRepository repositoyRead;
    @InjectMocks
    private UpdateStatusHandler handler;

    @Test
    void Given_ValidCommand_Must_DelegateToRepository(){
        //given
        var captor = ArgumentCaptor.forClass(Status.class);
        var command = new UpdateStatusCommand(1l, "NOVO STATUS");
        var status = new Status()
                .setId(1l)
                .setDescricao("ABERTA");
        when(repositoyRead.findById(command.id())).thenReturn(Optional.of(status));

        //when
        handler.handle(command);

        //then
        verify(repositoyRead, timeout(1)).findById(command.id());
        verify(repositoy, timeout(1)).update(captor.capture());
        var expected = captor.getValue();
        assertThat(expected.getId()).isEqualTo(status.getId());
        assertThat(expected.getDescricao()).isEqualTo(command.descricao());
    }

    @Test
    void Given_InValidCommand_Must_ReturnException(){
        //given
        var command = new UpdateStatusCommand(1l, "NOVO STATUS");
        when(repositoyRead.findById(command.id())).thenReturn(Optional.empty());

        //when
        var expection = assertThrows(StatusNotExistedException.class, ()->
            handler.handle(command));

        //then
        verify(repositoyRead, timeout(1)).findById(command.id());
        verify(repositoy, never()).update(any());
    }
}