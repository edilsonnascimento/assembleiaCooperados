package br.org.enascimento.assembleiacooperados.write.domain.application.handler;

import br.org.enascimento.assembleiacooperados.write.domain.core.WriteSessaoRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class JobSessaoHandler{

    private static final String TIME_ZONE = "America/Sao_Paulo";

    private WriteSessaoRepository repository;

    public JobSessaoHandler(WriteSessaoRepository repository) {
        this.repository = repository;
    }

    @Scheduled(cron = "0/5 * *  * * *", zone = TIME_ZONE)
    public void handle() {
        repository.fecharSessoes();
    }
}
