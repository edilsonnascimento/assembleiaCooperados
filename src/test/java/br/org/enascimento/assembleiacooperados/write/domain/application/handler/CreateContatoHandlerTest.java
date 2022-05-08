package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateContatoCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Operadora;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteContatoRepository;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("unit")
public class CreateContatoHandlerTest extends TestHelper {

    @InjectMocks
    private CreateContatoHandler handler;
    @Mock
    private WriteContatoRepository repository;

    @Test
    void DADO_CreateContatoCommand_VALIDO_Criar_Contato() {
        //given
        var command = new CreateContatoCommand(new BigDecimal("4135253225"), Operadora.GVT, "Contato");
        when(repository.create(any())).thenReturn(true);

        //when
        handler.handle(command);

        //then
        verify(repository, times(1)).create(any());
    }
}