package br.org.enascimento.assembleiacooperados.write.domain.core;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PautaTest {

    @Test
    void GIVEN_ValidData_MUST_RestrieveSameData(){
        //given
        var id = 1;
        var uuid = UUID.randomUUID();
        var titulo = "Aumento da segurança";
        var descricao = "Contratar 5 seguranças fixos.";

        //when
        var pauta = new Pauta()
                .setId(id)
                .setUuid(uuid)
                .setTitulo(titulo)
                .setDescricao(descricao);

        //then
        assertThat(pauta.getId()).isEqualTo(id);
        assertThat(pauta.getUuid()).isEqualTo(uuid);
        assertThat(pauta.getTitulo()).isEqualTo(titulo);
        assertThat(pauta.getDescricao()).isEqualTo(descricao);
    }
}
