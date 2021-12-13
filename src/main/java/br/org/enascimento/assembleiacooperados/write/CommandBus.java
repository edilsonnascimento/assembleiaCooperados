package br.org.enascimento.assembleiacooperados.write;

import br.org.enascimento.assembleiacooperados.write.domain.application.Command;
import br.org.enascimento.assembleiacooperados.write.domain.application.Handler;
import br.org.enascimento.assembleiacooperados.write.observable.CommandEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Faz o meio de campo entre o Adapter e o Domain
 */
@Service
public class CommandBus {

    private ApplicationContext context;
    private ApplicationEventPublisher publisher;

    public CommandBus(ApplicationContext context, ApplicationEventPublisher publisher) {
        this.context = context;
        this.publisher = publisher;
    }

    public void execute(Command command) {

        CommandEvent event = new CommandEvent(this, command);

        try {
            handle(command);
        } catch (Exception exception) {
            event.setException(exception);
            throw exception;
        } finally {
            publisher.publishEvent(event);
        }
    }

    private void handle(Command command) {
        String handlerName = String.format("%sHandler", command.getClass().getSimpleName());
        String handlerBeanName = Character.toLowerCase(handlerName.charAt(0)) + handlerName.substring(1);
        Handler<Command> handler = (Handler) context.getBean(handlerBeanName);
        handler.handle(command);
    }
}
