package br.org.enascimento.assembleiacooperados.write.adapter.in.consummers.validacpf;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.StatusCPF;
import br.org.enascimento.assembleiacooperados.write.domain.exception.ValidaCPFException;
import helper.GeradorCPF;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.CPF_INVALID;

@Tag("unit")
class ValidaCPFImplTest extends TestHelper {
    @Mock
    private ValidaCPFServiceAdapter service;
    @InjectMocks
    private ValidaCPFImpl validaCPF;

    @Test
    void DEVE_Retorar_TRUE_CPFValido(){
        //given
         var statusCPF = new StatusCPF("ABLE_TO_VOTE");
         when(service.sendWebServiceValidar(anyString())).thenReturn(statusCPF);

        //when
        var actual = validaCPF.isAbleToVote(new GeradorCPF().gerar());

        //then
        verify(service, times(1)).sendWebServiceValidar(anyString());
        assertThat(actual).isTrue();
    }

    @Test
    void DEVE_Retorar_FALSE_CPFInValido(){
        //given
        when(service.sendWebServiceValidar(anyString())).thenReturn(any());

        //when
        var actual = validaCPF.isAbleToVote(new GeradorCPF().gerar());

        //then
        verify(service, times(1)).sendWebServiceValidar(anyString());
        assertThat(actual).isFalse();
    }

    @Test
    void GIVEN_InValidDataCpf_MUST_ReturnRuntimeException(){
        //given
        var cpf = "999999999";
        doThrow(new ValidaCPFException(CPF_INVALID))
                .when(service)
                .sendWebServiceValidar(anyString());
        //when
        var expection = assertThrows(ValidaCPFException.class, () ->
                validaCPF.isAbleToVote(cpf));
        //then
        verify(service, times(1)).sendWebServiceValidar(anyString());
        assertThat(expection.getMessage()).isEqualTo("invalid cpf for voting");
    }
}