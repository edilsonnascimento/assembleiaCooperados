package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dtos.PautaInDto;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllPautasQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadPautaRepository;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Tag("unit")
public class ListAllPautasResolverTest extends TestHelper {

    @Mock
    private ReadPautaRepository repository;
    @InjectMocks
    private ListAllPautasResolver resolver;

    @Test
    void DADO_Query_Valida_DEVE_RetornarListaPautas() {
        //given
        var query = new ListAllPautasQuery();
        var dto = new PautaInDto(UUID.randomUUID(), faker.name().name(), faker.name().fullName());
        var expected = Arrays.asList(dto);
        when(repository.findAll()).thenReturn(expected);

        //when
        resolver.resolve(query);
        var actual = query.getResult();

        //then
        verify(repository, timeout(1)).findAll();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}