package br.org.enascimento.assembleiacooperados.write.adapter.out;

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

    private WritePautaRepositoryImpl repository;

    @BeforeEach
    void setup() {
        repository = new WritePautaRepositoryImpl(dataSource);
    }

    @Test
    void MUST_ImplementInterface(){
        assertThat(repository).isInstanceOf(WritePautaRepository.class);
    }

    @ParameterizedTest
    @MethodSource("validDataProvider")
    void WHEN_CreatingPauta_GIVEN_ValidData_MUST_PersistOnDatabase(UUID uuid,
                                               String titulo,
                                               String descricao) {
        //given
        var expected = new Pauta()
                .setUuid(uuid)
                .setTitulo(titulo)
                .setDescricao(descricao);

        //when
        repository.create(expected);

        //then
        var createdPauta = repository.findByUuid(uuid);
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
        var id = UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20");
        var actualPauta = repository.findByUuid(id).get();
        assertThat(actualPauta.getTitulo()).isEqualTo("PRIMEIRO-TITULO");
        assertThat(actualPauta.getDescricao()).isEqualTo("PRIMEIRA-DESCICAO");

        var titulo = faker.pokemon().name();
        var descricao = faker.lorem().characters(5, 100);
        actualPauta.setTitulo(titulo).setDescricao(descricao);

        // when
        repository.update(actualPauta);

        // then
        var expectedPauta = repository.findByUuid(id).get();
        assertThat(expectedPauta.getUuid()).isEqualTo(id);
        assertThat(expectedPauta.getTitulo()).isEqualTo(titulo);
        assertThat(expectedPauta.getDescricao()).isEqualTo(descricao);
        assertThat(expectedPauta.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(expectedPauta.getUpdatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @ParameterizedTest
    @MethodSource("inValidDataProvider")
    void WHEN_CreatingPauta_GIVEN_AlreadyExistentPauta_MUST_ThrowException(UUID uuid,
                                                      String titulo,
                                                      String descricao,
                                                      Map<String, Object> expectedError) {
        //given
        var expected = new Pauta()
                .setUuid(uuid)
                .setTitulo(titulo)
                .setDescricao(descricao);



        //when
        var exception = assertThrows(DuplicatedDataException.class, () -> repository.create(expected));

        //then
        assertThat(exception.getMessage()).isEqualTo("Invalid duplicated data");
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
        String validDescicao = "PRIMEIRA-DESCICAO";

        return Stream.of(
                arguments(UUID.randomUUID(), existenTitulo, validDescicao, Map.of("titulo", existenTitulo)),
                arguments(existenUuid, "Título valido", validDescicao, Map.of("uuid", existenUuid)),
                arguments(existenUuid, existenTitulo, validDescicao, Map.of("titulo", existenTitulo, "uuid", existenUuid))
        );
    }
}