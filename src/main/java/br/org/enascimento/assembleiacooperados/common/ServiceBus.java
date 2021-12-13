package br.org.enascimento.assembleiacooperados.common;

import br.org.enascimento.assembleiacooperados.red.application.Query;
import br.org.enascimento.assembleiacooperados.red.application.Resolver;
import br.org.enascimento.assembleiacooperados.red.observable.QueryEvent;
import br.org.enascimento.assembleiacooperados.write.domain.application.Command;
import br.org.enascimento.assembleiacooperados.write.domain.application.Handler;
import br.org.enascimento.assembleiacooperados.write.observable.CommandEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


@Service
public class ServiceBus {
    private ApplicationContext context;
    private ApplicationEventPublisher publisher;

    public ServiceBus(ApplicationContext context, ApplicationEventPublisher publisher) {
        this.context = context;
        this.publisher = publisher;
    }

    public void execute(Command command) {
        var event = new CommandEvent(command);
        execute(event);
    }

    public void execute(Query query) {
        var event = new QueryEvent(query);
        execute(event);
    }

    private void execute(InternalEvent event) {

        try {
            run(event);
        } catch (Exception exception) {
            event.setException(exception);
            throw exception;
        } finally {
            event.stopTimer();
            publisher.publishEvent(event);
        }
    }

    private void run(InternalEvent event) {

        var beanName = event.getOrigin().substring(0, 1).toLowerCase() + event.getOrigin().substring(1);

        switch (event.getType()) {
            case COMMAND -> {
                var handlerBeanName = beanName.replace("Command", "Handler");
                Handler<Command> handler = (Handler) context.getBean(handlerBeanName);
                handler.handle((Command) event.getSource());
            }
            case QUERY -> {
                var resolverBeanName = beanName.replace("Query", "Resolver");
                Resolver<Query> resolver = (Resolver) context.getBean(resolverBeanName);
                resolver.resolve((Query) event.getSource());
            }
            default -> throw new ServiceBusInvalidObjectException(event);
        }
    }
}
