package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.SessaoOutDto;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
public class FindSessaoUuidDtoOutQueryTest extends TestHelper {

    @Test
    void GIVEN_QueryUuidSessao_MUST_RetrieveListDto() {
        //given
        var query = new FindSessaoByUuidDtoOutQuery();
        var uuid = UUID.fromString("91459bb4-07e9-47ab-85c5-4af513db36a3");
        var quantidadeParticipantes = BigDecimal.TEN;
        var tituloPauta = faker.lorem().characters(10);
        var descricaoPauta = faker.lorem().characters(60);
        var dataInicio = LocalDateTime.now();
        var dataFim = dataInicio.plusSeconds(5l);
        var quantiadeVotosFavoraveis = BigDecimal.ZERO;
        var quantiadeVotosContra = BigDecimal.TEN;
        var estadoSessao = "ABERTA";
        query.setUuid(UUID.fromString("91459bb4-07e9-47ab-85c5-4af513db36a3"));
        var actual = new SessaoOutDto(
                uuid,
                quantidadeParticipantes,
                tituloPauta,
                descricaoPauta,
                dataInicio,
                dataFim,
                quantiadeVotosFavoraveis,
                quantiadeVotosContra,
                estadoSessao);

        //when
        query.setResult(actual);
        var expected = query.getResult();

        //then
        assertThat(expected.uuid()).isEqualTo(actual.uuid());
        assertThat(expected.tituloPauta()).isEqualTo(actual.tituloPauta());
        assertThat(expected.descricaoPauta()).isEqualTo(actual.descricaoPauta());
        assertThat(expected.quantidadeParticipantes()).isEqualTo(actual.quantidadeParticipantes());
        assertThat(expected.dataInicioSessao()).isEqualTo(actual.dataInicioSessao());
        assertThat(expected.dataFimSessao()).isEqualTo(actual.dataFimSessao());
        assertThat(expected.quantiadeVotosFavoraveis()).isEqualTo(actual.quantiadeVotosFavoraveis());
        assertThat(expected.quantiadeVotosContra()).isEqualTo(actual.quantiadeVotosContra());
        assertThat(expected.estadoSessao()).isEqualTo(actual.estadoSessao());
    }
}
