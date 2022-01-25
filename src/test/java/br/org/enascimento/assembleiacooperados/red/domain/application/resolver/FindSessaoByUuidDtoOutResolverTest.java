package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.SessaoOutDto;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindSessaoByUuidDtoOutQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadSessaoRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.SessaoNotExistedException;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Tag("unit")
public class FindSessaoByUuidDtoOutResolverTest extends TestHelper {

    private ReadSessaoRepository repository;
    private FindSessaoByUuidDtoOutResolver resolver;

    public FindSessaoByUuidDtoOutResolverTest() {
        this.repository = mock(ReadSessaoRepository.class);
        this.resolver = new FindSessaoByUuidDtoOutResolver(repository);
    }

    @Test
    void GIVEN_UuidValidFindbySessaoUuid_ReturnDto(){

        //given
        var uuid = UUID.randomUUID();
        var query = new FindSessaoByUuidDtoOutQuery();
        query.setUuid(uuid);
        var actual = new SessaoOutDto(
                uuid,
                BigDecimal.ZERO,
                faker.lorem().characters(),
                faker.lorem().characters(),
                LocalDateTime.now(),
                LocalDateTime.now().plusSeconds(5l),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                faker.lorem().characters());
        when(repository.findByUuidReturnDto(query.getUuid())).thenReturn(Optional.of(actual));

        //when
        resolver.resolve(query);
        var expected = query.getResult();

        //then
        verify(repository, timeout(1)).findByUuidReturnDto(any());
        assertThat(actual.uuid()).isEqualTo(expected.uuid());
        assertThat(actual.tituloPauta()).isEqualTo(expected.tituloPauta());
        assertThat(actual.descricaoPauta()).isEqualTo(expected.descricaoPauta());
        assertThat(actual.quantiadeVotosContra()).isEqualTo(expected.quantiadeVotosContra());
        assertThat(actual.quantiadeVotosFavoraveis()).isEqualTo(expected.quantiadeVotosFavoraveis());
        assertThat(actual.dataInicioSessao()).isEqualTo(expected.dataInicioSessao());
        assertThat(actual.dataFimSessao()).isEqualTo(expected.dataFimSessao());
        assertThat(actual.estadoSessao()).isEqualTo(expected.estadoSessao());
    }

    @Test
    void GIVEN_UuidValidFindbySessaoUuid_ReturnException() {

        //given
        var uuid = UUID.randomUUID();
        var query = new FindSessaoByUuidDtoOutQuery();
        query.setUuid(uuid);
        when(repository.findByUuidReturnDto(query.getUuid())).thenReturn(Optional.empty());

        //when
        var exception = assertThrows(SessaoNotExistedException.class, ()->
                resolver.resolve(query));

        //then
        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("Sessao not exist");
    }

}