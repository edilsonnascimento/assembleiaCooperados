package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.SessaoOutDto;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
public class ListAllSessaoQueryTest extends TestHelper {

    @Test
    void GIVEN_QueryAllSessoes_MUST_RetrieveListDto(){
        //given
        var query = new ListAllSessaoQuery();
        var expected = List.of(new SessaoOutDto(
                UUID.randomUUID(),
                BigDecimal.ZERO,
                faker.lorem().characters(10),
                faker.lorem().characters(30),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30L),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                faker.name().firstName())
        );

        //when
        query.setResult(expected);
        var actual = query.getResult();

        //then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);

    }
}
