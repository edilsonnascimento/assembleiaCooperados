package br.org.enascimento.assembleiacooperados.write.domain.exception;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DuplicatedDataExceptionTest {

    @Test
    void DuplicateDateException_MUST_Extends_DomainException(){
        //given
        var exception = new DuplicatedDataException("Teste");

        //then
        Assertions.assertThat(exception).isInstanceOf(DomainException.class);
    }

}