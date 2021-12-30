package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadSessaoRepository;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadSessaoRepositoyImplTest extends DataSourceHelper {

    private ReadSessaoRepository repository;

    @BeforeEach
    void setup() {
        repository = new ReadSessaoRepositoryImpl(dataSource);
    }

    @Test
    void MUST_ImplementInterface() {
        assertThat(repository).isInstanceOf(ReadSessaoRepository.class);
    }

    @Test
    void WHEN_QueryFindSessaoUiid_MUST_RetrieveSuccessful() {
        //given
        var uuid = UUID.fromString("91459bb4-07e9-47ab-85c5-4af513db36a3");

        //when
        var actual = repository.findByUuid(uuid).get();

        //then
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getIdPauta()).isEqualTo(1);
        assertThat(actual.getIdUrna()).isNull();
        assertThat(actual.getInicioSessao().withNano(0)).isEqualTo(LocalDateTime.of(2021,12,21, 8,54, 6));
        assertThat(actual.getFimSessao().withNano(0)).isEqualTo(LocalDateTime.of(2021,12,21, 8,59, 6));
        assertThat(actual.getTotalVotosFavor()).isEqualTo(BigDecimal.ZERO);
        assertThat(actual.getTotalVotosContra()).isEqualTo(BigDecimal.ZERO);
        assertThat(actual.getCreatedAt().withNano(0)).isEqualTo(LocalDateTime.of(2021,12,21, 8,54, 6));
        assertThat(actual.getUpdatedAt().withNano(0)).isEqualTo(LocalDateTime.of(2021,12,21, 8,54, 6));
        assertThat(actual.getIdStatus()).isEqualTo(1);
    }

}
