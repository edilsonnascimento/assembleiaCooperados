package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindStatusByIdQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadStatusRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.StatusNotExistedExcepetion;
import br.org.enascimento.assembleiacooperados.write.domain.core.Status;
import helper.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Tag("unit")
public class FindStatusByIdResolverTest extends TestHelper {

    private final FindStatusByIdResolver resolver;
    private final ReadStatusRepository repository;

    public FindStatusByIdResolverTest() {
        this.repository = mock(ReadStatusRepository.class);
        this.resolver = new FindStatusByIdResolver(repository);
    }

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
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        var exeption = Assertions.assertThrows(StatusNotExistedExcepetion.class, () ->
                resolver.resolve(query));

        //then
        assertThat(exeption.getMessage()).isEqualTo("Status not exist");
        assertThat(exeption.getCode()).isEqualTo(1005);
    }

}
