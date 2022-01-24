package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.CedulaOutDto;
import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
public class ListAllCedulaQueryTest extends TestHelper {

    @Test
    void GIVEN_QueryAllCedula_MUST_RetrieveListDto(){

        //given
        var query = new ListAllCedulaQuery();
        var actual = List.of( new CedulaOutDto()
                .setUuidCedula(UUID.randomUUID())
                .setUuidSessao(UUID.randomUUID())
                .setUuidCooperado(UUID.randomUUID())
                .setDataVoto(LocalDateTime.now())
                .setVoto(Voto.FAVORAVEL));
        query.setResult(actual);

        //when
        var expected = query.getResult();

        //then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
