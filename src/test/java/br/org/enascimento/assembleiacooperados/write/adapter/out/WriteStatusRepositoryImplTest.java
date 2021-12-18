package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.red.adapter.out.ReadStatusRepositoryImpl;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadStatusRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Status;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteStatusRepositoy;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WriteStatusRepositoryImplTest extends DataSourceHelper {

    private WriteStatusRepositoy repositoy;
    private ReadStatusRepository repositoryRead;

    @BeforeEach
    void setup() {
        repositoy = new WriteStatusRepositoryImpl(dataSource);
        repositoryRead = new ReadStatusRepositoryImpl(dataSource);
    }

    @Test
    void WHEN_CreatingStatus_GIVEN_ValidData_MUST_PersistOnDatabase() {
        //given
        var status = new Status().setId(1l).setDescricao("Ativo");

        //when
        boolean expected = repositoy.create(status);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void WHEN_UpdatingStatus_WITH_ValidData_MUST_SaveOnDatabase() {
        // given
        var status = new Status()
                .setId(1l)
                .setDescricao("NOVA DESCRICAO");
        var actual = repositoryRead.findById(status.getId()).get();

        //when
        repositoy.update(status);
        var expected = repositoryRead.findById(status.getId()).get();

        //then
        assertThat(expected.getId()).isEqualTo(actual.getId());
        assertThat(expected.getDescricao()).isEqualTo(status.getDescricao());
        assertThat(expected.getUpdatedAt()).isAfter(actual.getUpdatedAt());
    }
}
