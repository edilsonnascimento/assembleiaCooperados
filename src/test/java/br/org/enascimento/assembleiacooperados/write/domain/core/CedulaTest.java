package br.org.enascimento.assembleiacooperados.write.domain.core;

import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.org.enascimento.assembleiacooperados.write.domain.core.Voto.FAVORAVEL;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
public class CedulaTest extends TestHelper {

    @Test
    void GIVEN_ValidData_MUST_RestrieveSameData(){
        //given
        var uuid = UUID.randomUUID();
        var data = LocalDateTime.now();
        var entity = new Cedula();
        entity.setId(1l);
        entity.setUuid(uuid);
        entity.setIdSessao(1l);
        entity.setIdCoopereado(1l);
        entity.setVoto(FAVORAVEL);
        entity.setCreatedAt(data);
        entity.setUpdatedAt(data);

        //then
        assertThat(entity.getId()).isEqualTo(1l);
        assertThat(entity.getIdSessao()).isEqualTo(1l);
        assertThat(entity.getUuid()).isEqualTo(uuid);
        assertThat(entity.getCreatedAt()).isEqualTo(data);
        assertThat(entity.getUpdatedAt()).isEqualTo(data);
    }
}