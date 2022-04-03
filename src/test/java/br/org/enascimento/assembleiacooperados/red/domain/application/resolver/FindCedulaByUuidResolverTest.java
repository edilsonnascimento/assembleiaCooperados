package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.CedulaOutDto;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindCedulaByUuidQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCedulaRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.CedulaNotExistentException;
import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("unit")
class FindCedulaByUuidResolverTest extends TestHelper {

    @Mock
    private ReadCedulaRepository repository;
    @InjectMocks
    private FindCedulaByUuidResolver resolver;

    @Test
    void GIVEN_UuidValidFindbyUrnaUuid_ReturnDto() {
        //given
        var query = new FindCedulaByUuidQuery();
        query.setUuid(UUID.randomUUID());
        var actual = new CedulaOutDto().
                setUuidCedula(UUID.randomUUID()).
                setUuidCooperado(UUID.randomUUID()).
                setUuidSessao(UUID.randomUUID()).
                setDataVoto(LocalDateTime.now())
                .setVoto(Voto.FAVORAVEL);
        when(repository.findByUuidDto(any())).thenReturn(Optional.of(actual));

        //when
        resolver.resolve(query);
        var expected = query.getResult();

        //then
        verify(repository, timeout(1)).findByUuidDto(any());
        assertThat(actual.getUuidCedula()).isEqualTo(expected.getUuidCedula());
        assertThat(actual.getUuidSessao()).isEqualTo(expected.getUuidSessao());
        assertThat(actual.getUuidCooperado()).isEqualTo(expected.getUuidCooperado());
        assertThat(actual.getDataVoto()).isEqualTo(expected.getDataVoto());
        assertThat(actual.getVoto()).isEqualTo(expected.getVoto());
    }

    @Test
    void GIVEN_InvalidFindbyCedulaUuid_ReturnException() {
        //given
        var query = new FindCedulaByUuidQuery();
        var uuid = UUID.randomUUID();
        query.setUuid(uuid);
        when(repository.findByUuidDto(any())).thenReturn(Optional.empty());

        //when
        var exception = assertThrows(CedulaNotExistentException.class,
                () -> resolver.resolve(query));

        //then
        assertThat(exception.getMessage()).isEqualTo("Cedula not exist");
        verify(repository, times(1)).findByUuidDto(uuid);
    }
}