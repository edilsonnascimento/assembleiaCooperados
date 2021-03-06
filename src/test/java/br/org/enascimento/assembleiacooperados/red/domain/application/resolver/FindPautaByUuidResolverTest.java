package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dtos.PautaInDto;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindPautaByUuidQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadPautaRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.PautaNotExistentException;
import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
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
import static org.mockito.Mockito.*;

@Tag("unit")
class FindPautaByUuidResolverTest extends TestHelper {

    @Mock
    private ReadPautaRepository repository;
    @InjectMocks
    private FindPautaByUuidResolver resolver;

    @Test
    void GIVEN_UuidValidFindbyCooperadoUuid_ReturnDto() {
        //given
        var uuid = UUID.randomUUID();
        var query = new FindPautaByUuidQuery();
        query.setUuid(uuid);
        var data = LocalDateTime.now();
        var pauta = new Pauta();
        pauta.setId(1l);
        pauta.setUuid(uuid);
        pauta.setTitulo(faker.team().name());
        pauta.setDescricao(faker.lorem().characters(5, 100));
        pauta.setCreatedAt(data);
        pauta.setUpdatedAt(data);
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

    @Test
    void GIVEN_InvalidFindbyPautaUuid_ReturnException(){
        //given
        var query = new FindPautaByUuidQuery();
        var uuid = UUID.randomUUID();
        query.setUuid(uuid);
        when(repository.findByUuid(query.getUuid())).thenReturn(Optional.empty());

        //when
        var exception = assertThrows(PautaNotExistentException.class, () ->
                resolver.resolve(query));

        //then
        assertThat(exception.getMessage()).isEqualTo("Pauta not exist");
        verify(repository, times(1)).findByUuid(uuid);
    }
}