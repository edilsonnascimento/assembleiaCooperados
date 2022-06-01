package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.ContatoOutDTO;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindContatoByCodigoDTOOutQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadContatoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Contato;
import br.org.enascimento.assembleiacooperados.write.domain.core.Operadora;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Tag("unit")
public class FindContatoByCodigoDTOOutResolverTest extends TestHelper {

    @Mock
    private ReadContatoRepository repository;
    @InjectMocks
    private FindContatoByCodigoDTOOutResolver resolver;

    @Test
    void DEVE_Buscar_Repository_Contato_pelo_Codigo_setar_no_Result(){
        //given
        var codigo = faker.code().ean8();
        var nome = faker.name().fullName();
        var telefone = faker.number().digits(11);
        var operadora = Operadora.GVT;
        var data = LocalDateTime.now();
        var expected = new ContatoOutDTO(nome, telefone, operadora, data, codigo);
        var query = new FindContatoByCodigoDTOOutQuery();
        query.setCodigo(codigo);
        var contato = new Contato();
        contato.setId(faker.number().randomNumber());
        contato.setTelefone(telefone);
        contato.setOperadora(operadora);
        contato.setNome(nome);
        contato.setCreatedAt(data);
        contato.setUpdatedAt(data);
        contato.setCodigo(codigo);
        when(repository.findByCodigo(anyString())).thenReturn(Optional.of(contato));

        //when
        resolver.resolve(query);
        var actual = query.getResult();

        //then
        verify(repository, times(1)).findByCodigo(anyString());
        assertThat(actual.codigo()).isEqualTo(expected.codigo());
        assertThat(actual.telefone()).isEqualTo(expected.telefone());
        assertThat(actual.nomeContato()).isEqualTo(expected.nomeContato());
        assertThat(actual.operadora()).isEqualTo(expected.operadora());
        assertThat(actual.dataCadastro()).isEqualToIgnoringNanos(expected.dataCadastro());
    }

}
