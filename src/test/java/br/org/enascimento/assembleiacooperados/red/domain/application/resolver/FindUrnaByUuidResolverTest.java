package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.UrnaOutDto;
import br.org.enascimento.assembleiacooperados.red.adapter.out.ReadUrnaRepositoryImpl;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindUrnaByUuidQuery;
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
public class FindUrnaByUuidResolverTest extends TestHelper {

    @Test
    void GIVEN_UuidValidFindbyUrnaUuid_ReturnDto() {

        //given
        var repositoy = mock(ReadUrnaRepositoryImpl.class);
        var resolver = new FindUrnaByUuidResolver(repositoy);
        var query = new FindUrnaByUuidQuery();
        query.setUuid(UUID.randomUUID());
        var actual = new UrnaOutDto().
                setUuidUrna(UUID.randomUUID()).
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
        assertThat(actual.getUuidUrna()).isEqualTo(expected.getUuidUrna());
        assertThat(actual.getUuidSessao()).isEqualTo(expected.getUuidSessao());
        assertThat(actual.getUuidCooperado()).isEqualTo(expected.getUuidCooperado());
        assertThat(actual.getDataVoto()).isEqualTo(expected.getDataVoto());
        assertThat(actual.getVoto()).isEqualTo(expected.getVoto());
    }
}
