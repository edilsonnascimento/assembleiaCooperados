package br.org.enascimento.assembleiacooperados.write.domain.core;

import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class ContatoTest extends TestHelper {

    @Test
    void GIVEN_ValidData_MUST_RestrieveSameData(){
        //given
        var id = faker.number().randomNumber();
        var operadora = Operadora.GVT;
        var codigo = faker.code().asin();
        var telefone = faker.number().digits(9);
        var data = LocalDateTime.now();
        var entity = new Contato();
        entity.setId(id);
        entity.setOperadora(operadora);
        entity.setCodigo(codigo);
        entity.setTelefone(telefone);
        entity.setUpdatedAt(data);
        entity.setCreatedAt(data);

        //then
        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getCodigo()).isEqualTo(codigo);
        assertThat(entity.getOperadora()).isEqualTo(operadora);
        assertThat(entity.getTelefone()).isEqualTo(telefone);
        assertThat(entity.getCreatedAt()).isEqualTo(data);
        assertThat(entity.getUpdatedAt()).isEqualTo(data);
    }
}