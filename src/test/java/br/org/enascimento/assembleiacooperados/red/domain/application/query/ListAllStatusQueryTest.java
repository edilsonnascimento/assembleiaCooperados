package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.StatusDto;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class ListAllStatusQueryTest extends TestHelper {

    @Test
    void GIVEN_QueryAllStatus_MUST_RetrieveListDto(){

        //given
        var query = new ListAllStatusQuery();
        var actual = List.of(new StatusDto("ABERTA"), new StatusDto("ENCERRADA"));
        query.setResult(actual);

        //when
        var expected = query.getResult();

        //then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
