package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.domain.core.WriteSessaoRepository;
import helper.TestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@Tag("unit")
class JobSessaoHandlerTest extends TestHelper {

    @Mock
    private WriteSessaoRepository repository;
    @InjectMocks
    private JobSessaoHandler handler;

    @Test
    void WHEN_Cron_invoke_executeRepositoryCloseSessions(){
        //when
        handler.handle();

        //then
        verify(repository, times(1)).fecharSessoes();
    }
}