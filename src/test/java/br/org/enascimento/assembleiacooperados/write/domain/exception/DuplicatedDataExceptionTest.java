package br.org.enascimento.assembleiacooperados.write.domain.exception;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.INVALID_DUPLICATE_DATA;

class DuplicatedDataExceptionTest {

    @Test
    void DuplicateDateException_MUST_Extends_DomainException(){
        //given
        var exception = new DuplicatedDataException(INVALID_DUPLICATE_DATA, new RuntimeException());

        //then
        Assertions.assertThat(exception).isInstanceOf(DomainException.class);
    }

}