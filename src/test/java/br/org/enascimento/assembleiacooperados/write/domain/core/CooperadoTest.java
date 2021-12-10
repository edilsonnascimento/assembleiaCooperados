package br.org.enascimento.assembleiacooperados.write.domain.core;

import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
public class CooperadoTest extends TestHelper {

    @Test
    void GIVEN_ValidDataCooperado_MUST_RestrieveSameData(){
        //given
        var id = 1;
        var uuid = randomUUID();
        var nome = faker.name().fullName();
        var cpf = faker.number().digits(11);

        //when
        var cooperado = new Cooperado()
                .setId(id)
                .setUuid(uuid)
                .setNome(nome)
                .setCpf(cpf);

        //then
        assertThat(cooperado.getId()).isEqualTo(id);
        assertThat(cooperado.getUuid()).isEqualTo(uuid);
        assertThat(cooperado.getNome()).isEqualTo(nome);
        assertThat(cooperado.getCpf()).isEqualTo(cpf);
    }
}
