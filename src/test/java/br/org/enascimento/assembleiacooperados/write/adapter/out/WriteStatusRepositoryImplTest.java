package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.domain.core.Status;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteStatusRepositoy;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WriteStatusRepositoryImplTest extends DataSourceHelper {

    private WriteStatusRepositoy repositoy;

    @BeforeEach
    void setup(){
        repositoy = new WriteStatusRepositoryImpl(dataSource);
    }

    @Test
    void WHEN_CreatingStatus_GIVEN_ValidData_MUST_PersistOnDatabase(){
        //given
        var status = new Status().setId(1l).setDescricao("Ativo");

        //when
        boolean expected = repositoy.create(status);

        //then
        assertThat(expected).isTrue();
    }
}
