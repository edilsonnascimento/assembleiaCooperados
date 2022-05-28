package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllStatusQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadStatusRepository;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.StatusDto;
import br.org.enascimento.assembleiacooperados.write.domain.core.Status;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Tag("unit")
class ListAllStatusResolverTest extends TestHelper {

    @Mock
    private ReadStatusRepository repository;
    @InjectMocks
    private ListAllStatusResolver resolver;

    @Test
    void WHEN_QueryAllStatus_MUST_RetriveSuccessfull(){
        //given
        var query = new ListAllStatusQuery();
        var dataAtual = LocalDateTime.now();
        var aberta = new Status();
        aberta.setId(1l);
        aberta.setDescricao("ABERTA");
        aberta.setCreatedAt(dataAtual);
        aberta.setUpdatedAt(dataAtual);
        var encerrada = new Status() ;
        encerrada.setId(2l) ;
        encerrada.setDescricao("ENCERRADA") ;
        encerrada.setCreatedAt(dataAtual.plusSeconds(1l));
        encerrada.setUpdatedAt(dataAtual.plusSeconds(1l));
        var listStatus = List.of(aberta, encerrada);
        var expected = List.of(new StatusDto("ABERTA"), new StatusDto("ENCERRADA"));
        when(repository.findAll()).thenReturn(Optional.of(listStatus));
        //when
        resolver.resolve(query);
        var actual = query.getResult();
        //then
        verify(repository, times(1)).findAll();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}