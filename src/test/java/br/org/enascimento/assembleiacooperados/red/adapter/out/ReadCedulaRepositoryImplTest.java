package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.CedulaOutDto;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCedulaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cedula;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static br.org.enascimento.assembleiacooperados.write.domain.core.Voto.FAVORAVEL;
import static java.util.UUID.fromString;
import static org.assertj.core.api.Assertions.assertThat;

class ReadCedulaRepositoryImplTest extends DataSourceHelper {

    private ReadCedulaRepository repository;

    @BeforeEach
    void setup() {
        repository = new ReadCedulaRepositoryImpl(dataSource);
    }


    @Test
    void WHEN_QueryFindCedulaUiid_MUST_RetrieveSuccessful(){

        //given
        var data = LocalDateTime.of(2021,12,8, 5,55, 0);
        var actual = new Cedula();
        actual.setId(1L);
        actual.setUuid(fromString("0d28786f-8dbd-41f7-8a77-59ea8bed7d8c"));
        actual.setIdSessao(1L);
        actual.setIdCoopereado(1L);
        actual.setVoto(FAVORAVEL);
        actual.setCreatedAt(data);
        actual.setUpdatedAt(data);

        //then
        var expected = repository.findByUuid(actual.getUuid()).get();

        //when
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getUuid()).isEqualTo(expected.getUuid());
        assertThat(actual.getIdCoopereado()).isEqualTo(expected.getIdCoopereado());
        assertThat(actual.getIdSessao()).isEqualTo(expected.getIdSessao());
        assertThat(actual.getVoto()).isEqualTo(expected.getVoto());
        assertThat(actual.getCreatedAt()).isEqualTo(expected.getCreatedAt().withNano(0));
        assertThat(actual.getUpdatedAt()).isEqualTo(expected.getUpdatedAt().withNano(0));
    }

    @Test
    void WHEN_QueryAllCedulas_MUST_RetriveSuccessful(){

        //when
        var actual = repository.findAll().get();

        //then
        assertThat(actual).extracting(CedulaOutDto::getUuidCedula)
                .contains(fromString("0d28786f-8dbd-41f7-8a77-59ea8bed7d8c"),
                          fromString("147e966b-7b4a-4702-b322-ba0d5b707d18"));
    }

}
