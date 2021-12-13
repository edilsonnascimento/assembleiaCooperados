package br.org.enascimento.assembleiacooperados.write.observable;

import br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class LogListener {

    Logger logger = LoggerFactory.getLogger(LogListener.class);

    @Async
    @EventListener
    void onEventOccur(CommandEvent event) {

        if (event.isSuccess()) {
            if(logger.isInfoEnabled()) logger.info(event.toJson());
        } else if (event.getException() instanceof DomainException) {
            if(logger.isWarnEnabled()) logger.warn(event.toJson());
        } else {
            if(logger.isErrorEnabled()) logger.error(event.toJson());
        }
    }
}
