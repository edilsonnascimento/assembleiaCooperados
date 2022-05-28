package br.org.enascimento.assembleiacooperados.write.domain.core;

import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class SessaoTest extends TestHelper {

    private Sessao sessao = new Sessao();

    @Test
    void GIVEN_ValidData_MUST_RestrieveSameData(){
        //given
        var id = faker.number().randomNumber();
        var uuid = UUID.randomUUID();
        var idPauta = faker.number().numberBetween(0l, 100l);
        var idCedula = faker.number().numberBetween(0l, 100);

        //when
        sessao.setId(id);
        sessao.setUuid(uuid);
        sessao.setIdPauta(idPauta);

        //then
        assertThat(sessao.getId()).isEqualTo(id);
        assertThat(sessao.getUuid()).isEqualTo(uuid);
        assertThat(sessao.getIdPauta()).isEqualTo(idPauta);
        assertThat(sessao.getIdStatus()).isEqualTo(1l);
        assertThat(sessao.getInicioSessao()).isNotNull();
        assertThat(sessao.getTotalVotosContra()).isEqualTo(BigDecimal.ZERO);
        assertThat(sessao.getTotalVotosFavor()).isEqualTo(BigDecimal.ZERO);
    }
}
