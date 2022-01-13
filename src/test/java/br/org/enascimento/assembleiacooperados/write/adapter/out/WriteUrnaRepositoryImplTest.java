package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.UrnaInDto;
import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteUrnaRepository;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class WriteUrnaRepositoryImplTest extends DataSourceHelper {

    private WriteUrnaRepository repository;

    @BeforeEach
    void setup() {
        repository = new WriteUrnaRepositoryImpl(dataSource);
    }

    @Test
    void WHEN_CreatingUrna_GIVEN_ValidData_MUST_PersistOnDatabase() {
        //given
        var uuidUrna = UUID.fromString("3b54e51d-6340-47b3-a15d-0d28e4272551");
        var dto = new UrnaInDto(
                uuidUrna,
                1l,
                1l,
                Voto.FAVORAVEL);

        //when
        boolean retorno = repository.create(dto);

        //then
        assertThat(retorno).isTrue();
    }
}
