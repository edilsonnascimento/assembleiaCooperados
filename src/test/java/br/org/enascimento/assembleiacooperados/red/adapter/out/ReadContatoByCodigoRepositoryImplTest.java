package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadContatoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Contato;
import br.org.enascimento.assembleiacooperados.write.domain.core.Operadora;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadContatoByCodigoRepositoryImplTest extends DataSourceHelper {

    private ReadContatoRepository repository;

    @BeforeEach
    void setup() {
        repository = new ReadContatoRepositoryImpl(dataSource);
    }

    @Test
    void DEVE_Retornar_Um_Contato_Buscando_Pelo_Codigo() {
        //given
        var codigo = "53432232";
        var data = LocalDateTime.of(2022,5,8, 4,40, 53);
        var expected = new Contato();
        expected.setId(2l);
        expected.setTelefone("4688825947");
        expected.setOperadora(Operadora.OI);
        expected.setNome("Lynda Grady");
        expected.setCreatedAt(data);
        expected.setUpdatedAt(data);
        expected.setCodigo(codigo);

        //when
        var actual = repository.findByCodigo(codigo).get();

        //then
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getTelefone()).isEqualTo(expected.getTelefone());
        assertThat(actual.getOperadora()).isEqualTo(expected.getOperadora());
        assertThat(actual.getNome()).isEqualTo(expected.getNome());
        assertThat(actual.getCreatedAt()).isEqualToIgnoringNanos(expected.getCreatedAt());
        assertThat(actual.getUpdatedAt()).isEqualToIgnoringNanos(expected.getUpdatedAt());
        assertThat(actual.getCodigo()).isEqualTo(expected.getCodigo());
    }
}