package br.org.enascimento.assembleiacooperados.write.adapter.in;

import br.org.enascimento.assembleiacooperados.write.adapter.in.consummers.validacpf.ValidaCPFConsumer;
import helper.IntegrationHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ValidationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ValidaCPFConsumerIT extends IntegrationHelper {

    @Autowired
    private ValidaCPFConsumer validaCPF;

    @Test
    void GIVEN_ValidDataCpf_MUST_ReturnSucess(){
        //given
        var cpf = "04068307970";

        //when
        var actual = validaCPF.isAbleToVote(cpf);

        //then
        Assertions.assertTrue(actual);

    }

    @Test
    void GIVEN_InValidDataCpf_MUST_ReturnMesageInable(){
        //given
        var cpf = "62729122699";

        //when
        var actual = validaCPF.isAbleToVote(cpf);

        //then
        Assertions.assertFalse(actual);
    }

    @Test
    void GIVEN_InValidDataCpf_MUST_ReturnRuntimeException(){
        //given
        var cpf = "999999999";

        //when
        var expection = assertThrows(ValidationException.class, () ->
                validaCPF.isAbleToVote(cpf));
        //then
        assertThat(expection.getMessage()).isEqualTo("API not Found");
    }
}
