package br.org.enascimento.assembleiacooperados.write.domain.exception;

import br.org.enascimento.assembleiacooperados.red.domain.exception.PautaNotExistentException;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.INVALID_DUPLICATE_DATA;
import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.PAUTA_NOT_EXIST;

@Tag("unit")
class DominExceptionTest extends TestHelper {


    @Test
    void ALL_UseCaseException_MUST_ImplementsDomainException() {

        // given
        var duplicatedDataException = new DuplicatedDataException(INVALID_DUPLICATE_DATA, new RuntimeException());
        var bucketNotExistentException = new PautaNotExistentException(PAUTA_NOT_EXIST);

        // then
        assertThat(duplicatedDataException).isInstanceOf(DomainException.class);
        assertThat(bucketNotExistentException).isInstanceOf(DomainException.class);
    }

}