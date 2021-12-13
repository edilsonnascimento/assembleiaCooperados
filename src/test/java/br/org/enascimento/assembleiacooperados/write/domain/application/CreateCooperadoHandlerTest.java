package br.org.enascimento.assembleiacooperados.write.domain.application;

import br.org.enascimento.assembleiacooperados.write.adapter.out.WriteCooperadoRepositoryImpl;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@Tag("unit")
public class CreateCooperadoHandlerTest extends TestHelper {

    @Test
    void GIVEN_ValidCommand_MUST_CreateCooperado() {
        var captor = ArgumentCaptor.forClass(Cooperado.class);
        var uuid = randomUUID();
        var nome = faker.name().fullName();
        var cpf = faker.number().digits(11);
        var command = new CreateCooperadoCommand(uuid, nome, cpf);
        var repository = mock(WriteCooperadoRepositoryImpl.class);

        // when
        var handler = new CreateCooperadoHandler(repository);
        handler.handle(command);

        //then
        verify(repository).create(captor.capture());
        var cooperado = captor.getValue();
        assertThat(cooperado.getUuid()).isEqualTo(uuid);
        assertThat(cooperado.getNome()).isEqualTo(nome);
        assertThat(cooperado.getCpf()).isEqualTo(cpf);

    }

}
