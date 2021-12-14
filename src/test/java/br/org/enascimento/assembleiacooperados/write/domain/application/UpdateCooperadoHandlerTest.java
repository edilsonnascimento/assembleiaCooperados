package br.org.enascimento.assembleiacooperados.write.domain.application;

import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCooperadoRepository;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Tag("unit")
public class UpdateCooperadoHandlerTest extends TestHelper {

    @Test
    void Given_ValidCommand_Must_DelegateToHeadler(){
        //given
        var captor = ArgumentCaptor.forClass(Cooperado.class);
        var uuid = UUID.randomUUID();
        var nome = faker.name().fullName();
        var cpf = faker.number().digits(11);
        var command = new UpdateCooperadoCommand(uuid, nome, cpf);
        var repository = mock(WriteCooperadoRepository.class);
        var cooperado = new Cooperado().setUuid(uuid).setNome(nome).setCpf(cpf);
        when(repository.findByUuidOrCpf(uuid, null)).thenReturn(Optional.of(cooperado));

        //when
        var handler = new UpdateCooperadoHandler(repository);
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
