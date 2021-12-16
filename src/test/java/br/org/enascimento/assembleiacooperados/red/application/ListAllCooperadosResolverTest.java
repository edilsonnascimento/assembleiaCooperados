package br.org.enascimento.assembleiacooperados.red.application;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.CooperadoInDto;
import br.org.enascimento.assembleiacooperados.red.application.query.ListAllCooperadosQuery;
import br.org.enascimento.assembleiacooperados.red.application.resolver.ListAllCooperadosResolver;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCooperadoRepository;
import helper.TestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@Tag("unit")
public class ListAllCooperadosResolverTest extends TestHelper {

    private final ReadCooperadoRepository repository;
    private final ListAllCooperadosResolver resolver;

    public ListAllCooperadosResolverTest() {
        this.repository = mock(ReadCooperadoRepository.class);
        this.resolver = new ListAllCooperadosResolver(repository);
    }


    @Test
    void WHEN_QueryAllCooperados_MUST_RetriveSuccessful(){
        //given
        var query = new ListAllCooperadosQuery();
        var actual = List.of(
                new CooperadoInDto(UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20"), "NOME-EXISTENTE-1", "74656849359"),
                new CooperadoInDto(UUID.fromString("3731c747-ea27-42e5-a52b-1dfbfa9617db"), "NOME-EXISTENTE-2", "38176004707"));
        when(repository.findAll()).thenReturn(actual);

        //when
        resolver.resolve(query);
        var resultExpected = query.getResult();

        //then
        Assertions.assertThat(actual).containsExactlyInAnyOrderElementsOf(resultExpected);
        verify(repository, times(1)).findAll();
    }
}
