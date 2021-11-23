package br.org.enascimento.assembleiacooperados.write.domain.core;

import br.org.enascimento.assembleiacooperados.write.adapter.out.PautaRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class PautaTest {

    @Test
    void GIVEN_ValidData_MUST_RestrieveSameData(){
        //given
        var id = 1;
        var uuid = UUID.randomUUID();
        var titulo = "Aumento da segurança";
        var descricao = "Contratar 5 seguranças fixos.";
        var creatAt = LocalDateTime.now();
        var updateAt = LocalDateTime.now();

        //when
        var pauta = new Pauta()
                .setId(id)
                .setUuid(uuid)
                .setTitulo(titulo)
                .setDescricao(descricao)
                .setCreatAt(creatAt)
                .setUpdateAt(updateAt);

        //then
        assertThat(pauta.getId()).isEqualTo(id);
        assertThat(pauta.getUuid()).isEqualTo(uuid);
        assertThat(pauta.getTitulo()).isEqualTo(titulo);
        assertThat(pauta.getDescricao()).isEqualTo(descricao);
        assertThat(pauta.getCreatAt()).isEqualTo(creatAt);
        assertThat(pauta.getUpdateAt()).isEqualTo(updateAt);
    }
}
