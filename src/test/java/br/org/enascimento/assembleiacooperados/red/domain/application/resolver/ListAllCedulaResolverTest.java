package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.CedulaOutDto;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllCedulaQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCedulaRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.CedulaNotExistentException;
import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Tag("unit")
class ListAllCedulaResolverTest extends TestHelper {

    @Mock
    private ReadCedulaRepository repository;
    @InjectMocks
    private ListAllCedulaResolver resolver;

    @Test
    void WHEN_QueryAllCedula_MUST_RetriveSuccessfull(){
        //given
        var query = new ListAllCedulaQuery();
        var expected = List.of( new CedulaOutDto()
                .setUuidCedula(UUID.randomUUID())
                .setUuidSessao(UUID.randomUUID())
                .setUuidCooperado(UUID.randomUUID())
                .setDataVoto(LocalDateTime.now())
                .setVoto(Voto.FAVORAVEL));
        when(repository.findAll()).thenReturn(Optional.of(expected));

        //when
        resolver.resolve(query);
        var actual = query.getResult();

        //then
        verify(repository, times(1)).findAll();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void DADO_QueryAllCedula_Invalido_DEVE_LancarException() {
        //given
        var query = new ListAllCedulaQuery();
        when(repository.findAll()).thenReturn(Optional.empty());

        //when
        var exception = assertThrows(CedulaNotExistentException.class, () ->
                resolver.resolve(query));
        //then
        assertThat(exception.getMessage()).isEqualTo("Cedula not exist");
    }
}