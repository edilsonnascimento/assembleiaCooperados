package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.red.adapter.out.ReadPautaRepositoryImpl;
import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import br.org.enascimento.assembleiacooperados.write.domain.core.WritePautaRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class WritePautaRepositoryImplTest extends DataSourceHelper {

    private WritePautaRepositoryImpl repositoryWrite;
    private ReadPautaRepositoryImpl repositoryRead;

    @BeforeEach
    void setup() {
        repositoryRead = new ReadPautaRepositoryImpl(dataSource);
        repositoryWrite = new WritePautaRepositoryImpl(dataSource, repositoryRead);
    }

    @Test
    void MUST_ImplementInterface(){
        assertThat(repositoryWrite).isInstanceOf(WritePautaRepository.class);
    }

    @ParameterizedTest
    @MethodSource("validDataProvider")
    void WHEN_CreatingPauta_GIVEN_ValidData_MUST_PersistOnDatabase(UUID uuid,
                                               String titulo,
                                               String descricao) {
        //given
        var expected = new Pauta();
        expected.setUuid(uuid);
        expected.setTitulo(titulo);
        expected.setDescricao(descricao);

        //when
        repositoryWrite.create(expected);

        //then
        var createdPauta = repositoryRead.findByUuid(uuid);
        var actual = createdPauta.get();
        assertThat(actual.getUuid()).isEqualTo(expected.getUuid());
        assertThat(actual.getTitulo()).isEqualTo(expected.getTitulo());
        assertThat(actual.getDescricao()).isEqualTo(expected.getDescricao());
        assertThat(actual.getCreatedAt()).isNotNull();
        assertThat(actual.getUpdatedAt()).isNotNull();
    }

    @Test
    void WHEN_UpdatingPauta_WITH_ValidData_MUST_SaveOnDatabase() {
        // given
        var uuid = UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20");
        var actualPauta = repositoryRead.findByUuid(uuid).get();
        assertThat(actualPauta.getTitulo()).isEqualTo("PRIMEIRO-TITULO");
        assertThat(actualPauta.getDescricao()).isEqualTo("PRIMEIRA-DESCICAO");

        var titulo = faker.pokemon().name();
        var descricao = faker.lorem().characters(5, 100);
        actualPauta.setTitulo(titulo);
        actualPauta.setDescricao(descricao);

        // when
        repositoryWrite.update(actualPauta);

        // then
        var expectedPauta = repositoryRead.findByUuid(uuid).get();
        assertThat(expectedPauta.getUuid()).isEqualTo(uuid);
        assertThat(expectedPauta.getTitulo()).isEqualTo(titulo);
        assertThat(expectedPauta.getDescricao()).isEqualTo(descricao);
        assertThat(expectedPauta.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(expectedPauta.getUpdatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @ParameterizedTest
    @MethodSource("inValidDataProvider")
    void WHEN_CreatingPauta_GIVEN_AlreadyExistentKey_MUST_ThrowException(UUID uuid,
                                                      String titulo,
                                                      Map<String, Object> expectedError) {
        //given
        var expected = new Pauta();
        expected.setUuid(uuid);
        expected.setTitulo(titulo);
        expected.setDescricao(faker.lorem().characters(30));

        //when
        var exception = assertThrows(DuplicatedDataException.class, () -> repositoryWrite.create(expected));

        //then
        assertThat(exception.getMessage()).isEqualTo("Invalid duplicated data");
        assertThat(exception.getCode()).isEqualTo(1000);
        assertThat(exception.getErrors()).containsExactlyInAnyOrderEntriesOf(expectedError);
    }

    private static Stream<Arguments> validDataProvider() {
        return Stream.of(
                arguments(UUID.randomUUID(), "Titulo " + faker.pokemon().name() , "Descricao " + faker.pokemon().name()),
                arguments(UUID.randomUUID(), "Titulo " + faker.pokemon().name(), "Descricao " + faker.pokemon().name()),
                arguments(UUID.randomUUID(), "Titulo " + faker.pokemon().name(), "Descricao " + faker.pokemon().name())
        );
    }

    private static Stream<Arguments> inValidDataProvider() {

        UUID existenUuid = UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20");
        String existenTitulo = "PRIMEIRO-TITULO";

        return Stream.of(
                arguments(UUID.randomUUID(), existenTitulo, Map.of("titulo", existenTitulo)),
                arguments(existenUuid, "Título valido", Map.of("uuid", existenUuid)),
                arguments(existenUuid, existenTitulo, Map.of("titulo", existenTitulo, "uuid", existenUuid))
        );
    }
}