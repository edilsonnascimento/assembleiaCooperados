package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadPautaRepository;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.SessaoInDto;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateSessaoCommand;
import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import br.org.enascimento.assembleiacooperados.write.domain.core.Status;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteSessaoRepository;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@Tag("unit")
public class CreateSessaoHandlerTest extends TestHelper {

    private CreateSessaoHandler handler;
    private WriteSessaoRepository repository;
    private ReadPautaRepository repositoryRead;

    public CreateSessaoHandlerTest() {
        this.repository = mock(WriteSessaoRepository.class);
        this.repositoryRead = mock(ReadPautaRepository.class);
        this.handler = new CreateSessaoHandler(repository, repositoryRead);
    }

    @Test
    void Given_ValidCreateSessaoCommand_Must_DelegateToHeadler(){
        //given
        var dto = new SessaoInDto(UUID.randomUUID(), UUID.randomUUID());
        var command = new CreateSessaoCommand(dto);
        var pauta = new Pauta()
                .setId(1l)
                .setUuid(UUID.randomUUID())
                .setTitulo("PRIMEIRO-TITULO")
                .setDescricao("PRIMEIRA-DESCICAO");
        when(repositoryRead.findByUuid(any())).thenReturn(Optional.of(pauta));
        var status = new Status().setId(1l);
        when(repository.findStatus(anyLong())).thenReturn(Optional.of(status));

        //when
        handler.handle(command);

        //then
        verify(repositoryRead, timeout(1)).findByUuid(any());
        verify(repository, timeout(1)).create(any());
    }
}
