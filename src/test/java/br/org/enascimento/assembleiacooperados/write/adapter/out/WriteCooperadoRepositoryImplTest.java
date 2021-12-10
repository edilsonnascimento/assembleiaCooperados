package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import br.org.enascimento.assembleiacooperados.write.domain.exception.DuplicatedDataException;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

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

    @ParameterizedTest
    @MethodSource("inValidDataProvider")
    void WHEN_CreatingCooperado_GIVEN_AlreadyExistentCooperado_MUST_ThrowException(UUID uuid,
                                                                           String nome,
                                                                           String cpf,
                                                                           Map<String, Object> expectedError) {
        //given
        var expected = new Cooperado()
                .setUuid(uuid)
                .setNome(nome)
                .setCpf(cpf);

        //when
        var exception = assertThrows(DuplicatedDataException.class, () -> repository.create(expected));

        //then
        assertThat(exception.getMessage()).isEqualTo("Invalid duplicated data");
        assertThat(exception.getErrors()).containsExactlyInAnyOrderEntriesOf(expectedError);
    }

    private static Stream<Arguments> inValidDataProvider() {

        UUID existenteUuid = UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20");
        String existenteCpf = "74656849359";

        return Stream.of(
                arguments(existenteUuid, faker.name().fullName(), "45777302190", Map.of("uuid", existenteUuid)),
                arguments(UUID.randomUUID(), faker.name().fullName(), existenteCpf, Map.of("cpf", existenteCpf)),
                arguments(existenteUuid, faker.name().fullName(), existenteCpf, Map.of("cpf", existenteCpf, "uuid", existenteUuid))
        );
    }
}