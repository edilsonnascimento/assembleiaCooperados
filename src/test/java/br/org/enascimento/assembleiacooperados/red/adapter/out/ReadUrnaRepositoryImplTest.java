package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadUrnaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Urna;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.org.enascimento.assembleiacooperados.write.domain.core.Voto.FAVORAVEL;
import static org.assertj.core.api.Assertions.assertThat;

public class ReadUrnaRepositoryImplTest extends DataSourceHelper {

    private ReadUrnaRepository repository;

    @BeforeEach
    void setup() {
        repository = new ReadUrnaRepositoryImpl(dataSource);
    }


    @Test
    void WHEN_QueryFindUrnaUiid_MUST_RetrieveSuccessful(){

        //given
        var data = LocalDateTime.of(2021,12,8, 5,55, 0);
        var actual = new Urna();
        actual.setId(1l);
        actual.setUuid(UUID.fromString("0d28786f-8dbd-41f7-8a77-59ea8bed7d8c"));
        actual.setIdSessao(1l);
        actual.setIdCoopereado(1l);
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
    void WHEN_QueryFindUrnaUiid_MUST_RetrieveSuccessfulUrnaDto(){

        //given
        var data = LocalDateTime.of(2021,12,8, 5,55, 0);
        var actual = new Urna();
        actual.setId(1l);
        actual.setUuid(UUID.fromString("0d28786f-8dbd-41f7-8a77-59ea8bed7d8c"));
        actual.setIdSessao(1l);
        actual.setIdCoopereado(1l);
        actual.setVoto(FAVORAVEL);
        actual.setCreatedAt(data);
        actual.setUpdatedAt(data);

        //then
        var expected = repository.findByUuidDto(actual.getUuid());

        //when

    }
}
