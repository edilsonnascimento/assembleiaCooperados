package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindCooperadoByUuidQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCooperadoRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.CooperadoNotExistentException;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
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
class FindCooperadoByUuidResolverTest extends TestHelper {

    @Mock
    private ReadCooperadoRepository repository;
    @InjectMocks
    private FindCooperadoByUuidResolver resolver;

    @Test
    void GIVEN_ValidFindbyCooperadoUuid_ReturnDto(){
        //given
        var query = new FindCooperadoByUuidQuery();
        query.setUuid(UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20"));
        var dataAtual = LocalDateTime.now();
        var cooperado = new Cooperado()
                .setId(1L)
                .setUuid(UUID.randomUUID())
                .setNome(faker.name().fullName())
                .setCpf("00000000000")
                .setCreatedAt(dataAtual)
                .setUpdatedAt(dataAtual);
        when(repository.findByUuid(query.getUuid())).thenReturn(Optional.of(cooperado));
        //when
        resolver.resolve(query);
        var resultExpected = query.getResult();
        //then
        assertThat(resultExpected.uuid()).isEqualTo(cooperado.getUuid());
        assertThat(resultExpected.nome()).isEqualTo(cooperado.getNome());
        assertThat(resultExpected.cpf()).isEqualTo(cooperado.getCpf());
    }

    @Test
    void GIVEN_InvalidFindbyCooperadoUuid_ReturnException(){
        //given
        var query = new FindCooperadoByUuidQuery();
        var uuid = UUID.randomUUID();
        query.setUuid(uuid);
        when(repository.findByUuid(query.getUuid())).thenReturn(Optional.empty());
        //when
        var exception = assertThrows(CooperadoNotExistentException.class, () ->
                resolver.resolve(query));
        //then
        assertThat(exception.getMessage()).isEqualTo("Cooperado not exist");
        verify(repository, times(1)).findByUuid(uuid);
    }
}