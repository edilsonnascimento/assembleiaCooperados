package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.CedulaOutDto;
import br.org.enascimento.assembleiacooperados.red.adapter.out.ReadCedulaRepositoryImpl;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindCedulaByUuidQuery;
import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("unit")
public class FindCedulaByUuidResolverTest extends TestHelper {

    @Test
    void GIVEN_UuidValidFindbyUrnaUuid_ReturnDto() {

        //given
        var repositoy = mock(ReadCedulaRepositoryImpl.class);
        var resolver = new FindCedulaByUuidResolver(repositoy);
        var query = new FindCedulaByUuidQuery();
        query.setUuid(UUID.randomUUID());
        var actual = new CedulaOutDto().
                setUuidCedula(UUID.randomUUID()).
                setUuidCooperado(UUID.randomUUID()).
                setUuidSessao(UUID.randomUUID()).
                setDataVoto(LocalDateTime.now())
                .setVoto(Voto.FAVORAVEL);
        when(repositoy.findByUuidDto(any())).thenReturn(Optional.of(actual));

        //when
        resolver.resolve(query);
        var expected = query.getResult();

        //then
        verify(repositoy, timeout(1)).findByUuidDto(any());
        assertThat(actual.getUuidCedula()).isEqualTo(expected.getUuidCedula());
        assertThat(actual.getUuidSessao()).isEqualTo(expected.getUuidSessao());
        assertThat(actual.getUuidCooperado()).isEqualTo(expected.getUuidCooperado());
        assertThat(actual.getDataVoto()).isEqualTo(expected.getDataVoto());
        assertThat(actual.getVoto()).isEqualTo(expected.getVoto());
    }
}
