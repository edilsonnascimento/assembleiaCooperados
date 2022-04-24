package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.SessaoOutDto;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllSessaoQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadSessaoRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.SessaoNotExistedException;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Tag("unit")
class ListAllSessaoResolverTest extends TestHelper {

    @Mock
    private ReadSessaoRepository repository;
    @InjectMocks
    private ListAllSessaoResolver resolver;

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

    @Test
    void DADO_QueryAllSessao_Invalida_DEVE_LancarExeception() {
        //given
        var query = new ListAllSessaoQuery();
        when(repository.findAll()).thenReturn(Optional.empty());

        //when
        var exception = assertThrows(SessaoNotExistedException.class, () ->
                resolver.resolve(query));
        //then
        verify(repository, times(1)).findAll();
        assertThat(exception.getMessage()).isEqualTo("Sessao not exist");
    }
}