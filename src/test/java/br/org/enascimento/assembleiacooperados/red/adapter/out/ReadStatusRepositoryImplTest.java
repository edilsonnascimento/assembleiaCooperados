package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadStatusRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Status;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReadStatusRepositoryImplTest extends DataSourceHelper {

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
    void WHEN_QueryFindStatusId_MUST_RetrieveSuccessful() {
        //given
        var id = 1l;
        //when
        var expected = repository.findById(id).get();
        //then
        assertThat(expected.getId()).isEqualTo(id);
        assertThat(expected.getDescricao()).isEqualTo("ABERTA");
    }

    @Test
    void WHEN_QueryFindAllStatus_MUST_RetrieveSuccessful() {
        //given
        var dataAtual = LocalDateTime.now();
        var aberta = new Status();
                aberta.setId(1l);
                aberta.setDescricao("ABERTA");
                aberta.setCreatedAt(dataAtual);
                aberta.setUpdatedAt(dataAtual);
        var encerrada = new Status();
                encerrada.setId(2l);
                encerrada.setDescricao("ENCERRADA");
                encerrada.setCreatedAt(dataAtual.plusSeconds(1l));
                encerrada.setUpdatedAt(dataAtual.plusSeconds(1l));
        var actual = List.of(aberta, encerrada);

        //when
         var expected = repository.findAll().get();

        //then
        assertThat(expected)
                .extracting(Status::getDescricao)
                .contains(actual.get(0).getDescricao(), actual.get(1).getDescricao());

    }
}