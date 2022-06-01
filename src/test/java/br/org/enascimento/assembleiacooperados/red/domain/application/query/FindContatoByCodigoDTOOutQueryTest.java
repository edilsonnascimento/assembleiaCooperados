package br.org.enascimento.assembleiacooperados.red.domain.application.query;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.ContatoOutDTO;
import br.org.enascimento.assembleiacooperados.write.domain.core.Operadora;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
public class FindContatoByCodigoDTOOutQueryTest extends TestHelper {

    @Test
    public void DEVE_Receber_QueryValida_retornar_ContatoDTO(){
        //given
        var query = new FindContatoByCodigoDTOOutQuery();
        var codigo = faker.code().ean8();
        var expected = new ContatoOutDTO(faker.name().fullName(), faker.number().digits(11), Operadora.GVT, LocalDateTime.now(), codigo);
        query.setCodigo(codigo);
        query.setResult(expected);

        //when
        var actual = query.getResult();

        //then
        assertThat(actual.codigo()).isEqualTo(expected.codigo());
        assertThat(actual.nomeContato()).isEqualTo(expected.nomeContato());
        assertThat(actual.telefone()).isEqualTo(expected.telefone());
        assertThat(actual.dataCadastro()).isEqualToIgnoringNanos(expected.dataCadastro());
        assertThat(actual.operadora()).isEqualTo(expected.operadora());
    }
}
