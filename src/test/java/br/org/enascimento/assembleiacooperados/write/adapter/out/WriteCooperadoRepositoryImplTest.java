package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.red.adapter.out.ReadCooperadoRepositoryImpl;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCooperadoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCooperadoRepository;
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

import static br.org.enascimento.assembleiacooperados.common.DataUtils.isMesmaData;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class WriteCooperadoRepositoryImplTest extends DataSourceHelper {

    private WriteCooperadoRepository repositoryWrite;
    private ReadCooperadoRepository repositoryRead;

    @BeforeEach
    void setup() {
        repositoryRead = new ReadCooperadoRepositoryImpl(dataSource);
        repositoryWrite = new WriteCooperadoRepositoryImpl(dataSource, repositoryRead);
    }

    @Test
    void WHEN_CreatingCooperado_GIVEN_ValidData_MUST_PersistOnDatabase() {
        //given
        LocalDateTime otherDate = LocalDateTime.now();
        var uuid = randomUUID();
        var nome = faker.name().fullName();
        var cpf = faker.number().digits(11);
        var expected = new Cooperado()
                .setUuid(uuid)
                .setNome(nome)
                .setCpf(cpf);

        //when
        repositoryWrite.create(expected);

        //then
        var createdCooperado = repositoryRead.findByUuidOrCpf(uuid, null);
        var actual = createdCooperado.get();
        assertThat(isMesmaData(actual.getCreatedAt(), otherDate)).isTrue();
        assertThat(isMesmaData(actual.getCreatedAt(), otherDate)).isTrue();
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
        var exception = assertThrows(DuplicatedDataException.class, () -> repositoryWrite.create(expected));

        //then
        assertThat(exception.getMessage()).isEqualTo("Invalid duplicated data");
        assertThat(exception.getErrors()).containsExactlyInAnyOrderEntriesOf(expectedError);
    }

    @Test
    void GIVEN_UpdatedValidPayload_MUST_ReturnSuccess(){
        //given
        var uuid = UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20");
        var nomeActual = "NOME-EXISTENTE-1";
        var cpfActual = "55595585080";
        var actualCooperado = repositoryRead.findByUuidOrCpf(uuid, cpfActual).get();
        assertThat(actualCooperado.getUuid()).isEqualTo(uuid);
        assertThat(actualCooperado.getNome()).isEqualTo(nomeActual);
        assertThat(actualCooperado.getCpf()).isEqualTo(cpfActual);

        var nomeExpected = "NOVO NOME";
        var cpfExpected = "00000000000";
        var cooperado = new Cooperado()
                .setUuid(uuid)
                .setCpf(cpfExpected)
                .setNome(nomeExpected);

        //when
        repositoryWrite.update(cooperado);

        //then
        var expectedCooperado = repositoryRead.findByUuid(uuid).get();
        assertThat(expectedCooperado.getNome()).isEqualTo(nomeExpected);
        assertThat(expectedCooperado.getCpf()).isEqualTo(cpfExpected);

    }
    private static Stream<Arguments> inValidDataProvider() {

        UUID existenteUuid = UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20");
        String existenteCpf = "55595585080";

        return Stream.of(
                arguments(existenteUuid, faker.name().fullName(), "45777302190", Map.of("uuid", existenteUuid)),
                arguments(UUID.randomUUID(), faker.name().fullName(), existenteCpf, Map.of("cpf", existenteCpf)),
                arguments(existenteUuid, faker.name().fullName(), existenteCpf, Map.of("cpf", existenteCpf, "uuid", existenteUuid))
        );
    }
}