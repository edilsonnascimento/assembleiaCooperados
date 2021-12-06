package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.domain.core.PautaDto;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadPautaRepository;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class ReadPautaRepositoryImplTest extends DataSourceHelper {

    private ReadPautaRepositoryImpl repository;

    public ReadPautaRepositoryImplTest() {
        dataSetName = "ReadPautaRepositoryImplTest";
    }

    @BeforeEach
    void setup() {
        repository = new ReadPautaRepositoryImpl(dataSource);
    }

    @Test
    void MUST_ImplementInterface() {
        assertThat(repository).isInstanceOf(ReadPautaRepository.class);
    }

    @Test
    void WHEN_QueryAllPauta_MUST_RetriveSuccessful(){
        //given
        var expected = List.of(
                                    new PautaDto(UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20"), "PRIMEIRO-TITULO", "PRIMEIRA-DESCICAO"),
                                    new PautaDto(UUID.fromString("3731c747-ea27-42e5-a52b-1dfbfa9617db"), "SEGUNDO-TITULO", "SEGUNDA-DESCICAO"));

        //when
        var actual = repository.findAll();

        //then
        assertThat(actual).containsExactlyElementsOf(expected);
    }
 }
