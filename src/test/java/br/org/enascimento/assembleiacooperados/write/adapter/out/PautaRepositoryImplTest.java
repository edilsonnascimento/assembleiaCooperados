package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import br.org.enascimento.assembleiacooperados.write.domain.core.PautaRepository;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PautaRepositoryImplTest extends DataSourceHelper {

    private PautaRepository repository;

    @BeforeEach
    void setup(){
        repository = new PautaRepositoryImpl(dataSource);
    }

    @ParameterizedTest
    @MethodSource("validDataProvider")
    void GIVEN_ValidPauta_MUST_PersistDataBase(UUID uuid,
                                               String titulo,
                                               String descricao){
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

    @ParameterizedTest
    @MethodSource("inValidDataProvider")
    void GIVEN_AlreadyExtentPauta_MUST_ThowExcepetion(UUID uuid,
                                               String titulo,
                                               String descricao){
        //given
        var pauta = new Pauta()
                .setUuid(uuid)
                .setTitulo(titulo)
                .setDescricao(descricao);

        //then
        assertThrows(RuntimeException.class, ()-> repository.create(pauta));
    }

    @Test
    void GIVEN_AlredyExistePauta_MUST_ThrowException(){
        //given
        var pauta = new Pauta()
                .setId(1)
                .setUuid(UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20"))
                .setTitulo("Título outro")
                .setDescricao("Descrição outra");

        //then
        assertThrows(RuntimeException.class, () -> repository.create(pauta));
    }

    private static Stream<Arguments> validDataProvider(){
        return Stream.of(
            arguments(UUID.randomUUID(), "Título 1", "Descricao 1"),
            arguments(UUID.randomUUID(), "Título 2", "Descricao 2"),
            arguments(UUID.randomUUID(), "Título 3", "Descricao 3")
        );
    }

    private static Stream<Arguments> inValidDataProvider(){
        return Stream.of(
                arguments(UUID.randomUUID(), "Título Existe", "Descrição Existe"),
                arguments("1e73cdb3-0923-4452-a190-3c7eb7857e20", "Título Não Existe", "Descrição não Existe")
        );
    }

}