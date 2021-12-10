package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

class WriteCooperadoRepositoryImplTest extends DataSourceHelper {

    private WriteCooperadoRepositoryImpl repository;

    @BeforeEach
    void setup() {
        repository = new WriteCooperadoRepositoryImpl(dataSource);
    }

    @Test
    void WHEN_CreatingCooperado_GIVEN_ValidData_MUST_PersistOnDatabase() {
        //given
        var uuid = randomUUID();
        var nome = faker.name().fullName();
        var cpf = faker.number().digits(11);
        var expected = new Cooperado()
                .setUuid(uuid)
                .setNome(nome)
                .setCpf(cpf);

        //when
        repository.create(expected);

        //then
        var createdCooperado = repository.findByUuid(uuid);
        var actual = createdCooperado.get();
        assertThat(actual.getUuid()).isEqualTo(expected.getUuid());
        assertThat(actual.getNome()).isEqualTo(expected.getNome());
        assertThat(actual.getCpf()).isEqualTo(expected.getCpf());
        assertThat(actual.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(actual.getUpdatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }
}