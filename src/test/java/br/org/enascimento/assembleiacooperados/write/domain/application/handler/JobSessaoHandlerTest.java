package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.domain.core.WriteSessaoRepository;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


@Tag("unit")
class JobSessaoHandlerTest extends TestHelper {

    @Test
    void WHEN_Cron_invoke_executeRepositoryCloseSessions(){
        //given
        var repository = mock(WriteSessaoRepository.class);
        var handler = new JobSessaoHandler(repository);

        //when
        handler.handle();

        //then
        verify(repository, times(1)).fecharSessoes();
    }
}
