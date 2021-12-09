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

import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class WritePautaRepositoryImplTest extends DataSourceHelper {

    private WritePautaRepositoryImpl repository;

    public WritePautaRepositoryImplTest() {
        dataSetName = "WritePautaRepositoryImplTest";
    }

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
        var id = UUID.fromString("3731c747-ea27-42e5-a52b-1dfbfa9617db");
        var actualPauta = repository.findByUuid(id).get();
        assertThat(actualPauta.getTitulo()).isEqualTo("TITULO-EXISTENTE");
        assertThat(actualPauta.getDescricao()).isEqualTo("DESCICAO-EXISTENTE");

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
        assertThat(expectedPauta.getCreatedAt()).isNotNull();
        assertThat(expectedPauta.getUpdatedAt()).isNotNull();
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

        UUID existenUuid = UUID.fromString("3731c747-ea27-42e5-a52b-1dfbfa9617db");
        String existenTitulo = "PRIMEIRO-TITULO";
        String validDescicao = "PRIMEIRA-DESCICAO";

        return Stream.of(
                arguments(UUID.randomUUID(), existenTitulo, validDescicao, Map.of("titulo", existenTitulo)),
                arguments(existenUuid, "Título valido", validDescicao, Map.of("uuid", existenUuid)),
                arguments(existenUuid, existenTitulo, validDescicao, Map.of("titulo", existenTitulo, "uuid", existenUuid))
        );
    }
}