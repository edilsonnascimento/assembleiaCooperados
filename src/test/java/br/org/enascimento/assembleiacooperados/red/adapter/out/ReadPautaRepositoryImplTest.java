package br.org.enascimento.assembleiacooperados.red.adapter.out;

import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadPautaRepositoryImplTest extends DataSourceHelper {

    private ReadPautaRespositoryImpl repository;

    @BeforeEach
    void setup(){
        repository = new ReadPautaRespositoryImpl(dataSource);
    }

    @Test
    void WHEN_QueryAllPauta_MUST_RetriveSuccessful(){

        //given
        var expected = new PautaDto(UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20"),
                           "Título Existe",
                           "Descrição Existe");

        //when
        var actual = repository.findAll();

        //then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(List.of(expected));
    }
 }
