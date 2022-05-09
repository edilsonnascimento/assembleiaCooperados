package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.ContatoOutDTO;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllContatoQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadContatoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Operadora;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Tag("unit")
public class ListAllContatoResolverTest extends TestHelper {

    @InjectMocks
    private ListAllContatoResolver resolver;
    @Mock
    private ReadContatoRepository repository;

    @Test
    void DEVE_Retornar_Lista_de_Contatos_QUANDO_Recebido_ListAllContatoQuery() {
        //given
         var query = new ListAllContatoQuery();
         var actual = List.of(
                 new ContatoOutDTO(faker.name().fullName(), faker.number().digits(11), Operadora.GVT, LocalDateTime.now()));
        when(repository.findAll()).thenReturn(Optional.of(actual));

         //when
        resolver.resolve(query);
        var expected = query.getResult();

        //then
        verify(repository, times(1)).findAll();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}