package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.CooperadoInDto;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCooperadoRepository;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadCooperadoRepositoryImplTest extends DataSourceHelper {

    private ReadCooperadoRepository repository;


    @BeforeEach
    void setup() {
        repository = new ReadCooperadoRepositoryImpl(dataSource);
    }

    @Test
    void WHEN_QueryAllCooperad_MUST_RetriveSuccessful(){

        //given
        List<CooperadoInDto> expected = List.of(
                new CooperadoInDto(UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20"), "NOME-EXISTENTE-1", "74656849359"),
                new CooperadoInDto(UUID.fromString("3731c747-ea27-42e5-a52b-1dfbfa9617db"), "NOME-EXISTENTE-2", "38176004707"));
        //when
        List<CooperadoInDto> actual = repository.findAll();

        //then
        assertThat(actual).containsExactlyElementsOf(expected);


    }
}
