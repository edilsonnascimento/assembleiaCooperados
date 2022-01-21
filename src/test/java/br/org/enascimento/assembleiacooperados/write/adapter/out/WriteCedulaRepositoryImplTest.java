package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CedulaInDto;
import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCedulaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.DuplicatedDataException;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class WriteCedulaRepositoryImplTest extends DataSourceHelper {

    private WriteCedulaRepository repository;

    @BeforeEach
    void setup() {
        repository = new WriteCedulaRepositoryImpl(dataSource);
    }

    @Test
    void WHEN_CreatingUrna_GIVEN_ValidData_MUST_PersistOnDatabase() {
        //given
        var uuidUrna = UUID.fromString("3b54e51d-6340-47b3-a15d-0d28e4272551");
        var dto = new CedulaInDto(
                uuidUrna,
                2l,
                1L,
                Voto.FAVORAVEL);

        //when
        boolean retorno = repository.create(dto);

        //then
        assertThat(retorno).isTrue();
    }

    @ParameterizedTest
    @MethodSource("inValidDataProvider")
    void WHEN_CreatingUrna_GIVEN_AlreadyExistentKey_MUST_ThrowException(UUID uuid,
                                                                         Long idSessao,
                                                                         Long idCooperado,
                                                                         Voto voto,
                                                                        Map<String, UUID> expectedError) {
        //given
        var expected = new CedulaInDto(
                uuid,
                idSessao,
                idCooperado,
                voto);

        //when
        var exception = assertThrows(DuplicatedDataException.class,
                () -> repository.create(expected));

        //then
        assertThat(exception.getMessage()).isEqualTo("Invalid duplicated data");
        assertThat(exception.getCode()).isEqualTo(1000);
        assertThat(exception.getErrors()).containsExactlyInAnyOrderEntriesOf(expectedError);
    }

    private static Stream<Arguments> inValidDataProvider() {

        var existenUuid = UUID.fromString("0d28786f-8dbd-41f7-8a77-59ea8bed7d8c");
        var uuidNovo = UUID.randomUUID();
        var idSessaoExistente = 1L;
        var idCooperadoExistente = 1L;
        var voto = Voto.FAVORAVEL;


        return Stream.of(
                arguments(existenUuid, 2L, idCooperadoExistente, voto, Map.of("uuid", existenUuid)),
                arguments(uuidNovo, idSessaoExistente, idCooperadoExistente, voto, Map.of("cooperado", uuidNovo))
        );
    }

}
