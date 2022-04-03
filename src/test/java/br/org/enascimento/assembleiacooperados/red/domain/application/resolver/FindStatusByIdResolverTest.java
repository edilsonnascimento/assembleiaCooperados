package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindStatusByIdQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadStatusRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.StatusNotExistedException;
import br.org.enascimento.assembleiacooperados.write.domain.core.Status;
import helper.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Tag("unit")
class FindStatusByIdResolverTest extends TestHelper {

    @Mock
    private ReadStatusRepository repository;
    @InjectMocks
    private FindStatusByIdResolver resolver;

    @Test
    void GIVEN_DataValidFindSatusById_ReturnDto() {
        //given
        var query = new FindStatusByIdQuery();
        query.setId(1l);
        var status = new Status().setId(1l).setDescricao(faker.lorem().characters(50));
        when(repository.findById(anyLong())).thenReturn(Optional.of(status));
        //when
        resolver.resolve(query);
        var expected = query.getResult();
        //then
        verify(repository, timeout(1)).findById(anyLong());
        assertThat(expected.descricao()).isEqualTo(status.getDescricao());
    }

    @Test
    void GIVEN_InvalidDateFindbyid_ReturnException() {
        //given
        var query = new FindStatusByIdQuery();
        lenient().when(repository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        var exeption = Assertions.assertThrows(StatusNotExistedException.class, () ->
                resolver.resolve(query));
        //then
        assertThat(exeption.getMessage()).isEqualTo("Status not exist");
        assertThat(exeption.getCode()).isEqualTo(1005);
    }
}