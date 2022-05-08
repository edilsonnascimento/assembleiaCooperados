package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.ContatoDTO;
import br.org.enascimento.assembleiacooperados.write.domain.core.Operadora;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteContatoRepository;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class WriteContatoRepositoryImplTest extends DataSourceHelper {

    private WriteContatoRepository repository;

    @BeforeEach
    void setup() {
        repository = new WriteContatoRepositoryImpl(dataSource);
    }

    @Test
    void DADO_DTO_VALIDO_PERSISTIR_CONTATO_BASE(){
        //given
        var dto = new ContatoDTO(new BigDecimal("4130784510"), Operadora.GVT, "Contato");

        //when
        var resultado = repository.create(dto);

        //then
        assertThat(resultado).isTrue();
    }

}
