package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.SessaoOutDto;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllSessaoQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadSessaoRepository;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@Tag("unit")
class ListAllSessaoResolverTest extends TestHelper {

    private final ReadSessaoRepository repository;
    private final ListAllSessaoResolver resolver;

    public ListAllSessaoResolverTest() {
        this.repository = mock(ReadSessaoRepository.class);
        this.resolver = new ListAllSessaoResolver(repository);
    }

    @Test
    void WHEN_QueryAllSessao_MUST_RetriveSuccessfull() {

        //given
        var query = new ListAllSessaoQuery();
        var expected = List.of(new SessaoOutDto(
                UUID.randomUUID(),
                BigDecimal.ZERO,
                faker.lorem().characters(10),
                faker.lorem().characters(30),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30L),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                faker.name().firstName()));
        when(repository.findAll()).thenReturn(Optional.of(expected));

        //when
        resolver.resolve(query);
        var actual = query.getResult();

        //then
        verify(repository, times(1)).findAll();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void WHEN_QueryAllSessao_MUST_RetriveListEmpty() {

        //given
        var query = new ListAllSessaoQuery();
        var expected = new ArrayList<SessaoOutDto>();
        when(repository.findAll()).thenReturn(Optional.of(expected));

        //when
        resolver.resolve(query);
        var actual = query.getResult();

        //then
        verify(repository, times(1)).findAll();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}