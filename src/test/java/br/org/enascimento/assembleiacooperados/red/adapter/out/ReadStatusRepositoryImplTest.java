package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadStatusRepository;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadStatusRepositoryImplTest extends DataSourceHelper {

    private ReadStatusRepository repository;

    @BeforeEach
    void setup() {
        repository = new ReadStatusRepositoryImpl(dataSource);
    }

    @Test
    void MUST_ImplementInterface() {
        assertThat(repository).isInstanceOf(ReadStatusRepository.class);
    }

    @Test
    void WHEN_QueryFindStatusId_MUST_RetriveSuccessful() {
        //given
        var id = 1l;
        //when
        var expected = repository.findById(id).get();
        //then
        assertThat(expected.getId()).isEqualTo(id);
        assertThat(expected.getDescricao()).isEqualTo("Ativo");
    }
}
