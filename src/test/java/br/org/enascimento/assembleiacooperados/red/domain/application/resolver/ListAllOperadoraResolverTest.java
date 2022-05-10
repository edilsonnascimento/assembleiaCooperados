package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllOperadoraQuery;
import br.org.enascimento.assembleiacooperados.write.domain.core.Operadora;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Tag("unit")
public class ListAllOperadoraResolverTest extends TestHelper {

    @InjectMocks
    private ListAllOperadoraResolver resolver;

    @Test
    void DEVE_Retornar_Lista_de_Contatos_QUANDO_Recebido_ListAllContatoQuery() {
        //given
        var query = new ListAllOperadoraQuery();
        var actual = List.of(Operadora.values());

        //when
        resolver.resolve(query);
        var expected = query.getResult();

        //then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}