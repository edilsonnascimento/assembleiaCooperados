package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllStatusQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadStatusRepository;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.StatusDto;
import br.org.enascimento.assembleiacooperados.write.domain.core.Status;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Tag("unit")
public class ListAllStatusResolverTest extends TestHelper {

    private final ReadStatusRepository repository;
    private final ListAllStatusResolver resolver;

    public ListAllStatusResolverTest() {
        this.repository = mock(ReadStatusRepository.class);
        this.resolver = new ListAllStatusResolver(repository);
    }

    @Test
    void WHEN_QueryAllStatus_MUST_RetriveSuccessfull(){

        //given
        var query = new ListAllStatusQuery();
        var dataAtual = LocalDateTime.now();
        var aberta = new Status()
                                   .setId(1l)
                                   .setDescricao("ABERTA")
                                   .setCreatedAt(dataAtual)
                                   .setUpdatedAt(dataAtual);
        var encerrada = new Status()
                                   .setId(2l)
                                   .setDescricao("ENCERRADA")
                                   .setCreatedAt(dataAtual.plusSeconds(1l))
                                   .setUpdatedAt(dataAtual.plusSeconds(1l));
        var listStatus = List.of(aberta, encerrada);
        var expected = List.of(new StatusDto("ABERTA"), new StatusDto("ENCERRADA"));
        when(repository.findAll()).thenReturn(Optional.of(listStatus));

        //when
        resolver.resolve(query);
        var actual = query.getResult();

        //then
        verify(repository, times(1)).findAll();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}