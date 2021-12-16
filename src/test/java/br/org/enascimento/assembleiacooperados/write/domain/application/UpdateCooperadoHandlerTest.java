package br.org.enascimento.assembleiacooperados.write.domain.application;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCooperadoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.UpdateCooperadoCommand;
import br.org.enascimento.assembleiacooperados.write.domain.application.handler.UpdateCooperadoHandler;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCooperadoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.CooperadoNotExistentException;
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

    private WriteCooperadoRepository repositoryWrite;
    private ReadCooperadoRepository repositoryRead;
    private final UpdateCooperadoHandler handler;
    private final ArgumentCaptor<Cooperado> captor = ArgumentCaptor.forClass(Cooperado.class);

    public UpdateCooperadoHandlerTest() {
        this.repositoryWrite = mock(WriteCooperadoRepository.class);
        this.repositoryRead = mock(ReadCooperadoRepository.class);
        this.handler = new UpdateCooperadoHandler(repositoryWrite, repositoryRead);
    }

    @Test
    void GIVEN_InvalidCommandCooperado_MUST_TrowException(){

        //given
        var uuid = UUID.randomUUID();

        //when
        var exception = assertThrows(CooperadoUpdateInvalidException.class, () ->
                handler.handle(new UpdateCooperadoCommand(uuid, null, null)));

        //then
        verify(repositoryRead, never()).findByUuid(uuid);
        verify(repositoryWrite, never()).update(any());
        assertThat(exception.getMessage()).isEqualTo("Cooperado not update");
    }

    @Test
    void WHEN_CooperadoNotExistent_MUST_TrowException(){

        //given
        var uuid = UUID.randomUUID();
        var nome = faker.name().fullName();
        var cpf = faker.number().digits(11);

        //when
        var exception = assertThrows(CooperadoNotExistentException.class, () ->
                handler.handle(new UpdateCooperadoCommand(uuid, nome, cpf)));

        //then
        verify(repositoryRead, times(1)).findByUuid(uuid);
        verify(repositoryWrite, never()).update(any());
        assertThat(exception.getMessage()).isEqualTo("Cooperado not exist");
    }

    @Test
    void Given_ValidCommand_Must_DelegateToHeadler(){
        //given
        var uuid = UUID.randomUUID();
        var nome = faker.name().fullName();
        var cpf = faker.number().digits(11);
        var nomeAlterado = "NOVO-NOME";
        var cpfAlterado = "00000000000";
        var command = new UpdateCooperadoCommand(uuid, nomeAlterado, cpfAlterado);
        var cooperado = new Cooperado().setUuid(uuid).setNome(nome).setCpf(cpf);
        when(repositoryRead.findByUuid(uuid)).thenReturn(Optional.of(cooperado));

        //when
        handler.handle(command);

        //then
        verify(repositoryRead, timeout(1)).findByUuid(uuid);
        verify(repositoryWrite, times(1)).update(captor.capture());
        var cooperadoCapter = captor.getValue();
        assertThat(cooperadoCapter.getUuid()).isEqualTo(cooperado.getUuid());
        assertThat(cooperadoCapter.getNome()).isEqualTo(nomeAlterado);
        assertThat(cooperadoCapter.getCpf()).isEqualTo(cpfAlterado);
    }

    @Test
    void Given_ValidCommandOnlyNome_Must_DelegateToHeadler(){
        //given
        var uuid = UUID.randomUUID();
        var nome = faker.name().fullName();
        var cpf = faker.number().digits(11);
        var nomeAlterado = "NOVO-NOME";
        String cpfAlterado = null;
        var command = new UpdateCooperadoCommand(uuid, nomeAlterado, cpfAlterado);
        var cooperado = new Cooperado().setUuid(uuid).setNome(nome).setCpf(cpf);
        when(repositoryRead.findByUuid(uuid)).thenReturn(Optional.of(cooperado));

        //when
        handler.handle(command);

        //then
        verify(repositoryRead, timeout(1)).findByUuid(uuid);
        verify(repositoryWrite, times(1)).update(captor.capture());
        var cooperadoCapter = captor.getValue();
        assertThat(cooperadoCapter.getUuid()).isEqualTo(cooperado.getUuid());
        assertThat(cooperadoCapter.getNome()).isEqualTo(nomeAlterado);
        assertThat(cooperadoCapter.getCpf()).isEqualTo(cpf);
        assertThat(cooperadoCapter.getUpdatedAt()).isNotNull();
    }

    @Test
    void Given_ValidCommandOnlyCpf_Must_DelegateToHeadler(){
        //given
        var uuid = UUID.randomUUID();
        var nome = faker.name().fullName();
        var cpf = faker.number().digits(11);
        String nomeAlterado = null;
        var cpfAlterado = "00000000000";
        var command = new UpdateCooperadoCommand(uuid, nomeAlterado, cpfAlterado);
        var cooperado = new Cooperado().setUuid(uuid).setNome(nome).setCpf(cpf);
        when(repositoryRead.findByUuid(uuid)).thenReturn(Optional.of(cooperado));

        //when
        handler.handle(command);

        //then
        verify(repositoryRead, timeout(1)).findByUuid(uuid);
        verify(repositoryWrite, times(1)).update(captor.capture());
        var cooperadoCapter = captor.getValue();
        assertThat(cooperadoCapter.getUuid()).isEqualTo(cooperado.getUuid());
        assertThat(cooperadoCapter.getNome()).isEqualTo(nome);
        assertThat(cooperadoCapter.getCpf()).isEqualTo(cpfAlterado);
        assertThat(cooperadoCapter.getUpdatedAt()).isNotNull();
    }

}
