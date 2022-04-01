package br.org.enascimento.assembleiacooperados.write.adapter.in;

import br.org.enascimento.assembleiacooperados.write.adapter.in.consummers.validacpf.ValidaCPFConsumer;
import br.org.enascimento.assembleiacooperados.write.domain.exception.ValidaCPFException;
import helper.GeradorCPF;
import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ValidaCPFConsumerIT extends IntegrationHelper {

    @Autowired
    private ValidaCPFConsumer validaCPF;

    @Test
    void GIVEN_ValidDataCpf_MUST_ReturnSucess(){
        //given
        var cpf = new GeradorCPF().gerar();

        //when
        var actual = validaCPF.isAbleToVote(cpf);

        //then
        assertThat(actual).isNotNull();

    }

    @Test
    void GIVEN_InValidDataCpf_MUST_ReturnRuntimeException(){
        //given
        var cpf = "999999999";

        //when
        var expection = assertThrows(ValidaCPFException.class, () ->
                validaCPF.isAbleToVote(cpf));
        //then
        assertThat(expection.getMessage()).isEqualTo("invalid cpf for voting");
    }
}
