package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateCooperadoCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCooperadoRepository;
import helper.GeradorCPF;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@Tag("unit")
class CreateCooperadoHandlerTest extends TestHelper {

    @Mock
    private WriteCooperadoRepository repository;
    @InjectMocks
    private CreateCooperadoHandler handler;

    @Test
    void DADO_CommandValida_DEVE_CriarCooperado() {
        var captor = ArgumentCaptor.forClass(Cooperado.class);
        var uuid = randomUUID();
        var nome = faker.name().fullName();
        var cpf = new GeradorCPF().gerar();
        var actual = new CreateCooperadoCommand(randomUUID(), nome, cpf);

        // when
        handler.handle(actual);

        //then
        verify(repository).create(captor.capture());
        var expected = captor.getValue();
        assertThat(expected.getUuid()).isEqualTo(actual.uuid());
        assertThat(expected.getNome()).isEqualTo(actual.nome());
        assertThat(expected.getCpf()).isEqualTo(actual.cpf());
    }
}
