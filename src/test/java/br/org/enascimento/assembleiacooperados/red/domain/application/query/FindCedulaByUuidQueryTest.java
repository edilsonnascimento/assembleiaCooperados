package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.CedulaOutDto;
import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class FindCedulaByUuidQueryTest extends TestHelper {

    @Test
    void GIVEN_QueryAll_MUST_RetrieveListDto(){

        //given
        var query = new FindCedulaByUuidQuery();
        var actual = new CedulaOutDto().
                setUuidCedula(UUID.randomUUID()).
                setUuidCooperado(UUID.randomUUID()).
                setUuidSessao(UUID.randomUUID()).
                setDataVoto(LocalDateTime.now())
                .setVoto(Voto.FAVORAVEL);
        query.setResult(actual);

        //when
        var expected = query.getResult();

        //then
        assertThat(actual.getUuidCedula()).isEqualTo(expected.getUuidCedula());
        assertThat(actual.getUuidSessao()).isEqualTo(expected.getUuidSessao());
        assertThat(actual.getUuidCooperado()).isEqualTo(expected.getUuidCooperado());
        assertThat(actual.getVoto()).isEqualTo(expected.getVoto());
        assertThat(actual.getDataVoto()).isEqualTo(expected.getDataVoto());

    }
}
