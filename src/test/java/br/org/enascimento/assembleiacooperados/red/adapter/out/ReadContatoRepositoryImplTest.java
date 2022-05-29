package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.ContatoOutDTO;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadContatoRepository;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadContatoRepositoryImplTest extends DataSourceHelper {

    private ReadContatoRepository repository;

    @BeforeEach
    void setup() {
        repository = new ReadContatoRepositoryImpl(dataSource);
    }

    @Test
    void DEVE_Retornar_Uma_Lista_com_Todos_Contatos() {
        //when
        var actual = repository.findAll().get();

        //then
        assertThat(actual)
                .extracting(ContatoOutDTO::telefone)
                .contains("45871259476","4688825947","4199487497");
    }
}