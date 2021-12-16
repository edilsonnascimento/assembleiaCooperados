package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.PautaInDto;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindPautaByUuidQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadPautaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Tag("unit")
public class FindPautaByUuidResolverTest extends TestHelper {

    private final ReadPautaRepository repository;
    private final FindPautaByUuidResolver resolver;

    public FindPautaByUuidResolverTest() {
        this.repository = mock(ReadPautaRepository.class);
        this.resolver = new FindPautaByUuidResolver(repository);
    }

    @Test
    void GIVEN_UuidValidFindbyCooperadoUuid_ReturnDto() {
        //given
        var uuid = UUID.randomUUID();
        var query = new FindPautaByUuidQuery();
        query.setUuid(uuid);
        var data = LocalDateTime.now();
        var pauta = new Pauta()
                .setId(1l)
                .setUuid(uuid)
                .setTitulo(faker.team().name())
                .setDescricao(faker.lorem().characters(5, 100))
                .setCreatedAt(data)
                .setUpdatedAt(data);
        when(repository.findByUuid(uuid)).thenReturn(Optional.of(pauta));
        //when
        resolver.resolve(query);
        var expected = query.getResult();
        //then
        verify(repository, timeout(1)).findByUuid(uuid);
        assertThat(expected).isInstanceOf(PautaInDto.class);
        assertThat(expected.uuid()).isEqualTo(pauta.getUuid());
        assertThat(expected.titulo()).isEqualTo(pauta.getTitulo());
        assertThat(expected.descricao()).isEqualTo(pauta.getDescricao());
    }
}
