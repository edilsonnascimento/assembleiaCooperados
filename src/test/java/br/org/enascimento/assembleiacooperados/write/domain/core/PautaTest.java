package br.org.enascimento.assembleiacooperados.write.domain.core;

import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class PautaTest extends TestHelper {

    @Test
    void GIVEN_ValidData_MUST_RestrieveSameData(){
        //given
        var id = 1l;
        var uuid = UUID.randomUUID();
        var titulo = faker.lorem().fixedString(60);
        var descricao = faker.lorem().characters();;

        //when
        var pauta = new Pauta();
        pauta.setId(id);
        pauta.setUuid(uuid);
        pauta.setTitulo(titulo);
        pauta.setDescricao(descricao);

        //then
        assertThat(pauta.getId()).isEqualTo(id);
        assertThat(pauta.getUuid()).isEqualTo(uuid);
        assertThat(pauta.getTitulo()).isEqualTo(titulo);
        assertThat(pauta.getDescricao()).isEqualTo(descricao);
    }
}
