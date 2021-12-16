package br.org.enascimento.assembleiacooperados.write.domain.application;

import br.org.enascimento.assembleiacooperados.write.domain.application.command.UpdateCooperadoCommand;
import br.org.enascimento.assembleiacooperados.write.domain.application.handler.UpdateCooperadoHandler;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCooperadoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.CooperadoUpdateInvalidException;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Tag("unit")
public class UpdateCooperadoHandlerTest extends TestHelper {

    private final WriteCooperadoRepository repository;
    private final UpdateCooperadoHandler handler;

    public UpdateCooperadoHandlerTest() {
        this.repository = mock(WriteCooperadoRepository.class);
        this.handler = new UpdateCooperadoHandler(repository);
    }

    @Test
    void GIVEN_InvalidCommandCooperado_MUST_TrowException(){

        //given
        var uuid = UUID.randomUUID();

        //when
        var exception = assertThrows(CooperadoUpdateInvalidException.class, () ->
                handler.handle(new UpdateCooperadoCommand(uuid, null, null)));

        //then
        //verify(repository, never()).findByUuid(uuid);
        assertThat(exception.getMessage()).isEqualTo("Cooperado not update");
    }

    @Test
    void Given_ValidCommand_Must_DelegateToHeadler(){
        //given
        var captor = ArgumentCaptor.forClass(Cooperado.class);
        var uuid = UUID.randomUUID();
        var nome = faker.name().fullName();
        var cpf = faker.number().digits(11);
        var command = new UpdateCooperadoCommand(uuid, nome, cpf);
        var cooperado = new Cooperado().setUuid(uuid).setNome(nome).setCpf(cpf);
        when(repository.findByUuidOrCpf(uuid, null)).thenReturn(Optional.of(cooperado));

        //when
        handler.handle(command);

        //then
        verify(repository).findByUuidOrCpf(uuid, null);
        verify(repository).update(captor.capture());
        var cooperadoCapter = cooperado;
        assertThat(cooperadoCapter.getUuid().equals(cooperado.getUuid())).isTrue();
        assertThat(cooperadoCapter.getNome().equals(cooperado.getNome())).isTrue();
        assertThat(cooperadoCapter.getCpf().equals(cooperado.getCpf())).isTrue();
    }
}
